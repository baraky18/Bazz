package com.bazz.automatic_okr.dto.jira;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JiraScopeDto {

    private String type;
    private JiraProjectDto project;
}
