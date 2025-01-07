package com.java.project.mappers;

import com.java.project.dtos.ThuongHieuDto;
import com.java.project.entities.ThuongHieu;

public class ThuongHieuMapper {
    public static ThuongHieuDto toDTO(ThuongHieu thuongHieu) {
        return ThuongHieuDto.builder()
                .id(thuongHieu.getId())
                .maThuongHieu(thuongHieu.getMaThuongHieu())
                .tenThuongHieu(thuongHieu.getTenThuongHieu())
                .trangThai(thuongHieu.getTrangThai())
                .build();
    }
}
