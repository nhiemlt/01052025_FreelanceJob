package com.java.project.services;

import com.java.project.dtos.XuatXuDto;
import com.java.project.entities.XuatXu;
import com.java.project.mappers.XuatXuMapper;
import com.java.project.repositories.XuatXuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class XuatXuService {

    @Autowired
    private XuatXuRepository xuatXuRepository;

    public Page<XuatXuDto> getAll(String search, Pageable pageable) {
        Page<XuatXu> xuatXus = xuatXuRepository.findAllWithSearch(search, pageable);
        return xuatXus.map(XuatXuMapper::toDTO);
    }

    @Transactional
    public XuatXuDto addXuatXu(XuatXuDto xuatXuDto) {
        if (xuatXuRepository.findByTenXuatXu(xuatXuDto.getTenXuatXu()).isPresent()) {
            throw new IllegalArgumentException("Xuất xứ đã tồn tại");
        }
        XuatXu xuatXu = new XuatXu();
        xuatXu.setTenXuatXu(xuatXuDto.getTenXuatXu());
        xuatXu.setTrangThai(xuatXuDto.getTrangThai());
        xuatXu = xuatXuRepository.save(xuatXu);
        return XuatXuMapper.toDTO(xuatXu);
    }

    @Transactional
    public XuatXuDto updateXuatXu(Integer id, XuatXuDto xuatXuDto) {
        XuatXu xuatXu = xuatXuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy xuất xứ"));

        if (xuatXuRepository.findByTenXuatXu(xuatXuDto.getTenXuatXu()).isPresent()) {
            throw new IllegalArgumentException("Xuất xứ đã tồn tại");
        }

        xuatXu.setTenXuatXu(xuatXuDto.getTenXuatXu());
        xuatXu.setTrangThai(xuatXuDto.getTrangThai());
        xuatXu = xuatXuRepository.save(xuatXu);
        return XuatXuMapper.toDTO(xuatXu);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        XuatXu xuatXu = xuatXuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy xuất xứ"));
        xuatXu.setTrangThai(!xuatXu.getTrangThai());
        xuatXuRepository.save(xuatXu);
    }

    @Transactional
    public void deleteXuatXu(Integer id) {
        XuatXu xuatXu = xuatXuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy xuất xứ"));
        xuatXuRepository.delete(xuatXu);
    }
}

