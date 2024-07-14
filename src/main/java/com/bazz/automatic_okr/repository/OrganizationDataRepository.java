package com.bazz.automatic_okr.repository;

import com.bazz.automatic_okr.dao.OrganizationDataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrganizationDataRepository extends MongoRepository<OrganizationDataEntity, String> {


    Optional<OrganizationDataEntity> findByOrganizationId(String organizationId);

    Optional<OrganizationDataEntity> findByProjectJiraId(int projectJiraId);

    Optional<OrganizationDataEntity> findByOrganizationName(String organizationName);

    List<OrganizationDataEntity> findAllByParentOrganizationIdIsNull();
}
