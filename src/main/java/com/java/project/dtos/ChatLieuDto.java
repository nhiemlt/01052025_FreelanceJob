package com.java.project.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatLieuDto{
    Integer id;
    String maChatLieu;
    String tenChatLieu;
    Short trangThai;
}