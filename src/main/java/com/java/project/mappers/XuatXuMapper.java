package com.java.project.mappers;

import com.java.project.dtos.XuatXuDto;
import com.java.project.entities.XuatXu;

public class XuatXuMapper {
    public static XuatXuDto toDTO(XuatXu thuongHieu) {
        return XuatXuDto.builder()
                .id(thuongHieu.getId())
                .tenXuatXu(thuongHieu.getTenXuatXu())
                .trangThai(thuongHieu.getTrangThai())
                .build();
    }
}
