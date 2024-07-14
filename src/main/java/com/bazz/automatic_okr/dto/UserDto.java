package com.bazz.automatic_okr.dto;

import com.bazz.automatic_okr.dao.ProjectRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String displayName;
    private String jiraAccountId;
    private ProjectRole projectRole;
}
