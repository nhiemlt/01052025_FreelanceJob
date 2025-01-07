package com.java.project.dtos;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ThietKeDto {
    Integer id;
    String maThietKe;
    String tenThietKe;
    Short trangThai;
}