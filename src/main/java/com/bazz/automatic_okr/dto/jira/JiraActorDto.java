package com.bazz.automatic_okr.dto.jira;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JiraActorDto {

    private int id;
    private String displayName;
    private String type;
    private ActorUserDto actorUser;
}
