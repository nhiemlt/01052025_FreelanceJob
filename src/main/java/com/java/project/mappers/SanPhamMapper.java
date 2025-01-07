package com.java.project.mappers;

import com.java.project.dtos.SanPhamDto;
import com.java.project.entities.SanPham;

public class SanPhamMapper {
    public static SanPhamDto toDTO(SanPham sanPham) {
        return SanPhamDto.builder()
                .id(sanPham.getId())
                .DanhMuc(DanhMucMapper.toDTO(sanPham.getIdDanhMuc()))
                .tenSanPham(sanPham.getTenSanPham())
                .maSanPham(sanPham.getMaSanPham())
                .moTa(sanPham.getMoTa())
                .trangThai(sanPham.getTrangThai())
                .sanPhamChiTiets(SanPhamChiTietMapper.toDTOSet(sanPham.getSanPhamChiTiets()))
                .build();
    }
}

