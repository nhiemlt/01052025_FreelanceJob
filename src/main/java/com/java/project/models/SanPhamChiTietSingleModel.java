package com.java.project.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SanPhamChiTietSingleModel {
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

    @NotNull(message = "Mã sản phẩm không được null")
    private Integer idCoAo;

    @NotNull(message = "Thiết kế không được null")
    private Integer idThietKe;

    @NotNull(message = "Thương hiệu không được null")
    private Integer idThuongHieu;

    @NotNull(message = "Kiểu dáng không được null")
    private Integer idKieuDang;

    @NotNull(message = "Chất liệu không được null")
    private Integer idChatLieu;

    @NotNull(message = "Kích thước không được null")
    private Integer idKichThuoc;

    @NotNull(message = "Màu sắc không được null")
    private Integer idMauSac;

    @NotNull(message = "Mô tả không được null")
    private Integer idMoTa;
}



