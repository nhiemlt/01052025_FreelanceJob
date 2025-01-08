package com.java.project.repositories;

import com.java.project.entities.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {
    @Query("SELECT s FROM SanPhamChiTiet s " +
            "WHERE (:keyword IS NULL OR s.sanPham.tenSanPham LIKE %:keyword% " +
            "OR s.coAo.tenCoAo LIKE %:keyword% " +
            "OR s.thietKe.tenThietKe LIKE %:keyword% " +
            "OR s.thuongHieu.tenThuongHieu LIKE %:keyword%) " +
            "AND (:idSanPham IS NULL OR s.sanPham.id = :idSanPham) " +
            "AND (:coAoId IS NULL OR s.coAo.id = :coAoId) " +
            "AND (:thietKeId IS NULL OR s.thietKe.id = :thietKeId) " +
            "AND (:thuongHieuId IS NULL OR s.thuongHieu.id = :thuongHieuId) " +
            "AND (:kieuDangId IS NULL OR s.kieuDang.id = :kieuDangId) " +
            "AND (:chatLieuId IS NULL OR s.chatLieu.id = :chatLieuId) " +
            "AND (:kichThuocId IS NULL OR s.kichThuoc.id = :kichThuocId) " +
            "AND (:mauSacId IS NULL OR s.mauSac.id = :mauSacId) " +
            "AND (:donGiaMin IS NULL OR s.donGia >= :donGiaMin) " +
            "AND (:donGiaMax IS NULL OR s.donGia <= :donGiaMax)")
    Page<SanPhamChiTiet> findAll(
            @Param("keyword") String keyword,
            @Param("idSanPham") Integer idSanPham,
            @Param("coAoId") Integer coAoId,
            @Param("thietKeId") Integer thietKeId,
            @Param("thuongHieuId") Integer thuongHieuId,
            @Param("kieuDangId") Integer kieuDangId,
            @Param("chatLieuId") Integer chatLieuId,
            @Param("kichThuocId") Integer kichThuocId,
            @Param("mauSacId") Integer mauSacId,
            @Param("donGiaMin") BigDecimal donGiaMin,
            @Param("donGiaMax") BigDecimal donGiaMax,
            Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :idSanPham")
    Page<SanPhamChiTiet> findBySanPhamIdPageable(@Param("idSanPham") Integer idSanPham, Pageable pageable);

    boolean existsBySanPhamIdAndCoAoIdAndThietKeIdAndThuongHieuIdAndKieuDangIdAndChatLieuIdAndKichThuocIdAndMauSacIdAndMoTaId(
            Integer idSanPham, Integer idCoAo, Integer idThietKe, Integer idThuongHieu,
            Integer idKieuDang, Integer idChatLieu, Integer idKichThuoc, Integer idMauSac, Integer idMoTa);
}