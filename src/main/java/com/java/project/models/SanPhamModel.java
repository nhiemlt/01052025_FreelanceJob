package com.java.project.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SanPhamModel {

    @NotNull(message = "Danh mục không được null")
    private Integer idDanhMuc;

    @NotNull(message = "Tên sản phẩm không được null")
    @Size(max = 255, message = "Tên sản phẩm không quá 255 ký tự")
    private String tenSanPham;

    @NotNull(message = "Mã sản phẩm không được null")
    @Size(max = 50, message = "Mã sản phẩm không quá 50 ký tự")
    private String maSanPham;

    @Size(max = 255, message = "Mô tả không quá 255 ký tự")
    private String moTa;

    @NotNull(message = "Trạng thái không được null")
    private Short trangThai;
}
