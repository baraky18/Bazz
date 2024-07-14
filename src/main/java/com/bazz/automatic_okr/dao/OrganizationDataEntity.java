package com.bazz.automatic_okr.dao;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "automatic-okr")
@Data
@Builder
public class OrganizationDataEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private String organizationId;
    private String organizationName;
    private List<UserEntity> users;
    private int projectJiraId;
    private String parentOrganizationId;
    private Set<OrganizationDataEntity> childOrganizationsData;
}
