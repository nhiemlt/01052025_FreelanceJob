package com.java.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.project.entities.SanPham}
 */
@Data
@Builder
public class SanPhamDto implements Serializable {
    Integer id;
    @NotNull
    DanhMucDto danhMuc;
    @NotNull
    @Size(max = 255)
    String tenSanPham;

    Integer soLuong;
    @NotNull
    @Size(max = 50)
    String maSanPham;
    String moTa;
    @NotNull
    Boolean trangThai;
}