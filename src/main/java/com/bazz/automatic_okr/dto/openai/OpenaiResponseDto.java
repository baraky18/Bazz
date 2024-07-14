package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OpenaiResponseDto {

    private String id;
    private String object;
    private Date created;
    private String model;
    private List<ChoiceDto> choices;
    private UsageDto usage;
    private String system_fingerprint;
}
