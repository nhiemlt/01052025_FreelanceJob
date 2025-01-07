package com.java.project.mappers;

import com.java.project.dtos.MoTaDto;
import com.java.project.entities.MoTa;

public class MoTaMapper {
    public static MoTaDto toDTO(MoTa moTa) {
        return MoTaDto.builder()
                .id(moTa.getId())
                .noiDungMoTa(moTa.getNoiDungMoTa())
                .hinhAnhMoTa(moTa.getHinhAnhMoTa())
                .build();
    }
}
