package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KieuDangDto{
    Integer id;
    String maKieuDang;
    String tenKieuDang;
    Short trangThai;
}