package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessageRequestDto {

    private RoleDto role;
    private List<ContentRequestDto> content;
}
