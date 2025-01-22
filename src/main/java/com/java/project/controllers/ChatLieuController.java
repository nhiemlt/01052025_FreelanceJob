package com.java.project.controllers;

import com.java.project.dtos.ChatLieuDto;
import com.java.project.services.ChatLieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-lieu")
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService;

    // API lấy tất cả dữ liệu ChatLieu với phân trang, tìm kiếm và sắp xếp
    @GetMapping
    public ResponseEntity<Page<ChatLieuDto>> getAllChatLieu(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        // Tạo Pageable từ PageRequest
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<ChatLieuDto> chatLieuDtos = chatLieuService.getAll(search, pageable);
        return ResponseEntity.ok(chatLieuDtos);
    }

    // API thêm ChatLieu mới
    @PostMapping
    public ResponseEntity<ChatLieuDto> addChatLieu(@Valid @RequestBody ChatLieuDto chatLieuDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        ChatLieuDto newChatLieu = chatLieuService.addChatLieu(chatLieuDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newChatLieu);
    }

    // API cập nhật ChatLieu
    @PutMapping("/{id}")
    public ResponseEntity<ChatLieuDto> updateChatLieu(@PathVariable Integer id,
                                                      @Valid @RequestBody ChatLieuDto chatLieuDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        ChatLieuDto updatedChatLieu = chatLieuService.updateChatLieu(id, chatLieuDto);
        return ResponseEntity.ok(updatedChatLieu);
    }

    // API toggle trạng thái của ChatLieu
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        chatLieuService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    // API xóa ChatLieu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatLieu(@PathVariable Integer id) {
        chatLieuService.deleteChatLieu(id);
        return ResponseEntity.noContent().build();
    }
}
