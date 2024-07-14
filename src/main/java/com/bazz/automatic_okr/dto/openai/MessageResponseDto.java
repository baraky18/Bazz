package com.bazz.automatic_okr.dto.openai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDto {

    private RoleDto role;
    private String content;
}
