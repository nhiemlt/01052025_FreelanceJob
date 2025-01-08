package com.java.project.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SanPhamChiTietModel {

    @NotNull(message = "Sản phẩm không được null")
    @Positive(message = "ID sản phẩm phải là số nguyên dương")
    private Integer idSanPham;

    @NotNull(message = "Số lượng không được null")
    @Min(value = 1, message = "Số lượng phải ít nhất là 1")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá phải lớn hơn 0")
    private BigDecimal donGia;

    @Size(max = 255, message = "Người cập nhật không quá 255 ký tự")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ0-9 ]*$", message = "Người cập nhật chỉ được chứa ký tự chữ, số và khoảng trắng")
    private String nguoiCapNhat;

    @NotNull(message = "Trạng thái không được null")
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    @Max(value = 1, message = "Trạng thái không hợp lệ")
    private Short trangThai;

    @DecimalMin(value = "0.0", inclusive = true, message = "Trọng lượng phải là số không âm")
    private BigDecimal trongLuong;

    // Các trường danh sách ID thay vì các trường ID đơn
    @NotNull(message = "Danh sách mã sản phẩm không được null")
    @Size(min = 1, message = "Danh sách mã sản phẩm phải có ít nhất 1 phần tử")
    private List<Integer> idCoAoList;

    @NotNull(message = "Danh sách thiết kế không được null")
    @Size(min = 1, message = "Danh sách thiết kế phải có ít nhất 1 phần tử")
    private List<Integer> idThietKeList;

    @NotNull(message = "Danh sách thương hiệu không được null")
    @Size(min = 1, message = "Danh sách thương hiệu phải có ít nhất 1 phần tử")
    private List<Integer> idThuongHieuList;

    @NotNull(message = "Danh sách kiểu dáng không được null")
    @Size(min = 1, message = "Danh sách kiểu dáng phải có ít nhất 1 phần tử")
    private List<Integer> idKieuDangList;

    @NotNull(message = "Danh sách chất liệu không được null")
    @Size(min = 1, message = "Danh sách chất liệu phải có ít nhất 1 phần tử")
    private List<Integer> idChatLieuList;

    @NotNull(message = "Danh sách kích thước không được null")
    @Size(min = 1, message = "Danh sách kích thước phải có ít nhất 1 phần tử")
    private List<Integer> idKichThuocList;

    @NotNull(message = "Danh sách màu sắc không được null")
    @Size(min = 1, message = "Danh sách màu sắc phải có ít nhất 1 phần tử")
    private List<Integer> idMauSacList;

    @NotNull(message = "Danh sách mô tả không được null")
    @Size(min = 1, message = "Danh sách mô tả phải có ít nhất 1 phần tử")
    private List<Integer> idMoTaList;
}

