package com.java.project.mappers;

import com.java.project.dtos.ThongTinSanPhamDto;
import com.java.project.entities.SanPham;

public class ThongTinSanPhamMapper {
    public static ThongTinSanPhamDto toDTO(SanPham thongTinSanPham) {
        return ThongTinSanPhamDto.builder()
                .id(thongTinSanPham.getId())
                .tenSanPham(thongTinSanPham.getTenSanPham())
                .maSanPham(thongTinSanPham.getMaSanPham())
                .moTa(thongTinSanPham.getMoTa())
                .build();
    }
}
