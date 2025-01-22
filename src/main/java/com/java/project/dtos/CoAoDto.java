package com.java.project.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.java.project.entities.CoAo}
 */
@Data
@Builder
public class CoAoDto implements Serializable {
    Integer id;
    String tenCoAo;
    Boolean trangThai;
}