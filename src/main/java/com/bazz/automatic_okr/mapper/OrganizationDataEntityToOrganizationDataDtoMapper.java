package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dao.OrganizationDataEntity;
import com.bazz.automatic_okr.dto.OrganizationDataDto;
import com.bazz.automatic_okr.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrganizationDataEntityToOrganizationDataDtoMapper implements OneToOneMapper<OrganizationDataEntity, OrganizationDataDto> {

    @Autowired
    UserEntityToUserDtoMapper userEntityToUserDtoMapper;

    @Override
    public OrganizationDataDto map(OrganizationDataEntity organizationDataEntity) {
        return OrganizationDataDto.builder()
                .organizationId(organizationDataEntity.getOrganizationId())
                .projectJiraId(organizationDataEntity.getProjectJiraId())
                .users(mapUsers(organizationDataEntity))
                .organizationName(organizationDataEntity.getOrganizationName())
                .parentOrganizationId(organizationDataEntity.getParentOrganizationId())
                .build();
    }

    private List<UserDto> mapUsers(OrganizationDataEntity projectDataEntity) {
        List<UserDto> userDtos = new ArrayList<>();
        projectDataEntity.getUsers().forEach(user -> {UserDto userDto = userEntityToUserDtoMapper.map(user);
            userDtos.add(userDto);});
        return userDtos;
    }

    @Override
    public OrganizationDataEntity reverseMap(OrganizationDataDto projectDataDto) {
        return null;
    }
}
