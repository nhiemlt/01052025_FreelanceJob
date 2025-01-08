package com.java.project.controllers;

import com.java.project.dtos.SanPhamDto;
import com.java.project.exceptions.DuplicateKeyException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.models.SanPhamModel;
import com.java.project.services.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/san-pham")
@Validated
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping
    public ResponseEntity<?> createSanPham(@Valid @RequestBody SanPhamModel sanPhamModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            SanPhamDto sanPhamDto = sanPhamService.createSanPham(sanPhamModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(sanPhamDto);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình xử lý.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSanPham(@PathVariable Integer id,
                                           @Valid @RequestBody SanPhamModel sanPhamModel,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            SanPhamDto sanPhamDto = sanPhamService.updateSanPham(id, sanPhamModel);
            return ResponseEntity.ok(sanPhamDto);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình xử lý.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSanPhamById(@PathVariable Integer id) {
        try {
            SanPhamDto sanPhamDto = sanPhamService.getSanPhamById(id);
            return new ResponseEntity<>(sanPhamDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Đã sảy ra lỗi trong quá trình xử lí", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<Page<SanPhamDto>> getAllSanPham(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer danhMucId,
            @RequestParam(required = false) BigDecimal donGiaMin,
            @RequestParam(required = false) BigDecimal donGiaMax,
            @RequestParam(required = false) Integer coAoId,
            @RequestParam(required = false) Integer thietKeId,
            @RequestParam(required = false) Integer thuongHieuId,
            @RequestParam(required = false) Integer kieuDangId,
            @RequestParam(required = false) Integer chatLieuId,
            @RequestParam(required = false) Integer kichThuocId,
            @RequestParam(required = false) Integer mauSacId) {

        Page<SanPhamDto> sanPhamDtos = sanPhamService.getAllSanPham(
                page, size, sortBy, direction, keyword, danhMucId, donGiaMin, donGiaMax,
                coAoId, thietKeId, thuongHieuId, kieuDangId, chatLieuId, kichThuocId, mauSacId);

        return new ResponseEntity<>(sanPhamDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSanPham(@PathVariable Integer id) {
        try {
            sanPhamService.deleteSanPham(id);
            return new ResponseEntity<>("Sản phẩm đã được xóa thành công.", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
