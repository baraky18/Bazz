package com.bazz.automatic_okr.service;

import com.bazz.automatic_okr.dto.NestedOkrDto;
import com.bazz.automatic_okr.dto.OkrDto;
import com.bazz.automatic_okr.dto.ObjectiveDto;
import com.bazz.automatic_okr.dto.openai.*;
import com.bazz.automatic_okr.mapper.OpenaiResponseDtoToKeyResultsDtoMapper;
import com.bazz.automatic_okr.mapper.OpenaiResponseDtoToNestedOkrDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OpenaiService {

    @Value("${openai.url}")
    private String openaiUrl;
    @Value("${openai.token}")
    private String openaiToken;
    @Value("${openai.system-content-get-okr}")
    private String openaiSystemContentGetOkr;
    @Value("${openai.system-content-nested-organization-with-okrs}")
    private String openaiSystemContentNestedOrganizationWithOkrs;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OpenaiResponseDtoToKeyResultsDtoMapper openaiResponseDtoToKeyResultsDtoMapper;

    @Autowired
    OpenaiResponseDtoToNestedOkrDtoMapper openaiResponseDtoToNestedOkrDtoMapper;

    public OkrDto getOkr(ObjectiveDto objectiveDto) {
        OpenaiInputRequestDto openaiInputRequestDto = getOkrInputRequestDto(objectiveDto);
        HttpEntity<String> entity = new HttpEntity(getOpenaiRequest(openaiInputRequestDto), getHeaders());
        OpenaiResponseDto openaiResponseDto = restTemplate.postForObject(openaiUrl, entity, OpenaiResponseDto.class);
        log.info(openaiResponseDto.toString());
        return openaiResponseDtoToKeyResultsDtoMapper.map(openaiResponseDto, objectiveDto);
    }

    @SneakyThrows
    public NestedOkrDto getNestedOrganizationWithOkrs(NestedOkrDto nestedOrganizationWithoutNestedOkrs) {
        OpenaiInputRequestDto openaiInputRequestDto = getNestedOrganizationWithOkrsInputRequestDto(nestedOrganizationWithoutNestedOkrs);
        HttpEntity<String> entity = new HttpEntity(getOpenaiRequest(openaiInputRequestDto), getHeaders());
        OpenaiResponseDto openaiResponseDto = restTemplate.postForObject(openaiUrl, entity, OpenaiResponseDto.class);
        log.info(openaiResponseDto.toString());
        return openaiResponseDtoToNestedOkrDtoMapper.map(openaiResponseDto);
    }

    private OpenaiInputRequestDto getOkrInputRequestDto(ObjectiveDto objectiveDto) {
        return OpenaiInputRequestDto.builder()
                    .model("gpt-3.5-turbo-0125")
                    .systemContent(openaiSystemContentGetOkr)
                    .userContent(objectiveDto.getObjective())
                    .build();
    }

    @SneakyThrows
    private OpenaiInputRequestDto getNestedOrganizationWithOkrsInputRequestDto(NestedOkrDto nestedOrganizationWithoutNestedOkrs) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String nestedOrganizationWithoutNestedOkrsJson = objectWriter.writeValueAsString(nestedOrganizationWithoutNestedOkrs);
        OpenaiInputRequestDto openaiInputRequestDto = OpenaiInputRequestDto.builder()
                .model("gpt-4")
                .systemContent(openaiSystemContentNestedOrganizationWithOkrs)
                .userContent(nestedOrganizationWithoutNestedOkrsJson)
                .build();
        return openaiInputRequestDto;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, openaiToken);
        return headers;
    }

    private OpenaiRequestDto getOpenaiRequest(OpenaiInputRequestDto openaiInputRequestDto) {
        return OpenaiRequestDto.builder()
                .model(openaiInputRequestDto.getModel())
                .temperature(BigDecimal.valueOf(1))
                .max_tokens(2000)
                .top_p(BigDecimal.valueOf(1))
                .frequency_penalty(BigDecimal.valueOf(0))
                .presence_penalty(BigDecimal.valueOf(0))
                .messages(createMessagesRequest(openaiInputRequestDto))
                .build();
    }

    private List<MessageRequestDto> createMessagesRequest(OpenaiInputRequestDto openaiInputRequestDto) {
        List<MessageRequestDto> messageDtos = new ArrayList<>();
        messageDtos.add(MessageRequestDto.builder()
                .role(RoleDto.system)
                .content(getSystemContents(openaiInputRequestDto.getSystemContent()))
                .build());
        messageDtos.add(MessageRequestDto.builder()
                .role(RoleDto.user)
                .content(getUserContents(openaiInputRequestDto.getUserContent()))
                .build());
        return messageDtos;
    }

    private List<ContentRequestDto> getSystemContents(String openaiSystemContent) {
        List<ContentRequestDto> contentDtos = new ArrayList<>();
        contentDtos.add(ContentRequestDto.builder()
                .text(openaiSystemContent)
                .type("text")
                .build());
        return contentDtos;
    }

    private List<ContentRequestDto> getUserContents(String text) {
        List<ContentRequestDto> contentDtos = new ArrayList<>();
        contentDtos.add(ContentRequestDto.builder()
                .text(text)
                .type("text")
                .build());
        return contentDtos;
    }
}
