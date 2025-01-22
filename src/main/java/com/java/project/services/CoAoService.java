package com.java.project.services;

import com.java.project.dtos.CoAoDto;
import com.java.project.entities.CoAo;
import com.java.project.mappers.CoAoMapper;
import com.java.project.repositories.CoAoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoAoService {

    @Autowired
    private CoAoRepository coAoRepository;

    // Lấy tất cả dữ liệu với phân trang, sắp xếp và tìm kiếm
    public Page<CoAoDto> getAll(String search, Pageable pageable) {
        Page<CoAo> coAos = coAoRepository.findAllWithSearch(search, pageable);
        return coAos.map(CoAoMapper::toDTO);
    }

    // Thêm CoAo mới
    @Transactional
    public CoAoDto addCoAo(CoAoDto coAoDto) {
        // Kiểm tra trùng tên
        if (coAoRepository.findByTenCoAo(coAoDto.getTenCoAo()).isPresent()) {
            throw new IllegalArgumentException("Tên cổ áo đã tồn tại");
        }
        CoAo coAo = new CoAo();
        coAo.setTenCoAo(coAoDto.getTenCoAo());
        coAo.setTrangThai(coAoDto.getTrangThai());
        coAo = coAoRepository.save(coAo);
        return CoAoMapper.toDTO(coAo);
    }

    // Cập nhật CoAo
    @Transactional
    public CoAoDto updateCoAo(Integer id, CoAoDto coAoDto) {
        CoAo coAo = coAoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy cổ áo"));

        // Kiểm tra trùng tên
        if (coAoRepository.findByTenCoAo(coAoDto.getTenCoAo()).isPresent()) {
            throw new IllegalArgumentException("Tên cổ áo đã tồn tại");
        }

        coAo.setTenCoAo(coAoDto.getTenCoAo());
        coAo.setTrangThai(coAoDto.getTrangThai());
        coAo = coAoRepository.save(coAo);
        return CoAoMapper.toDTO(coAo);
    }

    // Toggle trạng thái
    @Transactional
    public void toggleTrangThai(Integer id) {
        CoAo coAo = coAoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy cổ áo"));
        coAo.setTrangThai(!coAo.getTrangThai());
        coAoRepository.save(coAo);
    }

    // Xóa CoAo
    @Transactional
    public void deleteCoAo(Integer id) {
        CoAo coAo = coAoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy cổ áo"));
        coAoRepository.delete(coAo);
    }
}
