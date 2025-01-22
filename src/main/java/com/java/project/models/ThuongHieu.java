package com.java.project.models;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ThuongHieu {
    @NotBlank(message = "Tên thương hiệu không được để trống")
    private String tenThuongHieu;
}
