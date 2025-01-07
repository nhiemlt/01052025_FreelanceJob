package com.java.project.mappers;

import com.java.project.dtos.ThietKeDto;
import com.java.project.entities.ThietKe;

public class ThietKeMapper {
    public static ThietKeDto toDTO(ThietKe thietKe) {
        return ThietKeDto.builder()
                .id(thietKe.getId())
                .maThietKe(thietKe.getMaThietKe())
                .tenThietKe(thietKe.getTenThietKe())
                .trangThai(thietKe.getTrangThai())
                .build();
    }
}
