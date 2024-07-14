package com.bazz.automatic_okr.controller;

import com.bazz.automatic_okr.dto.NestedOrganizationDataDto;
import com.bazz.automatic_okr.dto.OrganizationDataDto;
import com.bazz.automatic_okr.dto.OrganizationUpdatableDataDto;
import com.bazz.automatic_okr.service.OrganizationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization-data")
public class OrganizationDataController {

    @Autowired
    private OrganizationDataService organizationDataService;


    @PostMapping(path = "/sync")
    public void syncOrganizationData(){
        organizationDataService.syncOrganizationData();
    }

    @GetMapping
    public List<OrganizationDataDto> getOrganizationData(){
        return organizationDataService.getOrganizationData();
    }

    @PatchMapping(path = "/{organizationId}")
    public void updateOrganizationData(@PathVariable String organizationId, @RequestBody OrganizationUpdatableDataDto organizationUpdatableDataDto){
        organizationDataService.updateOrganizationData(organizationId, organizationUpdatableDataDto);
    }

    @GetMapping(path = "/nested")
    public NestedOrganizationDataDto getNestedOrganizationData(){
        return organizationDataService.getNestedOrganizationData();
    }
}
