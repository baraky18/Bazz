package com.bazz.automatic_okr.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class NestedOrganizationDataDto {

    private String organizationName;
    private String organizationId;
    private List<UserDto> users;
    private int projectJiraId;
    private Set<NestedOrganizationDataDto> childOrganizationsData;
}
