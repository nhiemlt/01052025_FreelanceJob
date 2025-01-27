package com.java.project.repositories;

import com.java.project.entities.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {

    @Query("SELECT s FROM SanPhamChiTiet s " +
            "WHERE (:search IS NULL OR s.sanPham.tenSanPham LIKE %:search% OR s.sanPham.maSanPham LIKE %:search%) " +
            "AND (:thuongHieuIds IS NULL OR s.thuongHieu.id IN :thuongHieuIds) " +
            "AND (:xuatXuIds IS NULL OR s.xuatXu.id IN :xuatXuIds) " +
            "AND (:chatLieuIds IS NULL OR s.chatLieu.id IN :chatLieuIds) " +
            "AND (:coAoIds IS NULL OR s.coAo.id IN :coAoIds) " +
            "AND (:tayAoIds IS NULL OR s.tayAo.id IN :tayAoIds) " +
            "AND (:mauSacIds IS NULL OR s.mauSac.id IN :mauSacIds) " +
            "AND (:kichThuocIds IS NULL OR s.kichThuoc.id IN :kichThuocIds)")
    Page<SanPhamChiTiet> findBySearchAndFilter(
            @Param("search") String search,
            @Param("thuongHieuIds") List<Integer> thuongHieuIds,
            @Param("xuatXuIds") List<Integer> xuatXuIds,
            @Param("chatLieuIds") List<Integer> chatLieuIds,
            @Param("coAoIds") List<Integer> coAoIds,
            @Param("tayAoIds") List<Integer> tayAoIds,
            @Param("mauSacIds") List<Integer> mauSacIds,
            @Param("kichThuocIds") List<Integer> kichThuocIds,
            Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTiet s WHERE s.sanPham.id = :sanPhamId " +
            "AND s.thuongHieu.id = :thuongHieuId " +
            "AND s.xuatXu.id = :xuatXuId " +
            "AND s.chatLieu.id = :chatLieuId " +
            "AND s.coAo.id = :coAoId " +
            "AND s.tayAo.id = :tayAoId " +
            "AND s.mauSac.id = :mauSacId " +
            "AND s.kichThuoc.id = :kichThuocId")
    SanPhamChiTiet findBySanPhamAndThuongHieuAndXuatXuAndChatLieuAndCoAoAndTayAoAndMauSacAndKichThuoc(
            Integer sanPhamId, Integer thuongHieuId, Integer xuatXuId, Integer chatLieuId, Integer coAoId,
            Integer tayAoId, Integer mauSacId, Integer kichThuocId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM SanPhamChiTiet s WHERE s.sanPham.id = :sanPhamId " +
            "AND s.thuongHieu.id = :thuongHieuId " +
            "AND s.xuatXu.id = :xuatXuId " +
            "AND s.chatLieu.id = :chatLieuId " +
            "AND s.coAo.id = :coAoId " +
            "AND s.tayAo.id = :tayAoId " +
            "AND s.mauSac.id = :mauSacId " +
            "AND s.kichThuoc.id = :kichThuocId")
    boolean existsBySanPhamAndThuongHieuAndXuatXuAndChatLieuAndCoAoAndTayAoAndMauSacAndKichThuoc(
            Integer sanPhamId, Integer thuongHieuId, Integer xuatXuId, Integer chatLieuId, Integer coAoId,
            Integer tayAoId, Integer mauSacId, Integer kichThuocId);

    List<SanPhamChiTiet> findBySanPhamId(Integer sanPhamId);
}