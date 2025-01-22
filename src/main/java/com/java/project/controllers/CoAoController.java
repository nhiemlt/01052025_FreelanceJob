package com.java.project.controllers;

import com.java.project.dtos.CoAoDto;
import com.java.project.services.CoAoService;
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
@RequestMapping("/api/co-ao")
public class CoAoController {

    @Autowired
    private CoAoService coAoService;

    // API lấy tất cả dữ liệu CoAo với phân trang, tìm kiếm và sắp xếp
    @GetMapping
    public ResponseEntity<Page<CoAoDto>> getAllCoAo(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        // Tạo Pageable từ PageRequest
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<CoAoDto> coAoDtos = coAoService.getAll(search, pageable);
        return ResponseEntity.ok(coAoDtos);
    }

    // API thêm CoAo mới
    @PostMapping
    public ResponseEntity<CoAoDto> addCoAo(@Valid @RequestBody CoAoDto coAoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            CoAoDto newCoAo = coAoService.addCoAo(coAoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCoAo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // API cập nhật CoAo
    @PutMapping("/{id}")
    public ResponseEntity<CoAoDto> updateCoAo(@PathVariable Integer id,
                                              @Valid @RequestBody CoAoDto coAoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            CoAoDto updatedCoAo = coAoService.updateCoAo(id, coAoDto);
            return ResponseEntity.ok(updatedCoAo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // API toggle trạng thái của CoAo
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        coAoService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    // API xóa CoAo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoAo(@PathVariable Integer id) {
        coAoService.deleteCoAo(id);
        return ResponseEntity.noContent().build();
    }
}
