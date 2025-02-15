package com.java.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuGiamGiaKhachHangResponse {

    private Integer id;
    private String tenKhachHang;
    private String tenPhieuGiamGia;
    private Byte trangThai;
    private Date ngayTao;
    private Date ngaySua;
    private Integer soLuong;
}
