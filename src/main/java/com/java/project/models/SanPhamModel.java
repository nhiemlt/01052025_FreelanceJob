package com.java.project.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SanPhamModel {

    @NotNull(message = "Danh mục không được null")
    @Positive(message = "ID danh mục phải là số nguyên dương")
    private Integer idDanhMuc;

    @NotNull(message = "Tên sản phẩm không được null")
    @Size(max = 255, message = "Tên sản phẩm không quá 255 ký tự")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ0-9 ]*$", message = "Tên sản phẩm chỉ được chứa ký tự chữ, số và khoảng trắng")
    private String tenSanPham;

    @NotNull(message = "Mã sản phẩm không được null")
    @Size(max = 50, message = "Mã sản phẩm không quá 50 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Mã sản phẩm chỉ được chứa ký tự chữ, số, dấu gạch dưới (_) hoặc dấu gạch ngang (-)")
    private String maSanPham;

    @Size(max = 255, message = "Mô tả không quá 255 ký tự")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ0-9,. ]*$", message = "Mô tả chỉ được chứa ký tự chữ, số, dấu phẩy, dấu chấm và khoảng trắng")
    private String moTa;

    @NotNull(message = "Trạng thái không được null")
    @Min(value = 0, message = "Trạng thái phải là 0 hoặc 1")
    @Max(value = 1, message = "Trạng thái phải là 0 hoặc 1")
    private Short trangThai;
}

