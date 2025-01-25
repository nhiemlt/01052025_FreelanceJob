package com.java.project.services;

import com.java.project.dtos.SanPhamChiTietDto;
import com.java.project.dtos.SanPhamChiTietGenerateDTO;
import com.java.project.dtos.SanPhamChiTietPhanLoaiDTO;
import com.java.project.entities.SanPhamChiTiet;
import com.java.project.exceptions.EntityNotFoundException;
import com.java.project.mappers.SanPhamChiTietMapper;
import com.java.project.models.SanPhamChiTietGenerateModel;
import com.java.project.models.SanPhamChiTietModel;
import com.java.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public List<SanPhamChiTietDto> createSanPhamChiTietList(List<SanPhamChiTietDto> sanPhamChiTietDtos) {
        List<SanPhamChiTietDto> result = new ArrayList<>();
        for (SanPhamChiTietDto sanPhamChiTietDto : sanPhamChiTietDtos) {
            validateExistence(sanPhamChiTietDto);
            checkUniqueSanPhamChiTiet(sanPhamChiTietDto);

            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            mapToEntity(sanPhamChiTietDto, sanPhamChiTiet);
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            result.add(SanPhamChiTietMapper.toDTO(sanPhamChiTiet));
        }
        return result;
    }

    @Transactional
    public SanPhamChiTietDto updateSanPhamChiTiet(Integer id, SanPhamChiTietDto sanPhamChiTietDto) {
        // Kiểm tra sự tồn tại của các thực thể
        validateExistence(sanPhamChiTietDto);

        // Tìm kiếm chi tiết sản phẩm theo ID
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SanPhamChiTiet không tồn tại"));

        // Cập nhật thông tin chi tiết sản phẩm
        mapToEntity(sanPhamChiTietDto, sanPhamChiTiet);
        sanPhamChiTietRepository.save(sanPhamChiTiet);

        return SanPhamChiTietMapper.toDTO(sanPhamChiTiet);
    }


    public Page<SanPhamChiTietDto> getAllSanPhamChiTiet(String search, List<Integer> thuongHieuIds, List<Integer> xuatXuIds,
                                                        List<Integer> chatLieuIds, List<Integer> coAoIds, List<Integer> tayAoIds,
                                                        List<Integer> mauSacIds, List<Integer> kichThuocIds, Pageable pageable) {
        Page<SanPhamChiTiet> sanPhamChiTiets = sanPhamChiTietRepository.findBySearchAndFilter(
                search, thuongHieuIds, xuatXuIds, chatLieuIds, coAoIds, tayAoIds, mauSacIds, kichThuocIds, pageable);

        return sanPhamChiTiets.map(SanPhamChiTietMapper::toDTO);
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

    private void validateExistence(SanPhamChiTietDto sanPhamChiTietDto) {
        Optional.ofNullable(sanPhamChiTietDto.getSanPham())
                .ifPresent(sanPham -> sanPhamRepository.findById(sanPham.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getThuongHieu())
                .ifPresent(thuongHieu -> thuongHieuRepository.findById(thuongHieu.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Thương hiệu không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getXuatXu())
                .ifPresent(xuatXu -> xuatXuRepository.findById(xuatXu.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Xuất xứ không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getChatLieu())
                .ifPresent(chatLieu -> chatLieuRepository.findById(chatLieu.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Chất liệu không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getCoAo())
                .ifPresent(coAo -> coAoRepository.findById(coAo.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Cỡ áo không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getTayAo())
                .ifPresent(tayAo -> tayAoRepository.findById(tayAo.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Tay áo không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getMauSac())
                .ifPresent(mauSac -> mauSacRepository.findById(mauSac.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Màu sắc không tồn tại")));

        Optional.ofNullable(sanPhamChiTietDto.getKichThuoc())
                .ifPresent(kichThuoc -> kichThuocRepository.findById(kichThuoc.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Kích thước không tồn tại")));
    }

    private void checkUniqueSanPhamChiTiet(SanPhamChiTietDto sanPhamChiTietDto) {
        SanPhamChiTiet existingSanPhamChiTiet = sanPhamChiTietRepository.findBySanPhamAndThuongHieuAndXuatXuAndChatLieuAndCoAoAndTayAoAndMauSacAndKichThuoc(
                sanPhamChiTietDto.getSanPham().getId(),
                sanPhamChiTietDto.getThuongHieu().getId(),
                sanPhamChiTietDto.getXuatXu().getId(),
                sanPhamChiTietDto.getChatLieu().getId(),
                sanPhamChiTietDto.getCoAo().getId(),
                sanPhamChiTietDto.getTayAo().getId(),
                sanPhamChiTietDto.getMauSac().getId(),
                sanPhamChiTietDto.getKichThuoc().getId()
        );

        if (existingSanPhamChiTiet != null) {
            throw new IllegalArgumentException("Chi tiết sản phẩm này đã tồn tại trong cơ sở dữ liệu");
        }
    }

    @Transactional
    public List<SanPhamChiTietPhanLoaiDTO> generateSanPhamChiTietGroupedByMauSac(SanPhamChiTietGenerateModel generateRequest) {
        // Bước 1: Generate danh sách SanPhamChiTietGenerateDTO
        List<SanPhamChiTietGenerateDTO> sanPhamChiTietList = generateSanPhamChiTietList(generateRequest);

        // Bước 2: Nhóm danh sách SanPhamChiTietGenerateDTO theo `mauSac`
        Map<Integer, List<SanPhamChiTietGenerateDTO>> groupedByMauSac = sanPhamChiTietList.stream()
                .collect(Collectors.groupingBy(SanPhamChiTietGenerateDTO::getMauSac));

        // Bước 3: Chuyển đổi từ nhóm sang danh sách SanPhamChiTietPhanLoaiDTO
        return groupedByMauSac.entrySet().stream()
                .map(this::convertEntryToPhanLoaiDTO)
                .collect(Collectors.toList());
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
                                                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy kích thước!"))
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
        List<SanPhamChiTietPhanLoaiDTO> chiTietSanPham = entry.getValue().stream()
                .map(this::convertToPhanLoaiDTO)
                .collect(Collectors.toList());

        return SanPhamChiTietPhanLoaiDTO.builder()
                .maMauSac(entry.getKey())
                .tenMauSac(mauSacRepository.findById(entry.getKey()).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy màu sắc với ID: " + entry.getKey()))
                        .getTenMauSac())
                .sanPhamChiTiet(chiTietSanPham)
                .build();
    }

    private SanPhamChiTietPhanLoaiDTO convertToPhanLoaiDTO(SanPhamChiTietGenerateDTO dto) {
        return SanPhamChiTietPhanLoaiDTO.builder()
                .maMauSac(dto.getMauSac())
                .tenMauSac(mauSacRepository.findById(dto.getMauSac()).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy màu sắc với ID: " +  dto.getMauSac()))
                        .getTenMauSac())
                .build();
    }

    public List<SanPhamChiTietPhanLoaiDTO> groupSanPhamByMauSac(Map<Integer, List<SanPhamChiTietGenerateDTO>> groupedByMauSac) {
        return groupedByMauSac.entrySet().stream()
                .map(this::convertEntryToPhanLoaiDTO)
                .collect(Collectors.toList());
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
