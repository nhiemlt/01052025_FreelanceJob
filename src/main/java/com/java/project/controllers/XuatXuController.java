package com.java.project.controllers;

import com.java.project.dtos.XuatXuDto;
import com.java.project.services.XuatXuService;
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
@RequestMapping("/api/xuat-xu")
public class XuatXuController {

    @Autowired
    private XuatXuService xuatXuService;

    @GetMapping
    public ResponseEntity<Page<XuatXuDto>> getAllXuatXu(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<XuatXuDto> xuatXuDtos = xuatXuService.getAll(search, pageable);
        return ResponseEntity.ok(xuatXuDtos);
    }

    @PostMapping
    public ResponseEntity<XuatXuDto> addXuatXu(@Valid @RequestBody XuatXuDto xuatXuDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            XuatXuDto newXuatXu = xuatXuService.addXuatXu(xuatXuDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newXuatXu);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<XuatXuDto> updateXuatXu(@PathVariable Integer id,
                                                  @Valid @RequestBody XuatXuDto xuatXuDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            XuatXuDto updatedXuatXu = xuatXuService.updateXuatXu(id, xuatXuDto);
            return ResponseEntity.ok(updatedXuatXu);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        xuatXuService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteXuatXu(@PathVariable Integer id) {
        xuatXuService.deleteXuatXu(id);
        return ResponseEntity.noContent().build();
    }
}

