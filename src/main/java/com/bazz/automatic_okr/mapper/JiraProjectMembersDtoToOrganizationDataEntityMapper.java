package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dao.OrganizationDataEntity;
import com.bazz.automatic_okr.dao.UserEntity;
import com.bazz.automatic_okr.dto.jira.JiraProjectMembersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JiraProjectMembersDtoToOrganizationDataEntityMapper implements OneToOneMapper<JiraProjectMembersDto, OrganizationDataEntity> {

    @Autowired
    JiraActorDtoToUserEntityMapper jiraActorDtoToUserEntityMapper;

    @Override
    public OrganizationDataEntity map(JiraProjectMembersDto jiraProjectMembersDto) {
        return OrganizationDataEntity.builder()
                .projectJiraId(jiraProjectMembersDto.getScope().getProject().getId())
                .users(mapJiraProjectRolesToUsers(jiraProjectMembersDto))
                .build();
    }

    private List<UserEntity> mapJiraProjectRolesToUsers(JiraProjectMembersDto jiraProjectMembersDto) {
        List<UserEntity> userEntityList = new ArrayList<>();
        jiraProjectMembersDto.getActors().forEach(actor -> {
            UserEntity userEntity = jiraActorDtoToUserEntityMapper.map(actor);
            userEntityList.add(userEntity);
        });
        return userEntityList;
    }

    @Override
    public JiraProjectMembersDto reverseMap(OrganizationDataEntity projectDataEntity) {
        return null;
    }
}
