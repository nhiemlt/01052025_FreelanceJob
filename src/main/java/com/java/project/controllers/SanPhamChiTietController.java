package com.java.project.controllers;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.dtos.SanPhamChiTietPhanLoaiDTO;
import com.java.project.models.SanPhamChiTietGenerateModel;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.services.SanPhamChiTietService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @PostMapping
    public ResponseEntity<?> createSanPhamChiTiet(@RequestBody @Valid List<SanPhamChiTietModel> sanPhamChiTietModels, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Tạo danh sách lỗi và trả về 400 nếu có lỗi xác thực
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            List<SanPhamChiTietDto> result = sanPhamChiTietService.createSanPhamChiTietList(sanPhamChiTietModels);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi trong service
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSanPhamChiTiet(@PathVariable Integer id, @RequestBody @Valid SanPhamChiTietModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            SanPhamChiTietDto result = sanPhamChiTietService.updateSanPhamChiTiet(id, model);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSanPhamChiTiet(@RequestParam(required = false) String search,
                                                  @RequestParam(required = false) List<Integer> thuongHieuIds,
                                                  @RequestParam(required = false) List<Integer> xuatXuIds,
                                                  @RequestParam(required = false) List<Integer> chatLieuIds,
                                                  @RequestParam(required = false) List<Integer> coAoIds,
                                                  @RequestParam(required = false) List<Integer> tayAoIds,
                                                  @RequestParam(required = false) List<Integer> mauSacIds,
                                                  @RequestParam(required = false) List<Integer> kichThuocIds,
                                                  Pageable pageable) {
        try {
            Page<SanPhamChiTietDto> result = sanPhamChiTietService.getAllSanPhamChiTiet(
                    search, thuongHieuIds, xuatXuIds, chatLieuIds, coAoIds, tayAoIds, mauSacIds, kichThuocIds, pageable);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/toggle-trang-thai")
    public ResponseEntity<?> toggleTrangThai(@PathVariable Integer id) {
        try {
            SanPhamChiTietDto result = sanPhamChiTietService.toggleTrangThai(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSanPhamChiTiet(@PathVariable Integer id) {
        try {
            sanPhamChiTietService.deleteSanPhamChiTiet(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateSanPhamChiTiet(@RequestBody SanPhamChiTietGenerateModel generateModel) {
        try {
            List<SanPhamChiTietPhanLoaiDTO> result = sanPhamChiTietService.generateSanPhamChiTietGroupedByMauSac(generateModel);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
    }
}
