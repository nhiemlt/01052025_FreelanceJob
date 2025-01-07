package com.java.project.repositories;

import com.java.project.entities.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Integer> {
    @Query("SELECT s FROM SanPhamChiTiet s WHERE (:keyword IS NULL OR s.sanPham.tenSanPham LIKE %:keyword% OR s.coAo.tenCoAo LIKE %:keyword% OR s.thietKe.tenThietKe LIKE %:keyword% OR s.thuongHieu.tenThuongHieu LIKE %:keyword%) " +
            "AND (:idSanPham IS NULL OR s.sanPham.id = :idSanPham)")
    Page<SanPhamChiTiet> findAll(
            @Param("keyword") String keyword,
            @Param("idSanPham") Integer idSanPham,
            Pageable pageable);
}