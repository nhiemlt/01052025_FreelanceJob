package com.java.project.services;

import com.java.project.dtos.SanPhamDto;
import com.java.project.entities.DanhMuc;
import com.java.project.entities.SanPham;
import com.java.project.exceptions.DuplicateKeyException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.SanPhamMapper;
import com.java.project.models.SanPhamModel;
import com.java.project.repositories.DanhMucRepository;
import com.java.project.repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;


@Service
public class SanPhamService {

    @Autowired
    DanhMucRepository danhMucRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    public SanPhamDto createSanPham(SanPhamModel sanPhamModel) {
        SanPham sanPham = new SanPham();
        if(sanPhamRepository.existsByMaSanPham(sanPhamModel.getMaSanPham())){
            throw new DuplicateKeyException("Mã sản phẩm đã tồn tại trước đó, vui lòng nhập mã khác");
        } else if (sanPhamRepository.existsByTenSanPham(sanPhamModel.getTenSanPham())){
            throw new DuplicateKeyException("Tên sản phẩm đã tồn tại trước đó, vui lòng nhập mã khác");
        }
        sanPham.setIdDanhMuc(danhMucRepository.findById(sanPhamModel.getIdDanhMuc())
                .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại")));
        sanPham.setTenSanPham(sanPhamModel.getTenSanPham());
        sanPham.setMaSanPham(sanPhamModel.getMaSanPham());
        sanPham.setMoTa(sanPhamModel.getMoTa());
        sanPham.setTrangThai(sanPhamModel.getTrangThai());

        sanPham = sanPhamRepository.save(sanPham);
        return SanPhamMapper.toDTO(sanPham);
    }

    public SanPhamDto updateSanPham(Integer id, SanPhamModel sanPhamModel) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));

        if(!Objects.equals(sanPhamModel.getMaSanPham(), sanPham.getMaSanPham()) && sanPhamRepository.existsByMaSanPham(sanPhamModel.getMaSanPham())){
            throw new DuplicateKeyException("Mã sản phẩm đã tồn tại trước đó, vui lòng nhập mã khác");
        } else if (!Objects.equals(sanPhamModel.getTenSanPham(), sanPham.getTenSanPham()) && sanPhamRepository.existsByTenSanPham(sanPhamModel.getTenSanPham())){
            throw new DuplicateKeyException("Tên sản phẩm đã tồn tại trước đó, vui lòng nhập mã khác");
        }

        DanhMuc danhMuc = danhMucRepository.findById(sanPhamModel.getIdDanhMuc())
                .orElseThrow(() -> new EntityNotFoundException("Danh mục không tồn tại"));

        sanPham.setIdDanhMuc(danhMuc);
        sanPham.setTenSanPham(sanPhamModel.getTenSanPham());
        sanPham.setMaSanPham(sanPhamModel.getMaSanPham());
        sanPham.setMoTa(sanPhamModel.getMoTa());
        sanPham.setTrangThai(sanPhamModel.getTrangThai());

        sanPhamRepository.save(sanPham);

        return SanPhamMapper.toDTO(sanPham);
    }


    public Page<SanPhamDto> getAllSanPham(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword,
            Integer danhMucId,
            BigDecimal donGiaMin,
            BigDecimal donGiaMax,
            Integer coAoId,
            Integer thietKeId,
            Integer thuongHieuId,
            Integer kieuDangId,
            Integer chatLieuId,
            Integer kichThuocId,
            Integer mauSacId) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<SanPham> sanPhamPage = sanPhamRepository.getAll(
                keyword,
                danhMucId,
                donGiaMin,
                donGiaMax,
                coAoId,
                thietKeId,
                thuongHieuId,
                kieuDangId,
                chatLieuId,
                kichThuocId,
                mauSacId,
                pageRequest);

        return sanPhamPage.map(SanPhamMapper::toDTO);
    }


    public SanPhamDto getSanPhamById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));
        return SanPhamMapper.toDTO(sanPham);
    }


    @Transactional
    public void deleteSanPham(Integer id) {
        if (!sanPhamRepository.existsById(id)) {
            throw new EntityNotFoundException("Sản phẩm không tồn tại");
        }

        try {
            sanPhamRepository.deleteById(id);
        } catch (Exception e) {
            throw new DuplicateKeyException("Sản phẩm không tồn tại");
        }
        }

}
