package com.bazz.automatic_okr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NestedObjectiveDto {

    private String objective;
    private String organizationName;
    private String organizationId;
}
