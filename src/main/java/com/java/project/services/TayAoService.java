package com.java.project.services;

import com.java.project.dtos.TayAoDto;
import com.java.project.entities.TayAo;
import com.java.project.mappers.TayAoMapper;
import com.java.project.repositories.TayAoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TayAoService {

    @Autowired
    private TayAoRepository tayAoRepository;

    public Page<TayAoDto> getAll(String search, Pageable pageable) {
        Page<TayAo> tayAos = tayAoRepository.findAllWithSearch(search, pageable);
        return tayAos.map(TayAoMapper::toDTO);
    }

    @Transactional
    public TayAoDto addTayAo(TayAoDto tayAoDto) {
        if (tayAoRepository.findByTenTayAo(tayAoDto.getTenTayAo()).isPresent()) {
            throw new IllegalArgumentException("Tên tay áo đã tồn tại");
        }
        TayAo tayAo = new TayAo();
        tayAo.setTenTayAo(tayAoDto.getTenTayAo());
        tayAo.setTrangThai(tayAoDto.getTrangThai());
        tayAo = tayAoRepository.save(tayAo);
        return TayAoMapper.toDTO(tayAo);
    }

    @Transactional
    public TayAoDto updateTayAo(Integer id, TayAoDto tayAoDto) {
        TayAo tayAo = tayAoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tay áo"));

        if (tayAoRepository.findByTenTayAo(tayAoDto.getTenTayAo()).isPresent()) {
            throw new IllegalArgumentException("Tên tay áo đã tồn tại");
        }

        tayAo.setTenTayAo(tayAoDto.getTenTayAo());
        tayAo.setTrangThai(tayAoDto.getTrangThai());
        tayAo = tayAoRepository.save(tayAo);
        return TayAoMapper.toDTO(tayAo);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        TayAo tayAo = tayAoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tay áo"));
        tayAo.setTrangThai(!tayAo.getTrangThai());
        tayAoRepository.save(tayAo);
    }

    @Transactional
    public void deleteTayAo(Integer id) {
        TayAo tayAo = tayAoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tay áo"));
        tayAoRepository.delete(tayAo);
    }
}

