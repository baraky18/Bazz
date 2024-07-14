package com.bazz.automatic_okr.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class OrganizationUpdatableDataDto {

    private Optional<String> organizationName;
    private Optional<UserDto> organizationManager;
    private Optional<String> parentOrganizationId;
}
