package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SanPhamChiTietGenerateDTO {
    private Integer sanPham;

    private Integer thuongHieu;

    private Integer xuatXu;

    private Integer chatLieu;

    private Integer coAo;

    private Integer tayAo;

    private Integer mauSac;

    private Integer kichThuoc;

    private String tenKichThuoc;

    private Integer soLuong = 10;

    private BigDecimal donGia = BigDecimal.valueOf(50000);

    private String hinhAnh = null;
}

