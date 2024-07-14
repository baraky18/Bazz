package com.bazz.automatic_okr.dao;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "automatic-okr")
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private String id;
    private String displayName;
    private String jiraAccountId;
    private ProjectRole projectRole;
}
