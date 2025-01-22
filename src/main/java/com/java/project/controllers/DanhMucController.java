package com.java.project.controllers;

import com.java.project.dtos.DanhMucDto;
import com.java.project.services.DanhMucService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/danh-muc")
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;

    // API lấy tất cả dữ liệu DanhMuc với phân trang, tìm kiếm và sắp xếp
    @GetMapping
    public ResponseEntity<Page<DanhMucDto>> getAllDanhMuc(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        // Tạo Pageable từ PageRequest
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<DanhMucDto> danhMucDtos = danhMucService.getAll(search, pageable);
        return ResponseEntity.ok(danhMucDtos);
    }

    // API thêm DanhMuc mới
    @PostMapping
    public ResponseEntity<DanhMucDto> addDanhMuc(@Valid @RequestBody DanhMucDto danhMucDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            DanhMucDto newDanhMuc = danhMucService.addDanhMuc(danhMucDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDanhMuc);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // API cập nhật DanhMuc
    @PutMapping("/{id}")
    public ResponseEntity<DanhMucDto> updateDanhMuc(@PathVariable Integer id,
                                                    @Valid @RequestBody DanhMucDto danhMucDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            DanhMucDto updatedDanhMuc = danhMucService.updateDanhMuc(id, danhMucDto);
            return ResponseEntity.ok(updatedDanhMuc);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // API toggle trạng thái của DanhMuc
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        danhMucService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    // API xóa DanhMuc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Integer id) {
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.noContent().build();
    }
}

