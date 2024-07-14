package com.bazz.automatic_okr.dto.jira;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JiraProjectMembersDto {

    private List<JiraActorDto> actors;
    private JiraScopeDto scope;
}
