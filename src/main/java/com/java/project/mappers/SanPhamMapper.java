package com.java.project.mappers;

import com.java.project.dtos.SanPhamDto;
import com.java.project.entities.SanPham;

public class SanPhamMapper {
    public static SanPhamDto toDTO(SanPham sanPham) {
        return SanPhamDto.builder()
                .id(sanPham.getId())
                .danhMuc(DanhMucMapper.toDTO(sanPham.getDanhMuc()))
                .tenSanPham(sanPham.getTenSanPham())
                .maSanPham(sanPham.getMaSanPham())
                .moTa(sanPham.getMoTa())
                .trangThai(sanPham.getTrangThai())
                .build();
    }
}

