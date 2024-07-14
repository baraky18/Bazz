package com.bazz.automatic_okr.service;

import com.bazz.automatic_okr.dto.NestedOkrDto;
import com.bazz.automatic_okr.dto.ObjectiveDto;
import com.bazz.automatic_okr.dto.OkrDto;
import com.bazz.automatic_okr.mapper.NestedOrganizationDataDtoToNestedOkrDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OKRService {

    @Autowired
    OpenaiService openaiService;

    @Autowired
    OrganizationDataService organizationDataService;

    @Autowired
    NestedOrganizationDataDtoToNestedOkrDtoMapper nestedOrganizationDataDtoToNestedOkrDtoMapper;


    public OkrDto getOkr(ObjectiveDto objectiveDto) {
        return openaiService.getOkr(objectiveDto);
    }

    public NestedOkrDto getNestedOkr(ObjectiveDto objectiveDto) {
        NestedOkrDto nestedOrganizationWithoutNestedOkrs = nestedOrganizationDataDtoToNestedOkrDtoMapper.map(organizationDataService.getNestedOrganizationData());
        nestedOrganizationWithoutNestedOkrs.setOkrs(setHighestObjectives(objectiveDto));
        return openaiService.getNestedOrganizationWithOkrs(nestedOrganizationWithoutNestedOkrs);
    }

    private List<OkrDto> setHighestObjectives(ObjectiveDto objectiveDto) {
        List<OkrDto> okrs = new ArrayList<>();
        okrs.add(OkrDto.builder()
                .objective(objectiveDto.getObjective())
                .build());
        return okrs;
    }
}
