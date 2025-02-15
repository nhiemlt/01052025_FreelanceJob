package com.java.project.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.java.project.entities.ChatLieu}
 */
@Value
public class ChatLieuDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String tenChatLieu;
    @NotNull
    Boolean trangThai;
    @NotNull
    Instant ngayTao;
}