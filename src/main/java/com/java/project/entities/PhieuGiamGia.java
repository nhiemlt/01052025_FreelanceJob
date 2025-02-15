package com.java.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phieu_giam_gia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_phieu_giam_gia")
    private String maPhieuGiamGia;

    @Column(name = "ten_phieu_giam_gia")
    private String tenPhieuGiamGia;

    @Column(name = "hinh_thuc_phieu_giam_gia")
    private Integer hinhThucPhieuGiamGia;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "thoi_gian_ap_dung")
    private Date thoiGianApDung;

    @Column(name = "thoi_gian_het_han")
    private Date thoiGianHetHan;

    @Column(name = "gia_tri_giam")
    private Double giaTriGiam;

    @Column(name = "so_tien_toi_thieu_hd")
    private Double soTienToiThieuHD;

    @Column(name = "so_tien_giam_toi_da")
    private Double soTienGiamToiDa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "loai_giam")
    private Integer loaiGiam;
}
