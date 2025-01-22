package com.java.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.project.entities.DanhMuc}
 */
@Builder
public class DanhMucDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String tenDanhMuc;
    @NotNull
    Boolean trangThai;
}