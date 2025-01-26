package com.java.project.services;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.dtos.SanPhamChiTietGenerateDTO;
import com.java.project.dtos.SanPhamChiTietPhanLoaiDTO;
import com.java.project.entities.SanPhamChiTiet;
import com.java.project.exceptions.EntityAlreadyExistsException;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.SanPhamChiTietMapper;
import com.java.project.models.SanPhamChiTietGenerateModel;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.models.UpdateSanPhamChiTietModel;
import com.java.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SanPhamChiTietService {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private CoAoRepository coAoRepository;

    @Autowired
    private TayAoRepository tayAoRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichThuocRepository kichThuocRepository;

    public Page<SanPhamChiTietDto> getAllSanPhamChiTiet(String search, List<Integer> thuongHieuIds, List<Integer> xuatXuIds,
                                                        List<Integer> chatLieuIds, List<Integer> coAoIds, List<Integer> tayAoIds,
                                                        List<Integer> mauSacIds, List<Integer> kichThuocIds, Pageable pageable) {
        Page<SanPhamChiTiet> sanPhamChiTiets = sanPhamChiTietRepository.findBySearchAndFilter(
                search, thuongHieuIds, xuatXuIds, chatLieuIds, coAoIds, tayAoIds, mauSacIds, kichThuocIds, pageable);

        return sanPhamChiTiets.map(SanPhamChiTietMapper::toDTO);
    }

    @Transactional
    public SanPhamChiTietDto getById(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));
        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    @Transactional
    public List<SanPhamChiTietDto> createSanPhamChiTietList(List<SanPhamChiTietModel> sanPhamChiTietModels) {
        List<SanPhamChiTietDto> result = new ArrayList<>();

        for (SanPhamChiTietModel model : sanPhamChiTietModels) {
            // Kiểm tra dữ liệu có tồn tại không
            validateExistence(model);

            // Kiểm tra uniqueness
            checkUniqueSanPhamChiTiet(model);

            // Map từ model sang entity
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            mapToEntity(model, sanPhamChiTiet);

            // Lưu entity vào database
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            // Thêm kết quả trả về
            result.add(SanPhamChiTietMapper.toDTO(sanPhamChiTiet));
        }

        return result;
    }

    @Transactional
    public SanPhamChiTietDto updateSanPhamChiTiet(Integer id, UpdateSanPhamChiTietModel model) {

        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chi tiết sản phẩm không tồn tại"));

        if (model.getSoLuong() != null) {
            sanPhamChiTiet.setSoLuong(model.getSoLuong());
        }
        if (model.getDonGia() != null) {
            sanPhamChiTiet.setDonGia(model.getDonGia());
        }
        if (model.getHinhAnh() != null) {
            sanPhamChiTiet.setHinhAnh(model.getHinhAnh());
        }

        sanPhamChiTietRepository.save(sanPhamChiTiet);

        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }


    public SanPhamChiTietDto toggleTrangThai(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SanPhamChiTiet không tồn tại"));

        sanPhamChiTiet.setTrangThai(!sanPhamChiTiet.getTrangThai());
        sanPhamChiTietRepository.save(sanPhamChiTiet);

        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }

    @Transactional
    public void deleteSanPhamChiTiet(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SanPhamChiTiet không tồn tại"));

        sanPhamChiTietRepository.delete(sanPhamChiTiet);
    }

    private void validateExistence(SanPhamChiTietModel model) {
        // Kiểm tra sự tồn tại của từng khóa ngoại (tương tự validateExistence của bạn)
        Optional.ofNullable(model.getSanPham())
                .ifPresent(sanPham -> sanPhamRepository.findById(sanPham)
                        .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại")));

        Optional.ofNullable(model.getThuongHieu())
                .ifPresent(thuongHieu -> thuongHieuRepository.findById(thuongHieu)
                        .orElseThrow(() -> new EntityNotFoundException("Thương hiệu không tồn tại")));

        // Tương tự cho các trường khác
        Optional.ofNullable(model.getXuatXu())
                .ifPresent(xuatXu -> xuatXuRepository.findById(xuatXu)
                        .orElseThrow(() -> new EntityNotFoundException("Xuất xứ không tồn tại")));

        Optional.ofNullable(model.getChatLieu())
                .ifPresent(chatLieu -> chatLieuRepository.findById(chatLieu)
                        .orElseThrow(() -> new EntityNotFoundException("Chất liệu không tồn tại")));

        Optional.ofNullable(model.getCoAo())
                .ifPresent(coAo -> coAoRepository.findById(coAo)
                        .orElseThrow(() -> new EntityNotFoundException("Cỡ áo không tồn tại")));

        Optional.ofNullable(model.getTayAo())
                .ifPresent(tayAo -> tayAoRepository.findById(tayAo)
                        .orElseThrow(() -> new EntityNotFoundException("Tay áo không tồn tại")));

        Optional.ofNullable(model.getMauSac())
                .ifPresent(mauSac -> mauSacRepository.findById(mauSac)
                        .orElseThrow(() -> new EntityNotFoundException("Màu sắc không tồn tại")));

        Optional.ofNullable(model.getKichThuoc())
                .ifPresent(kichThuoc -> kichThuocRepository.findById(kichThuoc)
                        .orElseThrow(() -> new EntityNotFoundException("Kích thước không tồn tại")));
    }

    private void checkUniqueSanPhamChiTiet(SanPhamChiTietModel model) {
        boolean exists = sanPhamChiTietRepository.existsBySanPhamAndThuongHieuAndXuatXuAndChatLieuAndCoAoAndTayAoAndMauSacAndKichThuoc(
                model.getSanPham(),
                model.getThuongHieu(),
                model.getXuatXu(),
                model.getChatLieu(),
                model.getCoAo(),
                model.getTayAo(),
                model.getMauSac(),
                model.getKichThuoc()
        );

        if (exists) {
            throw new EntityAlreadyExistsException("Chi tiết sản phẩm đã tồn tại trong cơ sở dữ liệu");
        }
    }

    private void mapToEntity(SanPhamChiTietModel model, SanPhamChiTiet entity) {
        entity.setSanPham(sanPhamRepository.findById(model.getSanPham()).orElse(null));
        entity.setThuongHieu(thuongHieuRepository.findById(model.getThuongHieu()).orElse(null));
        entity.setXuatXu(xuatXuRepository.findById(model.getXuatXu()).orElse(null));
        entity.setChatLieu(chatLieuRepository.findById(model.getChatLieu()).orElse(null));
        entity.setCoAo(coAoRepository.findById(model.getCoAo()).orElse(null));
        entity.setTayAo(tayAoRepository.findById(model.getTayAo()).orElse(null));
        entity.setMauSac(mauSacRepository.findById(model.getMauSac()).orElse(null));
        entity.setKichThuoc(kichThuocRepository.findById(model.getKichThuoc()).orElse(null));
        entity.setSoLuong(model.getSoLuong());
        entity.setDonGia(model.getDonGia());
        entity.setHinhAnh(model.getHinhAnh());
        entity.setTrangThai(true);
        entity.setNgayTao(Instant.now());
    }

    @Transactional
    public List<SanPhamChiTietPhanLoaiDTO> generateSanPhamChiTietGroupedByMauSac(SanPhamChiTietGenerateModel generateRequest) {
        // Bước 1: Generate danh sách tất cả các kết hợp sản phẩm chi tiết
        List<SanPhamChiTietGenerateDTO> allCombinations = generateSanPhamChiTietList(generateRequest);

        // Bước 2: Lọc ra các sản phẩm chi tiết chưa tồn tại
        List<SanPhamChiTietGenerateDTO> filteredCombinations = allCombinations.stream()
                .filter(this::isUniqueProductDetail)
                .collect(Collectors.toList());

        // Bước 3: Nhóm các sản phẩm đã lọc theo màu sắc
        Map<Integer, List<SanPhamChiTietGenerateDTO>> groupedByMauSac = filteredCombinations.stream()
                .collect(Collectors.groupingBy(SanPhamChiTietGenerateDTO::getMauSac));

        // Bước 4: Chuyển đổi thành danh sách DTO để trả về
        return groupedByMauSac.entrySet().stream()
                .map(this::convertEntryToPhanLoaiDTO)
                .collect(Collectors.toList());
    }

    private boolean isUniqueProductDetail(SanPhamChiTietGenerateDTO dto) {
        return !sanPhamChiTietRepository.existsBySanPhamAndThuongHieuAndXuatXuAndChatLieuAndCoAoAndTayAoAndMauSacAndKichThuoc(
                dto.getSanPham(),
                dto.getThuongHieu(),
                dto.getXuatXu(),
                dto.getChatLieu(),
                dto.getCoAo(),
                dto.getTayAo(),
                dto.getMauSac(),
                dto.getKichThuoc()
        );
    }

    private List<SanPhamChiTietGenerateDTO> generateSanPhamChiTietList(SanPhamChiTietGenerateModel generateRequest) {
        List<SanPhamChiTietGenerateDTO> result = new ArrayList<>();

        for (Integer thuongHieuId : generateRequest.getThuongHieu()) {
            for (Integer xuatXuId : generateRequest.getXuatXu()) {
                for (Integer chatLieuId : generateRequest.getChatLieu()) {
                    for (Integer coAoId : generateRequest.getCoAo()) {
                        for (Integer tayAoId : generateRequest.getTayAo()) {
                            for (Integer mauSacId : generateRequest.getMauSac()) {
                                for (Integer kichThuocId : generateRequest.getKichThuoc()) {
                                    SanPhamChiTietGenerateDTO dto = SanPhamChiTietGenerateDTO.builder()
                                            .sanPham(generateRequest.getSanPham())
                                            .thuongHieu(thuongHieuId)
                                            .xuatXu(xuatXuId)
                                            .chatLieu(chatLieuId)
                                            .coAo(coAoId)
                                            .tayAo(tayAoId)
                                            .mauSac(mauSacId)
                                            .kichThuoc(kichThuocId)
                                            .tenKichThuoc(kichThuocRepository.findById(kichThuocId)
                                                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kích thước với ID: " + kichThuocId))
                                                    .getTenKichThuoc())
                                            .soLuong(10)
                                            .donGia(BigDecimal.valueOf(50000))
                                            .hinhAnh(null)
                                            .build();

                                    result.add(dto);
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private SanPhamChiTietPhanLoaiDTO convertEntryToPhanLoaiDTO(Map.Entry<Integer, List<SanPhamChiTietGenerateDTO>> entry) {
        return SanPhamChiTietPhanLoaiDTO.builder()
                .maMauSac(entry.getKey())
                .tenMauSac(mauSacRepository.findById(entry.getKey())
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy màu sắc với ID: " + entry.getKey()))
                        .getTenMauSac())
                .sanPhamChiTiet(entry.getValue())
                .build();
    }

        private void mapToEntity(SanPhamChiTietDto dto, SanPhamChiTiet entity) {
        entity.setSanPham(sanPhamRepository.findById(dto.getSanPham().getId()).get());
        entity.setThuongHieu(thuongHieuRepository.findById(dto.getThuongHieu().getId()).get());
        entity.setXuatXu(xuatXuRepository.findById(dto.getXuatXu().getId()).get());
        entity.setChatLieu(chatLieuRepository.findById(dto.getChatLieu().getId()).get());
        entity.setCoAo(coAoRepository.findById(dto.getCoAo().getId()).get());
        entity.setTayAo(tayAoRepository.findById(dto.getTayAo().getId()).get());
        entity.setMauSac(mauSacRepository.findById(dto.getMauSac().getId()).get());
        entity.setKichThuoc(kichThuocRepository.findById(dto.getKichThuoc().getId()).get());
        entity.setSoLuong(dto.getSoLuong());
        entity.setDonGia(dto.getDonGia());
        entity.setHinhAnh(dto.getHinhAnh());
        entity.setTrangThai(dto.getTrangThai());
    }
}
