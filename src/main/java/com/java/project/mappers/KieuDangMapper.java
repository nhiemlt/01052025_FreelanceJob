package com.java.project.mappers;

import com.java.project.dtos.KieuDangDto;
import com.java.project.entities.KieuDang;

public class KieuDangMapper {
    public static KieuDangDto toDTO(KieuDang kieuDang) {
        return KieuDangDto.builder()
                .id(kieuDang.getId())
                .maKieuDang(kieuDang.getMaKieuDang())
                .tenKieuDang(kieuDang.getTenKieuDang())
                .trangThai(kieuDang.getTrangThai())
                .build();
    }
}
