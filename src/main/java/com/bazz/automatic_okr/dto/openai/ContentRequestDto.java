package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentRequestDto {

    private String text;
    private String type;
}
