package com.java.project.models;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PhieuGiamGiaCreateModel {
    @NotBlank(message = "Mã phiếu giảm giá không được để trống")
    @Size(max = 50, message = "Mã phiếu giảm giá không được quá 50 ký tự")
    private String maPhieuGiamGia;

    @NotBlank(message = "Tên phiếu giảm giá không được để trống")
    @Size(max = 255, message = "Tên phiếu giảm giá không được quá 255 ký tự")
    private String tenPhieuGiamGia;

    @FutureOrPresent(message = "Thời gian áp dụng phải là hiện tại hoặc tương lai")
    private LocalDateTime thoiGianApDung;

    @Future(message = "Thời gian hết hạn phải ở tương lai")
    private LocalDateTime thoiGianHetHan;

    @NotNull(message = "Giá trị giảm không được để trống")
    @Positive(message = "Giá trị giảm phải là số dương")
    private Double giaTriGiam;

    @NotNull(message = "Số tiền tối thiểu hóa đơn không được để trống")
    @Positive(message = "Số tiền tối thiểu hóa đơn phải là số dương")
    private Double soTienToiThieuHd;

    @PositiveOrZero(message = "Số tiền giảm tối đa phải là số dương hoặc bằng 0")
    private Double soTienGiamToiDa;

    @NotNull(message = "Loại giảm không được để trống")
    @Min(value = 0, message = "Loại giảm chỉ có thể là 0 hoặc 1")
    @Max(value = 1, message = "Loại giảm chỉ có thể là 0 hoặc 1")
    private Integer loaiGiam;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private Integer soLuong;

    List<Integer> khachHangId;
}
