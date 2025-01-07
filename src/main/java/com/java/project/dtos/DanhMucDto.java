package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DanhMucDto{
    Integer id;
    String maDanhMuc;
    String tenDanhMuc;
    Short trangThai;
}