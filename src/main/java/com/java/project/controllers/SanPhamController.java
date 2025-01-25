package com.java.project.controllers;

import com.java.project.dtos.SanPhamDto;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.services.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping
    public ResponseEntity<?> createSanPham(@Valid @RequestBody SanPhamDto sanPhamDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Thông tin sản phẩm không hợp lệ.");
            }

            SanPhamDto createdSanPham = sanPhamService.createSanPham(sanPhamDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSanPham);

        } catch (EntityAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSanPham(@PathVariable Integer id, @Valid @RequestBody SanPhamDto sanPhamDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Thông tin sản phẩm không hợp lệ.");
            }

            SanPhamDto updatedSanPham = sanPhamService.updateSanPham(id, sanPhamDto);
            return ResponseEntity.ok(updatedSanPham);

        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (EntityAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<SanPhamDto>> getAllSanPham(
            @RequestParam(required = false, defaultValue = "") String search,
            Pageable pageable) {
        try {
            Page<SanPhamDto> sanPhams = sanPhamService.getAllSanPham(search, pageable);
            return ResponseEntity.ok(sanPhams);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/toggle-trang-thai/{id}")
    public ResponseEntity<?> toggleTrangThai(@PathVariable Integer id) {
        try {
            SanPhamDto sanPhamDto = sanPhamService.toggleTrangThai(id);
            return ResponseEntity.ok(sanPhamDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSanPham(@PathVariable Integer id) {
        try {
            sanPhamService.deleteSanPham(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + ex.getMessage());
        }
    }
}

