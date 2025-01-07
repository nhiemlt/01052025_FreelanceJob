package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ThongTinSanPhamDto{
    Integer id;
    String tenSanPham;
    String maSanPham;
    String moTa;
}