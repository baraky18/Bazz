package com.bazz.automatic_okr.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NestedKeyResultDto {

    private String keyResult;
    private List<NestedOkrDto> nestedOkrDtos;
}
