package com.bazz.automatic_okr.dto.jira;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JiraRolesDto {

    private String Administrators;
    private String Administrator;
    private String Viewer;
    private String Member;
}
