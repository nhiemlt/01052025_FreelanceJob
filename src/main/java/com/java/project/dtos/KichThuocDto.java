package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KichThuocDto{
    Integer id;
    String maKichThuoc;
    String tenKichThuoc;
    Short trangThai;
}