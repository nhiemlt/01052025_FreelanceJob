package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoAoDto {
    Integer id;
    String maCoAo;
    String tenCoAo;
    Short trangThai;
}