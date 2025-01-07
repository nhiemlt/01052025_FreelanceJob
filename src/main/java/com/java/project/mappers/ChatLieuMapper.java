package com.java.project.mappers;

import com.java.project.dtos.ChatLieuDto;
import com.java.project.entities.ChatLieu;

public class ChatLieuMapper {
    public static ChatLieuDto toDTO(ChatLieu chatLieu) {
        return ChatLieuDto.builder()
                .id(chatLieu.getId())
                .maChatLieu(chatLieu.getMaChatLieu())
                .tenChatLieu(chatLieu.getTenChatLieu())
                .trangThai(chatLieu.getTrangThai())
                .build();
    }
}

