package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SanPhamChiTietPhanLoaiDTO implements Serializable {
    Integer maMauSac;
    String tenMauSac;
    List<SanPhamChiTietGenerateDTO> sanPhamChiTiet;
}