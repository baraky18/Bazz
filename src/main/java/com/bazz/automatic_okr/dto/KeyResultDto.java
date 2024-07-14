package com.bazz.automatic_okr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyResultDto {

    private String keyResultId;
    private String keyResult;
}
