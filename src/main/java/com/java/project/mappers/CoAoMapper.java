package com.java.project.mappers;

import com.java.project.dtos.CoAoDto;
import com.java.project.entities.CoAo;

public class CoAoMapper {
    public static CoAoDto toDTO(CoAo coAo) {
        return CoAoDto.builder()
                .id(coAo.getId())
                .maCoAo(coAo.getMaCoAo())
                .tenCoAo(coAo.getTenCoAo())
                .trangThai(coAo.getTrangThai())
                .build();
    }
}
