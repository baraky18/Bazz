package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dto.KeyResultDto;
import com.bazz.automatic_okr.dto.OkrDto;
import com.bazz.automatic_okr.dto.ObjectiveDto;
import com.bazz.automatic_okr.dto.openai.OpenaiResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class OpenaiResponseDtoToKeyResultsDtoMapper extends OpenaiResponseDtoToObject implements TwoToOneMapper<OpenaiResponseDto, ObjectiveDto, OkrDto> {


    @Override
    public OkrDto map(OpenaiResponseDto openaiResponseDto, ObjectiveDto objectiveDto) {
        return OkrDto.builder()
                .keyResults(breakContentToKeyResults(getResponseContent(openaiResponseDto)))
                .objective(objectiveDto.getObjective())
                .build();
    }

    private List<KeyResultDto> breakContentToKeyResults(String responseContent) {
        StringTokenizer stringTokenizer = new StringTokenizer(responseContent, "\n");
        List<KeyResultDto> keyResultDtoList = new ArrayList<>();
        while(stringTokenizer.hasMoreElements()){
            keyResultDtoList.add(KeyResultDto.builder()
                    .keyResult(stringTokenizer.nextToken())
                    .build());
        }
        return keyResultDtoList;
    }
}
