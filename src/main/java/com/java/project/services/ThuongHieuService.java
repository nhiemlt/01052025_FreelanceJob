package com.java.project.services;

import com.java.project.dtos.ThuongHieuDto;
import com.java.project.entities.ThuongHieu;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.ThuongHieuMapper;
import com.java.project.repositories.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    public Page<ThuongHieuDto> getAll(String search, Pageable pageable) {
        Page<ThuongHieu> thuongHieus = thuongHieuRepository.findAllWithSearch(search, pageable);
        return thuongHieus.map(ThuongHieuMapper::toDTO);
    }

    @Transactional
    public ThuongHieuDto addThuongHieu(ThuongHieuDto thuongHieuDto) {
        if (thuongHieuRepository.findByTenThuongHieu(thuongHieuDto.getTenThuongHieu()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên thương hiệu đã tồn tại");
        }
        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setTenThuongHieu(thuongHieuDto.getTenThuongHieu());
        thuongHieu.setTrangThai(thuongHieuDto.getTrangThai());
        thuongHieu = thuongHieuRepository.save(thuongHieu);
        return ThuongHieuMapper.toDTO(thuongHieu);
    }

    @Transactional
    public ThuongHieuDto updateThuongHieu(Integer id, ThuongHieuDto thuongHieuDto) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));

        if (thuongHieuRepository.findByTenThuongHieu(thuongHieuDto.getTenThuongHieu()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên thương hiệu đã tồn tại");
        }

        thuongHieu.setTenThuongHieu(thuongHieuDto.getTenThuongHieu());
        thuongHieu.setTrangThai(thuongHieuDto.getTrangThai());
        thuongHieu = thuongHieuRepository.save(thuongHieu);
        return ThuongHieuMapper.toDTO(thuongHieu);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        thuongHieu.setTrangThai(!thuongHieu.getTrangThai());
        thuongHieuRepository.save(thuongHieu);
    }

    @Transactional
    public void deleteThuongHieu(Integer id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        thuongHieuRepository.delete(thuongHieu);
    }
}
