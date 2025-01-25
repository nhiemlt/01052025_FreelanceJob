package com.java.project.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class SanPham {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSanPham;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSanPham;

    private String moTa;
}
