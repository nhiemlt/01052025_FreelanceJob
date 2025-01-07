package com.java.project.mappers;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.entities.SanPhamChiTiet;

import java.util.Set;
import java.util.stream.Collectors;

public class SanPhamChiTietMapper {
    public static SanPhamChiTietDto toDTO(SanPhamChiTiet sanPhamChiTiet) {
        return SanPhamChiTietDto.builder()
                .id(sanPhamChiTiet.getId())
                .thongTinSanPham(ThongTinSanPhamMapper.toDTO(sanPhamChiTiet.getSanPham()))
                .soLuong(sanPhamChiTiet.getSoLuong())
                .donGia(sanPhamChiTiet.getDonGia())
                .ngayTao(sanPhamChiTiet.getNgayTao())
                .ngayCapNhat(sanPhamChiTiet.getNgayCapNhat())
                .nguoiCapNhat(sanPhamChiTiet.getNguoiCapNhat())
                .trangThai(sanPhamChiTiet.getTrangThai())
                .trongLuong(sanPhamChiTiet.getTrongLuong())
                .coAo(CoAoMapper.toDTO(sanPhamChiTiet.getCoAo()))
                .thietKe(ThietKeMapper.toDTO(sanPhamChiTiet.getThietKe()))
                .thuongHieu(ThuongHieuMapper.toDTO(sanPhamChiTiet.getThuongHieu()))
                .kieuDang(KieuDangMapper.toDTO(sanPhamChiTiet.getKieuDang()))
                .chatLieu(ChatLieuMapper.toDTO(sanPhamChiTiet.getChatLieu()))
                .kichThuoc(KichThuocMapper.toDTO(sanPhamChiTiet.getKichThuoc()))
                .mauSac(MauSacMapper.toDTO(sanPhamChiTiet.getMauSac()))
                .moTa(MoTaMapper.toDTO(sanPhamChiTiet.getMoTa()))
                .build();
    }

    public static Set<SanPhamChiTietDto> toDTOSet(Set<SanPhamChiTiet> sanPhamChiTiets) {
        return sanPhamChiTiets.stream()
                .map(SanPhamChiTietMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
