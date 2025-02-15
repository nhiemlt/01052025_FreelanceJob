package com.java.project.services;

import com.java.project.dtos.HoaDonResponse;
import com.java.project.repositories.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HoaDonService {
    @Autowired
    HoaDonRepository hoaDonRepository;

    public List<HoaDonResponse> getAllHoaDon(Integer loaiDon) {
        return hoaDonRepository.getAll(loaiDon);
    }

    public Page<HoaDonResponse> GetPhanTrangHoaDon(Pageable pageable, Integer loaiDon) {
        return hoaDonRepository.getPhanTrang(pageable, loaiDon);
    }

    public List<HoaDonResponse> getAllSearch(Integer trangThaiGiaoHang,
                                             String keyword,
                                             LocalDate ngayBatDau,
                                             LocalDate ngayKetThuc,
                                             Integer loaiDon) {
        return hoaDonRepository.getSearchAll(trangThaiGiaoHang, keyword, ngayBatDau, ngayKetThuc, loaiDon);
    }

    public Page<HoaDonResponse> getPhanTrangSearch(Pageable pageable,
                                                   Integer trangThaiGiaoHang,
                                                   String keyword,
                                                   LocalDate ngayBatDau,
                                                   LocalDate ngayKetThuc,
                                                   Integer loaiDon) {
        return hoaDonRepository.getPhanTrangSearch(pageable, trangThaiGiaoHang, keyword, ngayBatDau, ngayKetThuc, loaiDon);
    }

    public Map<String, Long> getOrderCounts(
            LocalDate ngayBatDau, LocalDate ngayKetThuc,
            Integer loaiDon) {

        List<Object[]> results = hoaDonRepository.countOrdersByStatus(ngayBatDau, ngayKetThuc, loaiDon);

        // Tạo một map với giá trị mặc định là 0
        Map<String, Long> counts = new LinkedHashMap<>();
        counts.put("tong", 0L);
        counts.put("cho_xac_nhan", 0L);
        counts.put("xac_nhan", 0L);
        counts.put("cho_van_chuyen", 0L);
        counts.put("van_chuyen", 0L);
        counts.put("da_thanh_toan", 0L);
        counts.put("thanh_cong", 0L);
        counts.put("hoan_hang", 0L);
        counts.put("da_huy", 0L);

        // Cập nhật giá trị từ kết quả truy vấn
        for (Object[] result : results) {
            Integer trangThai = (Integer) result[0];
            Long soLuong = (Long) result[1];

            switch (trangThai) {
                case 1 -> counts.put("cho_xac_nhan", soLuong);      // Chờ Xác Nhận
                case 2 -> counts.put("xac_nhan", soLuong);         // Xác Nhận
                case 3 -> counts.put("cho_van_chuyen", soLuong);   // Chờ Vận Chuyển
                case 4 -> counts.put("van_chuyen", soLuong);       // Vận Chuyển
                case 5 -> counts.put("da_thanh_toan", soLuong);    // Đã Thanh Toán
                case 6 -> counts.put("thanh_cong", soLuong);       // Thành Công
                case 7 -> counts.put("hoan_hang", soLuong);        // Hoàn Hàng
                default -> counts.put("da_huy", soLuong);          // Đã Hủy
            }
            counts.put("tong", counts.get("tong") + soLuong);
        }

        return counts;
    }
}
