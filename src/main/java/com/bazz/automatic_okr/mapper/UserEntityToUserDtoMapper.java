package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dao.UserEntity;
import com.bazz.automatic_okr.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoMapper implements OneToOneMapper<UserEntity, UserDto> {

    @Override
    public UserDto map(UserEntity userEntity) {
        return UserDto.builder()
                .displayName(userEntity.getDisplayName())
                .jiraAccountId(userEntity.getJiraAccountId())
                .projectRole(userEntity.getProjectRole())
                .build();
    }

    @Override
    public UserEntity reverseMap(UserDto userDto) {
        return null;
    }
}
