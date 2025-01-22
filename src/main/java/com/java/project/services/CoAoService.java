package com.java.project.services;

import com.java.project.dtos.CoAoDto;
import com.java.project.entities.CoAo;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
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

    public Page<CoAoDto> getAll(String search, Pageable pageable) {
        Page<CoAo> coAos = coAoRepository.findAllWithSearch(search, pageable);
        return coAos.map(CoAoMapper::toDTO);
    }


    @Transactional
    public CoAoDto addCoAo(CoAoDto coAoDto) {
        if (coAoRepository.findByTenCoAo(coAoDto.getTenCoAo()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên cổ áo đã tồn tại");
        }
        CoAo coAo = new CoAo();
        coAo.setTenCoAo(coAoDto.getTenCoAo());
        coAo.setTrangThai(coAoDto.getTrangThai());
        coAo = coAoRepository.save(coAo);
        return CoAoMapper.toDTO(coAo);
    }

    @Transactional
    public CoAoDto updateCoAo(Integer id, CoAoDto coAoDto) {
        CoAo coAo = coAoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy cổ áo"));

        if (coAoRepository.findByTenCoAo(coAoDto.getTenCoAo()).isPresent()) {
            throw new EntityAlreadyExistsException("Tên cổ áo đã tồn tại");
        }

        coAo.setTenCoAo(coAoDto.getTenCoAo());
        coAo.setTrangThai(coAoDto.getTrangThai());
        coAo = coAoRepository.save(coAo);
        return CoAoMapper.toDTO(coAo);
    }

    @Transactional
    public void toggleTrangThai(Integer id) {
        CoAo coAo = coAoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy cổ áo"));
        coAo.setTrangThai(!coAo.getTrangThai());
        coAoRepository.save(coAo);
    }

    @Transactional
    public void deleteCoAo(Integer id) {
        CoAo coAo = coAoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy cổ áo"));
        coAoRepository.delete(coAo);
    }
}
