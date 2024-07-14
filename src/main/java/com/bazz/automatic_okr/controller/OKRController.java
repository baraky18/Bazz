package com.bazz.automatic_okr.controller;

import com.bazz.automatic_okr.dto.NestedObjectiveDto;
import com.bazz.automatic_okr.dto.NestedOkrDto;
import com.bazz.automatic_okr.dto.OkrDto;
import com.bazz.automatic_okr.dto.ObjectiveDto;
import com.bazz.automatic_okr.service.OKRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/okr")
public class OKRController {

    @Autowired
    OKRService okrService;

    @GetMapping
    public OkrDto getOkr(@RequestBody ObjectiveDto objectiveDto){
        return okrService.getOkr(objectiveDto);
    }

    @GetMapping(path = "/nested")
    public NestedOkrDto getNestedOkr(@RequestBody ObjectiveDto objectiveDto){
        return okrService.getNestedOkr(objectiveDto);
    }
}
