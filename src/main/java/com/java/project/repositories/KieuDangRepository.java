package com.java.project.repositories;

import com.java.project.entities.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KieuDangRepository extends JpaRepository<KieuDang, Integer> {
}