package com.java.project.controllers;

import com.java.project.dtos.KichThuocDto;
import com.java.project.services.KichThuocService;
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
@RequestMapping("/api/kich-thuoc")
public class KichThuocController {

    @Autowired
    private KichThuocService kichThuocService;

    @GetMapping
    public ResponseEntity<Page<KichThuocDto>> getAllKichThuoc(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<KichThuocDto> kichThuocDtos = kichThuocService.getAll(search, pageable);
        return ResponseEntity.ok(kichThuocDtos);
    }

    @PostMapping
    public ResponseEntity<KichThuocDto> addKichThuoc(@Valid @RequestBody KichThuocDto kichThuocDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            KichThuocDto newKichThuoc = kichThuocService.addKichThuoc(kichThuocDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newKichThuoc);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<KichThuocDto> updateKichThuoc(@PathVariable Integer id,
                                                        @Valid @RequestBody KichThuocDto kichThuocDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            KichThuocDto updatedKichThuoc = kichThuocService.updateKichThuoc(id, kichThuocDto);
            return ResponseEntity.ok(updatedKichThuoc);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        kichThuocService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKichThuoc(@PathVariable Integer id) {
        kichThuocService.deleteKichThuoc(id);
        return ResponseEntity.noContent().build();
    }
}

