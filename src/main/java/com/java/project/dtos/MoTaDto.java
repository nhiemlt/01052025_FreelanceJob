package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MoTaDto{
    Integer id;
    String noiDungMoTa;
    String hinhAnhMoTa;
}