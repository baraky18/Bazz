package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dao.UserEntity;
import com.bazz.automatic_okr.dto.jira.JiraActorDto;
import org.springframework.stereotype.Component;

@Component
public class JiraActorDtoToUserEntityMapper implements OneToOneMapper<JiraActorDto, UserEntity> {

    @Override
    public UserEntity map(JiraActorDto jiraActorDto) {
        return UserEntity.builder()
                .displayName(jiraActorDto.getDisplayName())
                .jiraAccountId(jiraActorDto.getActorUser().getAccountId())
                .build();
    }

    @Override
    public JiraActorDto reverseMap(UserEntity userEntity) {
        return null;
    }
}
