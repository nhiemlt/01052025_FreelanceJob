package com.java.project.controllers;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.exceptions.DuplicateKeyException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.models.SanPhamChiTietSingleModel;
import com.java.project.services.SanPhamChiTietService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @PostMapping
    public ResponseEntity<?> createSanPhamChiTiet(@Valid @RequestBody SanPhamChiTietModel sanPhamChiTietModel,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            sanPhamChiTietService.createSanPhamChiTiet(sanPhamChiTietModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sản phẩm chi tiết đã được tạo thành công.");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Lỗi: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình xử lý.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSanPhamChiTiet(@PathVariable Integer id,
                                                  @Valid @RequestBody SanPhamChiTietSingleModel sanPhamChiTietModel,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            SanPhamChiTietDto sanPhamChiTietDto = sanPhamChiTietService.updateSanPhamChiTiet(id, sanPhamChiTietModel);
            return ResponseEntity.ok(sanPhamChiTietDto);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình xử lý.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamChiTietDto> getSanPhamChiTietById(@PathVariable Integer id) {
        try {
            SanPhamChiTietDto sanPhamChiTietDto = sanPhamChiTietService.getSanPhamChiTietById(id);
            return new ResponseEntity<>(sanPhamChiTietDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bySanPhamId")
    public ResponseEntity<Page<SanPhamChiTietDto>> getSanPhamChiTietBySanPhamId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam Integer idSanPham) {
        try {
            Page<SanPhamChiTietDto> sanPhamChiTietDtos = sanPhamChiTietService.getSanPhamChiTietBySanPhamId(
                    page, size, sortBy, direction, idSanPham);
            return new ResponseEntity<>(sanPhamChiTietDtos, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Page.empty(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Page.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<Page<SanPhamChiTietDto>> getAllSanPhamChiTiet(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer idSanPham,
            @RequestParam(required = false) Integer coAoId,
            @RequestParam(required = false) Integer thietKeId,
            @RequestParam(required = false) Integer thuongHieuId,
            @RequestParam(required = false) Integer kieuDangId,
            @RequestParam(required = false) Integer chatLieuId,
            @RequestParam(required = false) Integer kichThuocId,
            @RequestParam(required = false) Integer mauSacId,
            @RequestParam(required = false) BigDecimal donGiaMin,
            @RequestParam(required = false) BigDecimal donGiaMax) {

        Page<SanPhamChiTietDto> sanPhamChiTietDtos = sanPhamChiTietService.getAllSanPhamChiTiet(
                page, size, sortBy, direction, keyword, idSanPham,
                coAoId, thietKeId, thuongHieuId, kieuDangId, chatLieuId,
                kichThuocId, mauSacId, donGiaMin, donGiaMax);

        return new ResponseEntity<>(sanPhamChiTietDtos, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSanPhamChiTiet(@PathVariable Integer id) {
        try {
            sanPhamChiTietService.deleteSanPhamChiTiet(id);
            return ResponseEntity.ok("Xóa chi tiết sản phẩm thành công.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Chi tiết sản phẩm không tồn tại.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Không thể xóa chi tiết sản phẩm vì đang được tham chiếu ở nơi khác.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi trong quá trình xử lý.");
        }
    }

}
