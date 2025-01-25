package com.java.project.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SanPhamChiTietGenerateModel {

    @NotNull(message = "Sản phẩm không được để trống.")
    private Integer sanPham;

    @NotNull(message = "Thương hiệu không được để trống.")
    private Integer[] thuongHieu;

    @NotNull(message = "Xuất xứ không được để trống.")
    private Integer[] xuatXu;

    @NotNull(message = "Chất liệu không được để trống.")
    private Integer[] chatLieu;

    @NotNull(message = "Cỡ áo không được để trống.")
    private Integer[] coAo;

    @NotNull(message = "Tay áo không được để trống.")
    private Integer[] tayAo;

    @NotNull(message = "Màu sắc không được để trống.")
    private Integer[] mauSac;

    @NotNull(message = "Kích thước không được để trống.")
    private Integer[] kichThuoc;
}

