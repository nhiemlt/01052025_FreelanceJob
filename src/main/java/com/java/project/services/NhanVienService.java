package com.java.project.services;

import com.java.project.entities.NhanVien;
import com.java.project.repositories.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;

    public List<NhanVien>getAll(){
        return nhanVienRepository.findAll();
    }

    public NhanVien getNhaVienByTenDangNhap(String tenDangNhap) {
        return nhanVienRepository.findByTenDangNhap(tenDangNhap)
                .orElseThrow(()->new RuntimeException("Không tìm thấy nhân viên !"));
    }
}
