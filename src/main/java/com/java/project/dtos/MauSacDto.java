package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MauSacDto{
    Integer id;
    String maMauSac;
    String tenMauSac;
    Short trangThai;
}