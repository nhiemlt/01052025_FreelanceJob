package com.java.project.services;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.entities.*;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.SanPhamChiTietMapper;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SanPhamChiTietService {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private CoAoRepository coAoRepository;

    @Autowired
    private ThietKeRepository thietKeRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private KieuDangRepository kieuDangRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private KichThuocRepository kichThuocRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private MoTaRepository moTaRepository;

    @Transactional
    public SanPhamChiTietDto createSanPhamChiTiet(SanPhamChiTietModel sanPhamChiTietModel) {
        SanPhamChiTiet sanPhamChiTiet = buildSanPhamChiTiet(sanPhamChiTietModel);

        sanPhamChiTiet = sanPhamChiTietRepository.save(sanPhamChiTiet);

        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    @Transactional
    public SanPhamChiTietDto updateSanPhamChiTiet(Integer id, SanPhamChiTietModel sanPhamChiTietModel) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));

        SanPhamChiTiet updatedSanPhamChiTiet = buildSanPhamChiTiet(sanPhamChiTietModel);

        updateSanPhamChiTietProperties(sanPhamChiTiet, updatedSanPhamChiTiet);

        sanPhamChiTietRepository.save(sanPhamChiTiet);

        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    // Phương thức để kiểm tra và gán các giá trị cho đối tượng SanPhamChiTiet
    private SanPhamChiTiet buildSanPhamChiTiet(SanPhamChiTietModel sanPhamChiTietModel) {
        SanPham sanPham = checkEntityExistence(sanPhamChiTietModel.getIdSanPham(), sanPhamRepository, "Sản phẩm");
        CoAo coAo = checkEntityExistence(sanPhamChiTietModel.getIdCoAo(), coAoRepository, "Mã sản phẩm");
        ThietKe thietKe = checkEntityExistence(sanPhamChiTietModel.getIdThietKe(), thietKeRepository, "Thiết kế");
        ThuongHieu thuongHieu = checkEntityExistence(sanPhamChiTietModel.getIdThuongHieu(), thuongHieuRepository, "Thương hiệu");
        KieuDang kieuDang = checkEntityExistence(sanPhamChiTietModel.getIdKieuDang(), kieuDangRepository, "Kiểu dáng");
        ChatLieu chatLieu = checkEntityExistence(sanPhamChiTietModel.getIdChatLieu(), chatLieuRepository, "Chất liệu");
        KichThuoc kichThuoc = checkEntityExistence(sanPhamChiTietModel.getIdKichThuoc(), kichThuocRepository, "Kích thước");
        MauSac mauSac = checkEntityExistence(sanPhamChiTietModel.getIdMauSac(), mauSacRepository, "Màu sắc");
        MoTa moTa = checkEntityExistence(sanPhamChiTietModel.getIdMoTa(), moTaRepository, "Mô tả");

        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        sanPhamChiTiet.setSanPham(sanPham);
        sanPhamChiTiet.setCoAo(coAo);
        sanPhamChiTiet.setThietKe(thietKe);
        sanPhamChiTiet.setThuongHieu(thuongHieu);
        sanPhamChiTiet.setKieuDang(kieuDang);
        sanPhamChiTiet.setChatLieu(chatLieu);
        sanPhamChiTiet.setKichThuoc(kichThuoc);
        sanPhamChiTiet.setMauSac(mauSac);
        sanPhamChiTiet.setMoTa(moTa);
        sanPhamChiTiet.setSoLuong(sanPhamChiTietModel.getSoLuong());
        sanPhamChiTiet.setDonGia(sanPhamChiTietModel.getDonGia());
        sanPhamChiTiet.setTrangThai(sanPhamChiTietModel.getTrangThai());
        sanPhamChiTiet.setTrongLuong(sanPhamChiTietModel.getTrongLuong());
        sanPhamChiTiet.setNgayTao(sanPhamChiTietModel.getNgayTao());
        sanPhamChiTiet.setNgayCapNhat(sanPhamChiTietModel.getNgayCapNhat());
        sanPhamChiTiet.setNguoiCapNhat(sanPhamChiTietModel.getNguoiCapNhat());

        return sanPhamChiTiet;
    }

    private <T> T checkEntityExistence(Integer id, JpaRepository<T, Integer> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " không tồn tại"));
    }

    private void updateSanPhamChiTietProperties(SanPhamChiTiet existing, SanPhamChiTiet updated) {
        existing.setCoAo(updated.getCoAo());
        existing.setThietKe(updated.getThietKe());
        existing.setThuongHieu(updated.getThuongHieu());
        existing.setKieuDang(updated.getKieuDang());
        existing.setChatLieu(updated.getChatLieu());
        existing.setKichThuoc(updated.getKichThuoc());
        existing.setMauSac(updated.getMauSac());
        existing.setMoTa(updated.getMoTa());
        existing.setSoLuong(updated.getSoLuong());
        existing.setDonGia(updated.getDonGia());
        existing.setTrangThai(updated.getTrangThai());
        existing.setTrongLuong(updated.getTrongLuong());
        existing.setNgayCapNhat(updated.getNgayCapNhat());
        existing.setNguoiCapNhat(updated.getNguoiCapNhat());
    }


    @Transactional
    public void deleteSanPhamChiTiet(Integer id) {
        sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));

        sanPhamChiTietRepository.deleteById(id);
    }


    public SanPhamChiTietDto getSanPhamChiTietById(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));
        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    public Page<SanPhamChiTietDto> getAllSanPhamChiTiet(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword,
            Integer idSanPham) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<SanPhamChiTiet> sanPhamChiTietPage = sanPhamChiTietRepository.findAll(
                keyword, idSanPham, pageRequest);

        return sanPhamChiTietPage.map(SanPhamChiTietMapper::toDTO);
    }
}

