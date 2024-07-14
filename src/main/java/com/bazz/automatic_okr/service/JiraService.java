package com.bazz.automatic_okr.service;

import com.bazz.automatic_okr.dto.jira.JiraProjectDto;
import com.bazz.automatic_okr.dto.jira.JiraProjectMembersDto;
import com.bazz.automatic_okr.dto.jira.JiraRolesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JiraService {

    @Value("${jira.url}")
    private String jiraUrl;
    @Value("${jira.username}")
    private String jiraUsername;
    @Value("${jira.apiToken}")
    private String jiraApiToken;
    @Value("${jira.project-uri}")
    private String jiraProjectUri;
    @Value("${jira.role-uri}")
    private String jiraRoleUri;

    @Autowired
    RestTemplate restTemplate;

    public List<JiraProjectMembersDto> fetchProjectData() {
        HttpEntity<String> httpEntity = getHttpEntity();
        JiraProjectDto[] jiraProjectDtoList = getJiraProjects(httpEntity);
        List<JiraRolesDto> jiraRolesDtoList = getRolesPerProject(httpEntity, jiraProjectDtoList);
        return getMembersPerProject(httpEntity, jiraRolesDtoList);
    }

    private List<JiraProjectMembersDto> getMembersPerProject(HttpEntity<String> entity, List<JiraRolesDto> jiraRolesDtoList) {
        List<JiraProjectMembersDto> jiraProjectMemberDtoList = new ArrayList<>();
        jiraRolesDtoList.forEach(r -> {
            if(r.getMember() != null) {
                JiraProjectMembersDto jiraProjectMemberDto = restTemplate.exchange(r.getMember(), HttpMethod.GET, entity, JiraProjectMembersDto.class).getBody();
                jiraProjectMemberDtoList.add(jiraProjectMemberDto);
            }
        });
        return jiraProjectMemberDtoList;
    }

    private List<JiraRolesDto> getRolesPerProject(HttpEntity<String> entity, JiraProjectDto[] jiraProjectDtoList) {
        List<JiraRolesDto> jiraRolesDtoList = new ArrayList<>();
        List<Integer> projectIdList = Arrays.stream(jiraProjectDtoList).toList().stream()
                .map(jiraProjectDto -> jiraProjectDto.getId())
                .collect(Collectors.toList());

        List<String> jiraRoleUriWithProjectIdList = projectIdList.stream().map(i -> jiraRoleUri.replace("{ID}", i.toString())).collect(Collectors.toList());
        jiraRoleUriWithProjectIdList.forEach(s -> log.info(s));

        jiraRoleUriWithProjectIdList.forEach(uri -> {
            JiraRolesDto jiraRolesDto = restTemplate.exchange(jiraUrl + uri, HttpMethod.GET, entity, JiraRolesDto.class).getBody();
            jiraRolesDtoList.add(jiraRolesDto);
            log.info(jiraRolesDto.toString());
        });
        return jiraRolesDtoList;
    }

    private JiraProjectDto[] getJiraProjects(HttpEntity<String> entity) {
        ResponseEntity<JiraProjectDto[]> jiraProjectResponse = restTemplate.exchange(jiraUrl + jiraProjectUri, HttpMethod.GET, entity, JiraProjectDto[].class);
        Arrays.stream(jiraProjectResponse.getBody()).toList().forEach(jiraProjectDto -> log.info(jiraProjectDto.toString()));
        return jiraProjectResponse.getBody();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(jiraUsername, jiraApiToken);
        HttpEntity<String> entity = new HttpEntity(headers);
        return entity;
    }
}
