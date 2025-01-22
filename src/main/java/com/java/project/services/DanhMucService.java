package com.java.project.services;

import com.java.project.dtos.DanhMucDto;
import com.java.project.entities.DanhMuc;
import com.java.project.mappers.DanhMucMapper;
import com.java.project.repositories.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    // Lấy tất cả dữ liệu với phân trang, sắp xếp và tìm kiếm
    public Page<DanhMucDto> getAll(String search, Pageable pageable) {
        Page<DanhMuc> danhMucs = danhMucRepository.findAllWithSearch(search, pageable);
        return danhMucs.map(DanhMucMapper::toDTO);
    }

    // Thêm DanhMuc mới
    @Transactional
    public DanhMucDto addDanhMuc(DanhMucDto danhMucDto) {
        // Kiểm tra trùng tên
        if (danhMucRepository.findByTenDanhMuc(danhMucDto.getTenDanhMuc()).isPresent()) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        }
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setTenDanhMuc(danhMucDto.getTenDanhMuc());
        danhMuc.setTrangThai(danhMucDto.getTrangThai());
        danhMuc = danhMucRepository.save(danhMuc);
        return DanhMucMapper.toDTO(danhMuc);
    }

    // Cập nhật DanhMuc
    @Transactional
    public DanhMucDto updateDanhMuc(Integer id, DanhMucDto danhMucDto) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));

        // Kiểm tra trùng tên
        if (danhMucRepository.findByTenDanhMuc(danhMucDto.getTenDanhMuc()).isPresent()) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại");
        }

        danhMuc.setTenDanhMuc(danhMucDto.getTenDanhMuc());
        danhMuc.setTrangThai(danhMucDto.getTrangThai());
        danhMuc = danhMucRepository.save(danhMuc);
        return DanhMucMapper.toDTO(danhMuc);
    }

    // Toggle trạng thái
    @Transactional
    public void toggleTrangThai(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
        danhMuc.setTrangThai(!danhMuc.getTrangThai());
        danhMucRepository.save(danhMuc);
    }

    // Xóa DanhMuc
    @Transactional
    public void deleteDanhMuc(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
        danhMucRepository.delete(danhMuc);
    }
}

