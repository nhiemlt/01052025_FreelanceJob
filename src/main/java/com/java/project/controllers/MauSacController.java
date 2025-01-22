package com.java.project.controllers;

import com.java.project.dtos.MauSacDto;
import com.java.project.services.MauSacService;
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
@RequestMapping("/api/mau-sac")
public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    @GetMapping
    public ResponseEntity<Page<MauSacDto>> getAllMauSac(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<MauSacDto> mauSacDtos = mauSacService.getAll(search, pageable);
        return ResponseEntity.ok(mauSacDtos);
    }

    @PostMapping
    public ResponseEntity<MauSacDto> addMauSac(@Valid @RequestBody MauSacDto mauSacDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            MauSacDto newMauSac = mauSacService.addMauSac(mauSacDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMauSac);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MauSacDto> updateMauSac(@PathVariable Integer id,
                                                  @Valid @RequestBody MauSacDto mauSacDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            MauSacDto updatedMauSac = mauSacService.updateMauSac(id, mauSacDto);
            return ResponseEntity.ok(updatedMauSac);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        mauSacService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMauSac(@PathVariable Integer id) {
        mauSacService.deleteMauSac(id);
        return ResponseEntity.noContent().build();
    }
}

