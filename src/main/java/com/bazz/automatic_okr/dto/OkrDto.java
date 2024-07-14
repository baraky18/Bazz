package com.bazz.automatic_okr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OkrDto {

    private String objective;
    private String derivedFromKeyResultId;
    private List<KeyResultDto> keyResults;
}
