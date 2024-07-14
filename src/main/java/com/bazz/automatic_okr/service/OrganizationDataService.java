package com.bazz.automatic_okr.service;

import com.bazz.automatic_okr.dao.OrganizationDataEntity;
import com.bazz.automatic_okr.dao.ProjectRole;
import com.bazz.automatic_okr.dao.UserEntity;
import com.bazz.automatic_okr.dto.NestedOrganizationDataDto;
import com.bazz.automatic_okr.dto.OrganizationDataDto;
import com.bazz.automatic_okr.dto.OrganizationUpdatableDataDto;
import com.bazz.automatic_okr.dto.jira.JiraProjectMembersDto;
import com.bazz.automatic_okr.mapper.JiraProjectMembersDtoToOrganizationDataEntityMapper;
import com.bazz.automatic_okr.mapper.OrganizationDataEntityToNestedOrganizationDataDtoMapper;
import com.bazz.automatic_okr.mapper.OrganizationDataEntityToOrganizationDataDtoMapper;
import com.bazz.automatic_okr.repository.OrganizationDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrganizationDataService {

    @Autowired
    private JiraService jiraService;

    @Autowired
    JiraProjectMembersDtoToOrganizationDataEntityMapper jiraProjectMembersDtoToOrganizationDataEntityMapper;

    @Autowired
    OrganizationDataEntityToOrganizationDataDtoMapper organizationDataEntityToOrganizationDataDtoMapper;

    @Autowired
    OrganizationDataEntityToNestedOrganizationDataDtoMapper organizationDataEntityToNestedOrganizationDataDtoMapper;

    @Autowired
    OrganizationDataRepository organizationDataRepository;

    public void syncOrganizationData() {
        List<JiraProjectMembersDto> jiraProjectMemberDtoList = jiraService.fetchProjectData();
        saveOrganizationData(jiraProjectMemberDtoList);
    }

    private void saveOrganizationData(List<JiraProjectMembersDto> jiraProjectMemberDtoList) {
        jiraProjectMemberDtoList.forEach(project -> {
            OrganizationDataEntity organizationDataEntity = jiraProjectMembersDtoToOrganizationDataEntityMapper.map(project);
            if(organizationDataRepository.findByProjectJiraId(project.getScope().getProject().getId()).isEmpty()){
                organizationDataRepository.save(organizationDataEntity);
            }
        });
        organizationDataRepository.findByOrganizationName("Company").orElse(saveCompanyLevelOrganizationData());
    }

    private OrganizationDataEntity saveCompanyLevelOrganizationData() {
        List<OrganizationDataEntity> allOrganizationDataEntities = organizationDataRepository.findAllByParentOrganizationIdIsNull();
        OrganizationDataEntity companyLevelOrganizationData = OrganizationDataEntity.builder()
                .organizationName("Company")
                .users(new ArrayList<>())
                .childOrganizationsData(new HashSet<>(allOrganizationDataEntities))
                .build();
        return organizationDataRepository.save(companyLevelOrganizationData);
    }

    public List<OrganizationDataDto> getOrganizationData() {
        List<OrganizationDataDto> organizationDataDtos = new ArrayList<>();
        List<OrganizationDataEntity> organizationDataEntities = organizationDataRepository.findAll();
        organizationDataEntities.forEach(entity -> {
            OrganizationDataDto organizationDataDto = organizationDataEntityToOrganizationDataDtoMapper.map(entity);
            organizationDataDtos.add(organizationDataDto);});
        return organizationDataDtos;
    }

    public void updateOrganizationData(String organizationId, OrganizationUpdatableDataDto organizationUpdatableDataDto) {
        OrganizationDataEntity organizationDataEntity = organizationDataRepository.findByOrganizationId(organizationId).orElseThrow();
        String managerJiraAccountId = organizationUpdatableDataDto.getOrganizationManager().isPresent() ? organizationUpdatableDataDto.getOrganizationManager().get().getJiraAccountId() : null;
        if(!Objects.isNull(managerJiraAccountId)){
            UserEntity userEntity = organizationDataEntity.getUsers()
                    .stream()
                    .filter(u -> u.getJiraAccountId().equalsIgnoreCase(managerJiraAccountId))
                    .findAny()
                    .orElseThrow();
            userEntity.setProjectRole(ProjectRole.PROJECT_MANAGER);
        }
        organizationDataEntity.setOrganizationName(organizationUpdatableDataDto.getOrganizationName().orElse(organizationDataEntity.getOrganizationName()));
        if(organizationUpdatableDataDto.getParentOrganizationId().isPresent()){
            OrganizationDataEntity parentOrganizationDataEntity = organizationDataRepository.findByOrganizationId(organizationUpdatableDataDto.getParentOrganizationId().get()).orElseThrow();
            if(Objects.isNull(parentOrganizationDataEntity.getChildOrganizationsData())){
                parentOrganizationDataEntity.setChildOrganizationsData(new HashSet<>());
            }
            parentOrganizationDataEntity.getChildOrganizationsData().add(organizationDataEntity);
            organizationDataEntity.setParentOrganizationId(organizationUpdatableDataDto.getParentOrganizationId().get());
            organizationDataRepository.save(parentOrganizationDataEntity);
        }
        organizationDataRepository.save(organizationDataEntity);
    }

    public NestedOrganizationDataDto getNestedOrganizationData() {
        OrganizationDataEntity companyOrganizationDataEntity = organizationDataRepository.findByOrganizationName("Company").get();
        NestedOrganizationDataDto nestedOrganizationDataDto = organizationDataEntityToNestedOrganizationDataDtoMapper.map(companyOrganizationDataEntity);
        return nestedOrganizationDataDto;
    }
}
