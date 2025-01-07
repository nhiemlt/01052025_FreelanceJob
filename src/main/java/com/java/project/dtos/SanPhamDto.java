package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Builder
@Data
public class SanPhamDto {
    Integer id;
    DanhMucDto DanhMuc;
    String tenSanPham;
    String maSanPham;
    String moTa;
    Short trangThai;
    Set<SanPhamChiTietDto> sanPhamChiTiets;
}