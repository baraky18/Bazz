package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dao.OrganizationDataEntity;
import com.bazz.automatic_okr.dto.NestedOrganizationDataDto;
import com.bazz.automatic_okr.dto.UserDto;
import com.bazz.automatic_okr.repository.OrganizationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrganizationDataEntityToNestedOrganizationDataDtoMapper implements OneToOneMapper<OrganizationDataEntity, NestedOrganizationDataDto> {

    @Autowired
    UserEntityToUserDtoMapper userEntityToUserDtoMapper;

    @Autowired
    OrganizationDataRepository organizationDataRepository;

    @Override
    public NestedOrganizationDataDto map(OrganizationDataEntity organizationDataEntity) {
        return NestedOrganizationDataDto.builder()
                .organizationId(organizationDataEntity.getOrganizationId())
                .projectJiraId(organizationDataEntity.getProjectJiraId())
                .users(mapUsers(organizationDataEntity))
                .organizationName(organizationDataEntity.getOrganizationName())
                .childOrganizationsData(mapChildOrganizationData(organizationDataEntity.getChildOrganizationsData()))
                .build();
    }

    private Set<NestedOrganizationDataDto> mapChildOrganizationData(Set<OrganizationDataEntity> childOrganizationsData) {
        Set<NestedOrganizationDataDto> nestedChildOrganizationDataDtos = new HashSet<>();
        if(Objects.isNull(childOrganizationsData) || childOrganizationsData.isEmpty()){
            return null;
        }
        childOrganizationsData.forEach(entity -> {
            Optional<OrganizationDataEntity> childEntity = organizationDataRepository.findByOrganizationId(entity.getOrganizationId());
            NestedOrganizationDataDto childNestedOrganizationDataDto = this.map(childEntity.get());
            nestedChildOrganizationDataDtos.add(childNestedOrganizationDataDto);
        });
        return nestedChildOrganizationDataDtos;
    }

    private List<UserDto> mapUsers(OrganizationDataEntity organizationDataEntity) {
        List<UserDto> userDtos = new ArrayList<>();
        organizationDataEntity.getUsers().forEach(user -> {UserDto userDto = userEntityToUserDtoMapper.map(user);
            userDtos.add(userDto);});
        return userDtos;
    }

    @Override
    public OrganizationDataEntity reverseMap(NestedOrganizationDataDto nestedOrganizationDataDto) {
        return null;
    }
}
