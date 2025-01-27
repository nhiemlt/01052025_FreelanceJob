package com.java.project.services;

import com.java.project.dtos.SanPhamDto;
import com.java.project.entities.SanPham;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.SanPhamMapper;
import com.java.project.models.SanPhamModel;
import com.java.project.repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    public Page<SanPhamDto> getAllSanPham(String search, Pageable pageable) {
        Page<SanPham> sanPhams = sanPhamRepository.searchByNameOrCode(search, pageable);
        return sanPhams.map(sanPham -> SanPhamMapper.toDTO(sanPham, sanPhamRepository.countSLByID(sanPham.getId())));
    }

    @Transactional
    public SanPhamDto getById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm"));
        int soLuong = sanPhamRepository.countSLByID(sanPham.getId());
        return SanPhamMapper.toDTO(sanPham, soLuong);
    }


    @Transactional
    public SanPhamDto createSanPham(SanPhamModel sanPhamModel) {
        // Kiểm tra trùng tên sản phẩm
        Optional<SanPham> existingSanPhamByName = sanPhamRepository.findByTenSanPham(sanPhamModel.getTenSanPham());
        if (existingSanPhamByName.isPresent()) {
            throw new EntityAlreadyExistsException("Tên sản phẩm đã tồn tại.");
        }

        // Kiểm tra trùng mã sản phẩm
        Optional<SanPham> existingSanPhamByCode = sanPhamRepository.findByMaSanPham(sanPhamModel.getMaSanPham());
        if (existingSanPhamByCode.isPresent()) {
            throw new EntityAlreadyExistsException("Mã sản phẩm đã tồn tại.");
        }

        SanPham sanPham = new SanPham();
        sanPham.setTenSanPham(sanPhamModel.getTenSanPham());
        sanPham.setMaSanPham(sanPhamModel.getMaSanPham());
        sanPham.setMoTa(sanPhamModel.getMoTa());
        sanPham.setNgayTao(Instant.now());
        sanPham.setTrangThai(true);

        sanPhamRepository.save(sanPham);

        return SanPhamMapper.toDTO(sanPham, sanPhamRepository.countSLByID(sanPham.getId()));
    }

    @Transactional
    public SanPhamDto updateSanPham(Integer id, SanPhamModel sanPhamModel) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));

        // Kiểm tra trùng tên sản phẩm khi cập nhật
        Optional<SanPham> existingSanPhamByName = sanPhamRepository.findByTenSanPham(sanPhamModel.getTenSanPham());
        if (existingSanPhamByName.isPresent() && !existingSanPhamByName.get().getId().equals(id)) {
            throw new EntityAlreadyExistsException("Tên sản phẩm đã tồn tại.");
        }

        // Kiểm tra trùng mã sản phẩm khi cập nhật
        Optional<SanPham> existingSanPhamByCode = sanPhamRepository.findByMaSanPham(sanPhamModel.getMaSanPham());
        if (existingSanPhamByCode.isPresent() && !existingSanPhamByCode.get().getId().equals(id)) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại.");
        }

        sanPham.setTenSanPham(sanPhamModel.getTenSanPham());
        sanPham.setMaSanPham(sanPhamModel.getMaSanPham());
        sanPham.setMoTa(sanPhamModel.getMoTa());

        sanPhamRepository.save(sanPham);

        return SanPhamMapper.toDTO(sanPham, sanPhamRepository.countSLByID(sanPham.getId()));
    }

    public SanPhamDto toggleTrangThai(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));

        sanPham.setTrangThai(!sanPham.getTrangThai());
        sanPhamRepository.save(sanPham);

        return SanPhamMapper.toDTO(sanPham, sanPhamRepository.countSLByID(sanPham.getId()));
    }

    public void deleteSanPham(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));

        sanPhamRepository.delete(sanPham);
    }
}
