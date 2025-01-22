package com.java.project.models;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class XuatXu {
    @NotBlank(message = "Xuất xứ không được để trống")
    private String tenXuatXu;
}
