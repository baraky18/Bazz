package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsageDto {

    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}
