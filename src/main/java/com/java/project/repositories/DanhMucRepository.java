package com.java.project.repositories;

import com.java.project.entities.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    @Query("SELECT d FROM DanhMuc d WHERE d.tenDanhMuc LIKE %:search% OR d.id = :search")
    Page<DanhMuc> findAllWithSearch(String search, Pageable pageable);

    // Kiểm tra tên có tồn tại hay không
    Optional<DanhMuc> findByTenDanhMuc(String tenDanhMuc);
}