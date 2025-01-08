package com.java.project.repositories;

import com.java.project.entities.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    @Query("SELECT sp FROM SanPham sp " +
            "LEFT JOIN sp.sanPhamChiTiets spct " +
            "WHERE (:keyword IS NULL OR LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(sp.maSanPham) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:danhMucId IS NULL OR sp.idDanhMuc.id = :danhMucId) " +
            "AND (:donGiaMin IS NULL OR spct.donGia >= :donGiaMin) " +
            "AND (:donGiaMax IS NULL OR spct.donGia <= :donGiaMax) " +
            "AND (:coAoId IS NULL OR spct.coAo.id = :coAoId) " +
            "AND (:thietKeId IS NULL OR spct.thietKe.id = :thietKeId) " +
            "AND (:thuongHieuId IS NULL OR spct.thuongHieu.id = :thuongHieuId) " +
            "AND (:kieuDangId IS NULL OR spct.kieuDang.id = :kieuDangId) " +
            "AND (:chatLieuId IS NULL OR spct.chatLieu.id = :chatLieuId) " +
            "AND (:kichThuocId IS NULL OR spct.kichThuoc.id = :kichThuocId) " +
            "AND (:mauSacId IS NULL OR spct.mauSac.id = :mauSacId)")
    Page<SanPham> getAll(
            String keyword,
            Integer danhMucId,
            BigDecimal donGiaMin,
            BigDecimal donGiaMax,
            Integer coAoId,
            Integer thietKeId,
            Integer thuongHieuId,
            Integer kieuDangId,
            Integer chatLieuId,
            Integer kichThuocId,
            Integer mauSacId,
            PageRequest pageRequest);

    boolean existsByMaSanPham(String maSP);

    boolean existsByTenSanPham(String tenSP);


}