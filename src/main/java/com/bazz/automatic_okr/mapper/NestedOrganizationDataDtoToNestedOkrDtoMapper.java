package com.bazz.automatic_okr.mapper;

import com.bazz.automatic_okr.dto.NestedOkrDto;
import com.bazz.automatic_okr.dto.NestedOrganizationDataDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class NestedOrganizationDataDtoToNestedOkrDtoMapper implements OneToOneMapper <NestedOrganizationDataDto, NestedOkrDto>{

    @Override
    public NestedOkrDto map(NestedOrganizationDataDto nestedOrganizationDataDto) {
        return NestedOkrDto.builder()
                .organizationId(nestedOrganizationDataDto.getOrganizationId())
                .organizationName(nestedOrganizationDataDto.getOrganizationName())
                .childNestedOkrs(mapChildNestedOkrs(nestedOrganizationDataDto.getChildOrganizationsData()))
                .build();
    }

    private Set<NestedOkrDto> mapChildNestedOkrs(Set<NestedOrganizationDataDto> childOrganizationsData) {
        Set<NestedOkrDto> ChildNestedOkrs = new HashSet<>();
        if(Objects.isNull(childOrganizationsData) || childOrganizationsData.isEmpty()){
            return null;
        }
        childOrganizationsData.forEach(child -> {
            NestedOkrDto nestedOkrDto = this.map(child);
            ChildNestedOkrs.add(nestedOkrDto);
        });
        return ChildNestedOkrs;
    }

    @Override
    public NestedOrganizationDataDto reverseMap(NestedOkrDto nestedOkrDto) {
        return null;
    }
}
