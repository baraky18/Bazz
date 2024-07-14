package com.bazz.automatic_okr.dto.jira;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JiraProjectDto {

    private int id;
    private String key;
    private String name;
    private String projectTypeKey;
}
