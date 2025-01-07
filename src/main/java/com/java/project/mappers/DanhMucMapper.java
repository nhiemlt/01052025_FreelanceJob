package com.java.project.mappers;

import com.java.project.dtos.DanhMucDto;
import com.java.project.entities.DanhMuc;

public class DanhMucMapper {
    public static DanhMucDto toDTO(DanhMuc danhMuc) {
        return DanhMucDto.builder()
                .id(danhMuc.getId())
                .maDanhMuc(danhMuc.getMaDanhMuc())
                .tenDanhMuc(danhMuc.getTenDanhMuc())
                .trangThai(danhMuc.getTrangThai())
                .build();
    }
}
