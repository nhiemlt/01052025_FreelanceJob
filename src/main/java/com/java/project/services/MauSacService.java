package com.java.project.services;

import com.java.project.dtos.MauSacDto;
import com.java.project.entities.MauSac;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.MauSacMapper;
import com.java.project.repositories.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    public Page<MauSacDto> getAll(String search, Pageable pageable) {
        Page<MauSac> mauSacs = mauSacRepository.findAllWithSearch(search, pageable);
        return mauSacs.map(MauSacMapper::toDTO);
    }

    @Transactional
    public MauSacDto addMauSac(MauSacDto mauSacDto) {
        if (mauSacRepository.findByTenMauSac(mauSacDto.getTenMauSac()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên màu sắc đã tồn tại");
        }
        if (mauSacRepository.findByMaHex(mauSacDto.getMaHex()).isPresent()) {
            throw new EntityAlreadyExistsException("Mã màu HEX đã tồn tại");
        }
        MauSac mauSac = new MauSac();
        mauSac.setMaHex(mauSacDto.getMaHex());
        mauSac.setTenMauSac(mauSacDto.getTenMauSac());
        mauSac.setTrangThai(mauSacDto.getTrangThai());
        mauSac = mauSacRepository.save(mauSac);
        return MauSacMapper.toDTO(mauSac);
    }

    @Transactional
    public MauSacDto updateMauSac(Integer id, MauSacDto mauSacDto) {
        MauSac mauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy màu sắc"));

        if (mauSacRepository.findByTenMauSac(mauSacDto.getTenMauSac()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên màu sắc đã tồn tại");
        }

        if (mauSacRepository.findByMaHex(mauSacDto.getMaHex()).isPresent()) {
            throw new EntityAlreadyExistsException("Mã màu HEX đã tồn tại");
        }

        mauSac.setMaHex(mauSacDto.getMaHex());
        mauSac.setTenMauSac(mauSacDto.getTenMauSac());
        mauSac.setTrangThai(mauSacDto.getTrangThai());
        mauSac = mauSacRepository.save(mauSac);
        return MauSacMapper.toDTO(mauSac);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        MauSac mauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy màu sắc"));
        mauSac.setTrangThai(!mauSac.getTrangThai());
        mauSacRepository.save(mauSac);
    }

    @Transactional
    public void deleteMauSac(Integer id) {
        MauSac mauSac = mauSacRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy màu sắc"));
        mauSacRepository.delete(mauSac);
    }
}

