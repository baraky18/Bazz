package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dto.NestedOkrDto;
import com.bazz.automatic_okr.dto.openai.OpenaiResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class OpenaiResponseDtoToNestedOkrDtoMapper extends OpenaiResponseDtoToObject implements OneToOneMapper <OpenaiResponseDto, NestedOkrDto>{

    @Override
    @SneakyThrows
    public NestedOkrDto map(OpenaiResponseDto openaiResponseDto) {
        String nestedOkrDtoJson = getResponseContent(openaiResponseDto);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(nestedOkrDtoJson, NestedOkrDto.class);
    }

    @Override
    public OpenaiResponseDto reverseMap(NestedOkrDto nestedOkrDto) {
        return null;
    }
}
