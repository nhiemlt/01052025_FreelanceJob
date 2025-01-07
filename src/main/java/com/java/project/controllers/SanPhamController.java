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

@RestController
@RequestMapping("/api/san-pham")
@Validated
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping
    public ResponseEntity<Object> createSanPham(@Valid @RequestBody SanPhamModel sanPhamModel, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
                return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
            }

            SanPhamDto sanPhamDto = sanPhamService.createSanPham(sanPhamModel);
            return new ResponseEntity<>(sanPhamDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi hệ thống: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSanPham(@PathVariable Integer id, @Valid @RequestBody SanPhamModel sanPhamModel, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
                return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
            }

            SanPhamDto sanPhamDto = sanPhamService.updateSanPham(id, sanPhamModel);
            return new ResponseEntity<>(sanPhamDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi hệ thống: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDto> getSanPhamById(@PathVariable Integer id) {
        SanPhamDto sanPhamDto = sanPhamService.getSanPhamById(id);
        return new ResponseEntity<>(sanPhamDto, HttpStatus.OK);
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
