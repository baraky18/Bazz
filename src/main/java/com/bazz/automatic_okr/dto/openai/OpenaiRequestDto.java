package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OpenaiRequestDto {

    private String model;
    private List<MessageRequestDto> messages;
    private BigDecimal temperature;
    private int max_tokens;
    private BigDecimal top_p;
    private BigDecimal frequency_penalty;
    private BigDecimal presence_penalty;
}
