package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.java.project.entities.MauSac}
 */
@Data
@Builder
public class MauSacDto implements Serializable {
    Integer id;
    String maHex;
    String tenMauSac;
    Boolean trangThai;
}