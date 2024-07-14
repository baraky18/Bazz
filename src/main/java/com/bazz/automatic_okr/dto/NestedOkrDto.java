package com.bazz.automatic_okr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NestedOkrDto {

    private String organizationName;
    private String organizationId;
    private List<OkrDto> okrs;
    private Set<NestedOkrDto> childNestedOkrs;
}
