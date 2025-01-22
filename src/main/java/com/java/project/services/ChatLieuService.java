package com.java.project.services;

import com.java.project.dtos.ChatLieuDto;
import com.java.project.entities.ChatLieu;
import com.java.project.mappers.ChatLieuMapper;
import com.java.project.repositories.ChatLieuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChatLieuService {


    @Autowired
    private ChatLieuRepository chatLieuRepository;

    // Lấy tất cả ChatLieu với phân trang, tìm kiếm và sắp xếp
    public Page<ChatLieuDto> getAll(String search, Pageable pageable) {
        Page<ChatLieu> page = chatLieuRepository.findByIdOrTenChatLieu(search, pageable);
        return page.map(ChatLieuMapper::toDTO);
    }

    // Thêm mới ChatLieu (kiểm tra trùng tên)
    @Transactional
    public ChatLieuDto addChatLieu(ChatLieuDto chatLieuDto) {
        if (chatLieuRepository.existsByTenChatLieu(chatLieuDto.getTenChatLieu())) {
            throw new IllegalArgumentException("Tên chất liệu đã tồn tại");
        }
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setTenChatLieu(chatLieuDto.getTenChatLieu());
        chatLieu.setTrangThai(chatLieuDto.getTrangThai());
        chatLieu = chatLieuRepository.save(chatLieu);
        return ChatLieuMapper.toDTO(chatLieu);
    }

    // Cập nhật ChatLieu (kiểm tra trùng tên)
    @Transactional
    public ChatLieuDto updateChatLieu(Integer id, ChatLieuDto chatLieuDto) {
        if (chatLieuRepository.existsByTenChatLieu(chatLieuDto.getTenChatLieu())) {
            throw new IllegalArgumentException("Tên chất liệu đã tồn tại");
        }
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại"));

        chatLieu.setTenChatLieu(chatLieuDto.getTenChatLieu());
        chatLieu.setTrangThai(chatLieuDto.getTrangThai());
        chatLieu = chatLieuRepository.save(chatLieu);

        return ChatLieuMapper.toDTO(chatLieu);
    }

    // Toggle trạng thái của ChatLieu (mở hoặc tắt trạng thái)
    @Transactional
    public void toggleTrangThai(Integer id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại"));
        chatLieu.setTrangThai(!chatLieu.getTrangThai());
        chatLieuRepository.save(chatLieu);
    }

    // Xóa ChatLieu
    @Transactional
    public void deleteChatLieu(Integer id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại"));
        chatLieuRepository.delete(chatLieu);
    }
}
