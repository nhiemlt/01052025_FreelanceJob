package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ThuongHieuDto {
    Integer id;
    String maThuongHieu;
    String tenThuongHieu;
    Short trangThai;
}