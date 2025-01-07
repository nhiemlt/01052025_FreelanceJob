package com.java.project.controllers;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.services.SanPhamChiTietService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    // Tạo chi tiết sản phẩm mới
    @PostMapping
    public ResponseEntity<SanPhamChiTietDto> createSanPhamChiTiet(@Valid @RequestBody SanPhamChiTietModel sanPhamChiTietModel) {
        SanPhamChiTietDto sanPhamChiTietDto = sanPhamChiTietService.createSanPhamChiTiet(sanPhamChiTietModel);
        return new ResponseEntity<>(sanPhamChiTietDto, HttpStatus.CREATED);
    }

    // Cập nhật chi tiết sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<SanPhamChiTietDto> updateSanPhamChiTiet(@PathVariable Integer id,
                                                                  @Valid @RequestBody SanPhamChiTietModel sanPhamChiTietModel) {
        SanPhamChiTietDto sanPhamChiTietDto = sanPhamChiTietService.updateSanPhamChiTiet(id, sanPhamChiTietModel);
        return new ResponseEntity<>(sanPhamChiTietDto, HttpStatus.OK);
    }

    // Lấy chi tiết sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SanPhamChiTietDto> getSanPhamChiTietById(@PathVariable Integer id) {
        SanPhamChiTietDto sanPhamChiTietDto = sanPhamChiTietService.getSanPhamChiTietById(id);
        return new ResponseEntity<>(sanPhamChiTietDto, HttpStatus.OK);
    }

    // Lấy tất cả chi tiết sản phẩm với phân trang và tìm kiếm
    @GetMapping
    public ResponseEntity<Page<SanPhamChiTietDto>> getAllSanPhamChiTiet(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer idSanPham) {
        Page<SanPhamChiTietDto> sanPhamChiTietDtos = sanPhamChiTietService.getAllSanPhamChiTiet(
                page, size, sortBy, direction, keyword, idSanPham);
        return new ResponseEntity<>(sanPhamChiTietDtos, HttpStatus.OK);
    }

    // Xóa chi tiết sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPhamChiTiet(@PathVariable Integer id) {
        sanPhamChiTietService.deleteSanPhamChiTiet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
