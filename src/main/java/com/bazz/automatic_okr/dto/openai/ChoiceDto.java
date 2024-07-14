package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceDto {

    private int index;
    private MessageResponseDto message;
    private String logprobs;
    private String finish_reason;
}
