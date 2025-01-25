package com.java.project.controllers;

import com.java.project.dtos.ChatLieuDto;
import com.java.project.services.ChatLieuService;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
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

    @GetMapping
    public ResponseEntity<Page<ChatLieuDto>> getAllChatLieu(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
            Page<ChatLieuDto> chatLieuDtos = chatLieuService.getAll(search, pageable);
            return ResponseEntity.ok(chatLieuDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // API thêm ChatLieu mới
    @PostMapping
    public ResponseEntity<ChatLieuDto> addChatLieu(@Valid @RequestBody ChatLieuDto chatLieuDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(null);
            }
            ChatLieuDto newChatLieu = chatLieuService.addChatLieu(chatLieuDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newChatLieu);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // API cập nhật ChatLieu
    @PutMapping("/{id}")
    public ResponseEntity<ChatLieuDto> updateChatLieu(@PathVariable Integer id,
                                                      @Valid @RequestBody ChatLieuDto chatLieuDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(null);
            }
            ChatLieuDto updatedChatLieu = chatLieuService.updateChatLieu(id, chatLieuDto);
            return ResponseEntity.ok(updatedChatLieu);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // API toggle trạng thái của ChatLieu
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        try {
            chatLieuService.toggleTrangThai(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // API xóa ChatLieu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatLieu(@PathVariable Integer id) {
        try {
            chatLieuService.deleteChatLieu(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
