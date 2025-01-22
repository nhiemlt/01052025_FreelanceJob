package com.java.project.controllers;

import com.java.project.dtos.TayAoDto;
import com.java.project.services.TayAoService;
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
@RequestMapping("/api/tay-ao")
public class TayAoController {

    @Autowired
    private TayAoService tayAoService;

    @GetMapping
    public ResponseEntity<Page<TayAoDto>> getAllTayAo(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction))));
        Page<TayAoDto> tayAoDtos = tayAoService.getAll(search, pageable);
        return ResponseEntity.ok(tayAoDtos);
    }

    @PostMapping
    public ResponseEntity<TayAoDto> addTayAo(@Valid @RequestBody TayAoDto tayAoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            TayAoDto newTayAo = tayAoService.addTayAo(tayAoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTayAo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TayAoDto> updateTayAo(@PathVariable Integer id,
                                                @Valid @RequestBody TayAoDto tayAoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            TayAoDto updatedTayAo = tayAoService.updateTayAo(id, tayAoDto);
            return ResponseEntity.ok(updatedTayAo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTrangThai(@PathVariable Integer id) {
        tayAoService.toggleTrangThai(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTayAo(@PathVariable Integer id) {
        tayAoService.deleteTayAo(id);
        return ResponseEntity.noContent().build();
    }
}

