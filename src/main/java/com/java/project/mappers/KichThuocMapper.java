package com.java.project.mappers;

import com.java.project.dtos.KichThuocDto;
import com.java.project.entities.KichThuoc;

public class KichThuocMapper {
    public static KichThuocDto toDTO(KichThuoc kichThuoc) {
        return KichThuocDto.builder()
                .id(kichThuoc.getId())
                .maKichThuoc(kichThuoc.getMaKichThuoc())
                .tenKichThuoc(kichThuoc.getTenKichThuoc())
                .trangThai(kichThuoc.getTrangThai())
                .build();
    }
}
