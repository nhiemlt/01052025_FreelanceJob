package com.java.project.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateSanPhamChiTietModel {
    @NotNull(message = "Số lượng không được để trống.")
    @Positive(message = "Số lượng phải là số dương.")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được để trống.")
    @Positive(message = "Đơn giá phải là số dương.")
    private BigDecimal donGia;

    private String hinhAnh;
}

