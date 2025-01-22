package com.java.project.services;

import com.java.project.dtos.ChatLieuDto;
import com.java.project.entities.ChatLieu;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.ChatLieuMapper;
import com.java.project.repositories.ChatLieuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChatLieuService {


    @Autowired
    private ChatLieuRepository chatLieuRepository;

    public Page<ChatLieuDto> getAll(String search, Pageable pageable) {
        Page<ChatLieu> page = chatLieuRepository.findByIdOrTenChatLieu(search, pageable);
        return page.map(ChatLieuMapper::toDTO);
    }

    @Transactional
    public ChatLieuDto addChatLieu(ChatLieuDto chatLieuDto) {
        if (chatLieuRepository.existsByTenChatLieu(chatLieuDto.getTenChatLieu())) {
            throw new EntityAlreadyExistsException("Tên chất liệu đã tồn tại");
        }
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setTenChatLieu(chatLieuDto.getTenChatLieu());
        chatLieu.setTrangThai(chatLieuDto.getTrangThai());
        chatLieu = chatLieuRepository.save(chatLieu);
        return ChatLieuMapper.toDTO(chatLieu);
    }

    @Transactional
    public ChatLieuDto updateChatLieu(Integer id, ChatLieuDto chatLieuDto) {
        if (chatLieuRepository.existsByTenChatLieu(chatLieuDto.getTenChatLieu())) {
            throw new EntityAlreadyExistsException("Tên chất liệu đã tồn tại");
        }
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chất liệu không tồn tại"));

        chatLieu.setTenChatLieu(chatLieuDto.getTenChatLieu());
        chatLieu.setTrangThai(chatLieuDto.getTrangThai());
        chatLieu = chatLieuRepository.save(chatLieu);

        return ChatLieuMapper.toDTO(chatLieu);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chất liệu không tồn tại"));
        chatLieu.setTrangThai(!chatLieu.getTrangThai());
        chatLieuRepository.save(chatLieu);
    }

    @Transactional
    public void deleteChatLieu(Integer id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chất liệu không tồn tại"));
        chatLieuRepository.delete(chatLieu);
    }
}
