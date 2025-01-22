package com.java.project.controllers;

import com.java.project.dtos.ThuongHieuDto;
import com.java.project.services.ThuongHieuService;
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
@RequestMapping("/api/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping
    public ResponseEntity<Page<ThuongHieuDto>> getAllThuongHieu(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<ThuongHieuDto> thuongHieuDtos = thuongHieuService.getAll(search, pageable);
        return ResponseEntity.ok(thuongHieuDtos);
    }

    @PostMapping
    public ResponseEntity<ThuongHieuDto> addThuongHieu(@Valid @RequestBody ThuongHieuDto thuongHieuDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            ThuongHieuDto newThuongHieu = thuongHieuService.addThuongHieu(thuongHieuDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newThuongHieu);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuongHieuDto> updateThuongHieu(@PathVariable Integer id,
                                                          @Valid @RequestBody ThuongHieuDto thuongHieuDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            ThuongHieuDto updatedThuongHieu = thuongHieuService.updateThuongHieu(id, thuongHieuDto);
            return ResponseEntity.ok(updatedThuongHieu);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        thuongHieuService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuongHieu(@PathVariable Integer id) {
        thuongHieuService.deleteThuongHieu(id);
        return ResponseEntity.noContent().build();
    }
}

