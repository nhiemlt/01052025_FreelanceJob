package com.java.project.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SanPhamChiTietModel {

    @NotNull(message = "Sản phẩm không được null")
    private Integer idSanPham;

    @NotNull(message = "Số lượng không được null")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được null")
    private BigDecimal donGia;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người cập nhật không quá 255 ký tự")
    private String nguoiCapNhat;

    @NotNull(message = "Trạng thái không được null")
    private Short trangThai;

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
