package com.java.project.services;

import com.java.project.dtos.KichThuocDto;
import com.java.project.entities.KichThuoc;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.KichThuocMapper;
import com.java.project.repositories.KichThuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KichThuocService {

    @Autowired
    private KichThuocRepository kichThuocRepository;

    public Page<KichThuocDto> getAll(String search, Pageable pageable) {
        Page<KichThuoc> kichThuocs = kichThuocRepository.findAllWithSearch(search, pageable);
        return kichThuocs.map(KichThuocMapper::toDTO);
    }

    @Transactional
    public KichThuocDto addKichThuoc(KichThuocDto kichThuocDto) {
        if (kichThuocRepository.findByTenKichThuoc(kichThuocDto.getTenKichThuoc()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên kích thước đã tồn tại");
        }
        KichThuoc kichThuoc = new KichThuoc();
        kichThuoc.setTenKichThuoc(kichThuocDto.getTenKichThuoc());
        kichThuoc.setTrangThai(kichThuocDto.getTrangThai());
        kichThuoc = kichThuocRepository.save(kichThuoc);
        return KichThuocMapper.toDTO(kichThuoc);
    }

    @Transactional
    public KichThuocDto updateKichThuoc(Integer id, KichThuocDto kichThuocDto) {
        KichThuoc kichThuoc = kichThuocRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kích thước"));

        if (kichThuocRepository.findByTenKichThuoc(kichThuocDto.getTenKichThuoc()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên kích thước đã tồn tại");
        }

        kichThuoc.setTenKichThuoc(kichThuocDto.getTenKichThuoc());
        kichThuoc.setTrangThai(kichThuocDto.getTrangThai());
        kichThuoc = kichThuocRepository.save(kichThuoc);
        return KichThuocMapper.toDTO(kichThuoc);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        KichThuoc kichThuoc = kichThuocRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kích thước"));
        kichThuoc.setTrangThai(!kichThuoc.getTrangThai());
        kichThuocRepository.save(kichThuoc);
    }

    @Transactional
    public void deleteKichThuoc(Integer id) {
        KichThuoc kichThuoc = kichThuocRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kích thước"));
        kichThuocRepository.delete(kichThuoc);
    }
}

