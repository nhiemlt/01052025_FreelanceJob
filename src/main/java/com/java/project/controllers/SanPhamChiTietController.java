package com.java.project.controllers;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.dtos.SanPhamChiTietPhanLoaiDTO;
import com.java.project.models.SanPhamChiTietGenerateModel;
import com.java.project.services.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @PostMapping
    public ResponseEntity<List<SanPhamChiTietDto>> createSanPhamChiTiet(@RequestBody List<SanPhamChiTietDto> sanPhamChiTietDtos) {
        List<SanPhamChiTietDto> result = sanPhamChiTietService.createSanPhamChiTietList(sanPhamChiTietDtos);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamChiTietDto> updateSanPhamChiTiet(
            @PathVariable Integer id,
            @RequestBody SanPhamChiTietDto sanPhamChiTietDto) {
        SanPhamChiTietDto result = sanPhamChiTietService.updateSanPhamChiTiet(id, sanPhamChiTietDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Page<SanPhamChiTietDto>> getAllSanPhamChiTiet(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<Integer> thuongHieuIds,
            @RequestParam(required = false) List<Integer> xuatXuIds,
            @RequestParam(required = false) List<Integer> chatLieuIds,
            @RequestParam(required = false) List<Integer> coAoIds,
            @RequestParam(required = false) List<Integer> tayAoIds,
            @RequestParam(required = false) List<Integer> mauSacIds,
            @RequestParam(required = false) List<Integer> kichThuocIds,
            Pageable pageable) {
        Page<SanPhamChiTietDto> result = sanPhamChiTietService.getAllSanPhamChiTiet(
                search, thuongHieuIds, xuatXuIds, chatLieuIds, coAoIds, tayAoIds, mauSacIds, kichThuocIds, pageable);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/toggle-trang-thai")
    public ResponseEntity<SanPhamChiTietDto> toggleTrangThai(@PathVariable Integer id) {
        SanPhamChiTietDto result = sanPhamChiTietService.toggleTrangThai(id);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPhamChiTiet(@PathVariable Integer id) {
        sanPhamChiTietService.deleteSanPhamChiTiet(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Generate sản phẩm chi tiết và nhóm theo màu sắc
     */
    @PostMapping("/generate")
    public ResponseEntity<List<SanPhamChiTietPhanLoaiDTO>> generateSanPhamChiTiet(@RequestBody SanPhamChiTietGenerateModel generateModel) {
        List<SanPhamChiTietPhanLoaiDTO> result = sanPhamChiTietService.generateSanPhamChiTietGroupedByMauSac(generateModel);
        return ResponseEntity.ok(result);
    }
}
