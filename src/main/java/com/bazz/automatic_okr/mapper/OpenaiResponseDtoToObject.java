package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dto.openai.OpenaiResponseDto;
import org.springframework.stereotype.Component;

@Component
public abstract class OpenaiResponseDtoToObject {

    protected String getResponseContent(OpenaiResponseDto openaiResponseDto) {
        String responseContent = "";
        if(openaiResponseDto.getChoices() != null){
            if(openaiResponseDto.getChoices().get(0) != null){
                if(openaiResponseDto.getChoices().get(0).getMessage() != null){
                    if(openaiResponseDto.getChoices().get(0).getMessage().getContent() != null){
                        responseContent = openaiResponseDto.getChoices().get(0).getMessage().getContent();
                    }
                }
            }
        }
        return responseContent;
    }
}
