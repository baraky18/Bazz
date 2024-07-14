package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenaiInputRequestDto {

    private String userContent;
    private String systemContent;
    private String model;
}
