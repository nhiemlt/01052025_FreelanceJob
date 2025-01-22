package com.java.project.models;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DanhMuc {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String tenDanhMuc;
}
