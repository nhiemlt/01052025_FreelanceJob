package com.java.project.dtos;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.java.project.entities.SanPhamChiTiet}
 */
@Builder
public class SanPhamChiTietDto implements Serializable {
    Integer id;
    SanPhamDto sanPham;
    ThuongHieuDto thuongHieu;
    XuatXuDto xuatXu;
    ChatLieuDto chatLieu;
    CoAoDto coAo;
    TayAoDto tayAo;
    MauSacDto mauSac;
    KichThuocDto kichThuoc;
    Integer soLuong;
    BigDecimal donGia;
    String hinhAnh;
    Instant ngayTao;
    Boolean trangThai;
}