package com.java.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.project.entities.MauSac}
 */
@Builder
public class MauSacDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 7)
    String maHex;
    @NotNull
    @Size(max = 255)
    String tenMauSac;
    @NotNull
    Boolean trangThai;
}