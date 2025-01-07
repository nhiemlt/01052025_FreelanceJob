package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class SanPhamChiTietDto{
    Integer id;
    ThongTinSanPhamDto thongTinSanPham;
    Integer soLuong;
    BigDecimal donGia;
    LocalDate ngayTao;
    LocalDate ngayCapNhat;
    String nguoiCapNhat;
    Short trangThai;
    BigDecimal trongLuong;
    CoAoDto coAo;
    ThietKeDto thietKe;
    ThuongHieuDto thuongHieu;
    KieuDangDto kieuDang;
    ChatLieuDto chatLieu;
    KichThuocDto kichThuoc;
    MauSacDto mauSac;
    MoTaDto moTa;
}