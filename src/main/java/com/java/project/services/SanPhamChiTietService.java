package com.java.project.services;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.entities.*;
import com.java.project.exceptions.DuplicateKeyException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.SanPhamChiTietMapper;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.models.SanPhamChiTietSingleModel;
import com.java.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public void createSanPhamChiTiet(SanPhamChiTietModel sanPhamChiTietModel) {
        // Duyệt qua từng sự kết hợp và tạo đối tượng SanPhamChiTiet
        for (Integer idCoAo : sanPhamChiTietModel.getIdCoAoList()) {
            for (Integer idThietKe : sanPhamChiTietModel.getIdThietKeList()) {
                for (Integer idThuongHieu : sanPhamChiTietModel.getIdThuongHieuList()) {
                    for (Integer idKieuDang : sanPhamChiTietModel.getIdKieuDangList()) {
                        for (Integer idChatLieu : sanPhamChiTietModel.getIdChatLieuList()) {
                            for (Integer idKichThuoc : sanPhamChiTietModel.getIdKichThuocList()) {
                                for (Integer idMauSac : sanPhamChiTietModel.getIdMauSacList()) {
                                    for (Integer idMoTa : sanPhamChiTietModel.getIdMoTaList()) {

                                        // Kiểm tra sự tồn tại của sản phẩm chi tiết với các ID đã cho
                                        if (isSanPhamChiTietExist(sanPhamChiTietModel.getIdSanPham(), idCoAo, idThietKe,
                                                idThuongHieu, idKieuDang, idChatLieu, idKichThuoc, idMauSac, idMoTa)) {
                                            // Nếu đã tồn tại, bỏ qua việc tạo sản phẩm chi tiết này
                                            continue;
                                        }

                                        // Nếu chưa tồn tại, tiến hành tạo sản phẩm chi tiết
                                        SanPhamChiTietSingleModel tempModel = new SanPhamChiTietSingleModel();
                                        tempModel.setIdSanPham(sanPhamChiTietModel.getIdSanPham());
                                        tempModel.setSoLuong(sanPhamChiTietModel.getSoLuong());
                                        tempModel.setDonGia(sanPhamChiTietModel.getDonGia());
                                        tempModel.setTrangThai(sanPhamChiTietModel.getTrangThai());
                                        tempModel.setTrongLuong(sanPhamChiTietModel.getTrongLuong());
                                        tempModel.setNguoiCapNhat(sanPhamChiTietModel.getNguoiCapNhat());

                                        // Gán các giá trị từ danh sách ID
                                        tempModel.setIdCoAo(idCoAo);
                                        tempModel.setIdThietKe(idThietKe);
                                        tempModel.setIdThuongHieu(idThuongHieu);
                                        tempModel.setIdKieuDang(idKieuDang);
                                        tempModel.setIdChatLieu(idChatLieu);
                                        tempModel.setIdKichThuoc(idKichThuoc);
                                        tempModel.setIdMauSac(idMauSac);
                                        tempModel.setIdMoTa(idMoTa);

                                        // Tạo và lưu sản phẩm chi tiết
                                        SanPhamChiTiet sanPhamChiTiet = buildSanPhamChiTiet(tempModel);
                                        sanPhamChiTietRepository.save(sanPhamChiTiet);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public SanPhamChiTietDto updateSanPhamChiTiet(Integer id, SanPhamChiTietSingleModel sanPhamChiTietModel) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));

        SanPhamChiTiet updatedSanPhamChiTiet = buildSanPhamChiTiet(sanPhamChiTietModel);

        updateSanPhamChiTietProperties(sanPhamChiTiet, updatedSanPhamChiTiet);

        sanPhamChiTietRepository.save(sanPhamChiTiet);

        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    private SanPhamChiTiet buildSanPhamChiTiet(SanPhamChiTietSingleModel sanPhamChiTietModel) {
        SanPham sanPham = checkEntityExistence(sanPhamChiTietModel.getIdSanPham(), sanPhamRepository, "Sản phẩm");
        CoAo coAo = checkEntityExistence(sanPhamChiTietModel.getIdCoAo(), coAoRepository, "Cổ áo");
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
        sanPhamChiTiet.setNgayTao(LocalDate.now());
        sanPhamChiTiet.setNgayCapNhat(LocalDate.now());
        sanPhamChiTiet.setNguoiCapNhat(sanPhamChiTietModel.getNguoiCapNhat());

        return sanPhamChiTiet;
    }

    private <T> T checkEntityExistence(Integer id, JpaRepository<T, Integer> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " không tồn tại"));
    }

    private boolean isSanPhamChiTietExist(Integer idSanPham, Integer idCoAo, Integer idThietKe, Integer idThuongHieu,
                                          Integer idKieuDang, Integer idChatLieu, Integer idKichThuoc, Integer idMauSac,
                                          Integer idMoTa) {
        return sanPhamChiTietRepository.existsBySanPhamIdAndCoAoIdAndThietKeIdAndThuongHieuIdAndKieuDangIdAndChatLieuIdAndKichThuocIdAndMauSacIdAndMoTaId(
                idSanPham, idCoAo, idThietKe, idThuongHieu, idKieuDang, idChatLieu, idKichThuoc, idMauSac, idMoTa);
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

        try {
            sanPhamChiTietRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException("Không thể xóa do xung đột khóa ngoại");
        }
    }


    public SanPhamChiTietDto getSanPhamChiTietById(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));
        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    public Page<SanPhamChiTietDto> getSanPhamChiTietBySanPhamId(
            int page, int size, String sortBy, String direction,
            Integer idSanPham) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Tìm kiếm sản phẩm chi tiết theo idSanPham
        Page<SanPhamChiTiet> sanPhamChiTietPage = sanPhamChiTietRepository.findBySanPhamIdPageable(idSanPham, pageRequest);

        // Chuyển đổi sang DTO và trả về
        return sanPhamChiTietPage.map(SanPhamChiTietMapper::toDTO);
    }


    public Page<SanPhamChiTietDto> getAllSanPhamChiTiet(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword,
            Integer idSanPham,
            Integer coAoId,
            Integer thietKeId,
            Integer thuongHieuId,
            Integer kieuDangId,
            Integer chatLieuId,
            Integer kichThuocId,
            Integer mauSacId,
            BigDecimal donGiaMin,
            BigDecimal donGiaMax) {

        // Xử lý sắp xếp
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Tìm kiếm và lọc các sản phẩm chi tiết
        Page<SanPhamChiTiet> sanPhamChiTietPage = sanPhamChiTietRepository.findAll(
                keyword, idSanPham, coAoId, thietKeId, thuongHieuId, kieuDangId,
                chatLieuId, kichThuocId, mauSacId, donGiaMin, donGiaMax, pageRequest);

        // Chuyển đổi sang DTO và trả về
        return sanPhamChiTietPage.map(SanPhamChiTietMapper::toDTO);
    }

}

