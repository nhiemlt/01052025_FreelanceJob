package com.java.project.repositories;

import com.java.project.dtos.HoaDonResponse;
import com.java.project.entities.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("""
        select new com.java.project.dtos.HoaDonResponse(
        hd.khachHang.tenKhachHang,
        hd.nhanVien.maNhanVien,
        hd.maHoaDon,
        hd.loaiDon,
        hd.ghiChu,
        hd.hoTenNguoiNhan,
        hd.soDienThoai,
        hd.email,
        hd.diaChiNhanHang,
        hd.ngayNhanMongMuon,
        hd.ngayDuKienNhan,
        hd.trangThaiGiaoHang,
        hd.phiShip,
        hd.tongTien,
        hd.ngayTao,
        hd.trangThai
        )

        from HoaDon hd
        where CAST(hd.ngayTao AS DATE ) = CURRENT_DATE 
                and (:loaiDon IS NULL OR hd.loaiDon = :loaiDon)
        order by hd.ngayTao ASC 
""")
    List<HoaDonResponse> getAll(@Param("loaiDon")Integer loaiDon);

    @Query("""
                    select new com.java.project.dtos.HoaDonResponse(
                 hd.khachHang.tenKhachHang,
                hd.nhanVien.maNhanVien,
                hd.maHoaDon,
                hd.loaiDon,
                hd.ghiChu,
                hd.hoTenNguoiNhan,
                hd.soDienThoai,
                hd.email,
                hd.diaChiNhanHang,
                hd.ngayNhanMongMuon,
                hd.ngayDuKienNhan,
                hd.trangThaiGiaoHang,
                hd.phiShip,
                hd.tongTien,
                hd.ngayTao,
                hd.trangThai
                    ) 
                from HoaDon hd
                where CAST(hd.ngayTao AS DATE ) = CURRENT_DATE 
                and (:loaiDon IS NULL OR hd.loaiDon = :loaiDon)
                order by hd.ngayTao ASC 
                    """)
    Page<HoaDonResponse>getPhanTrang(Pageable pageable, @Param("loaiDon") Integer loaiDon);

    @Query("""
                    select new com.java.project.dtos.HoaDonResponse(
                 hd.khachHang.tenKhachHang,
                hd.nhanVien.maNhanVien,
                hd.maHoaDon,
                hd.loaiDon,
                hd.ghiChu,
                hd.hoTenNguoiNhan,
                hd.soDienThoai,
                hd.email,
                hd.diaChiNhanHang,
                hd.ngayNhanMongMuon,
                hd.ngayDuKienNhan,
                hd.trangThaiGiaoHang,
                hd.phiShip,
                hd.tongTien,
                hd.ngayTao,
                hd.trangThai
                    ) 
                from HoaDon hd
                where (:trangThaiGiaoHang IS NULL OR hd.trangThaiGiaoHang = :trangThaiGiaoHang)
                and (:keyword IS NULL OR LOWER(hd.maHoaDon) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(hd.khachHang.tenKhachHang) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(hd.nhanVien.maNhanVien) LIKE LOWER(CONCAT('%', :keyword, '%')))
                and (:ngayBatDau IS NULL OR CAST(hd.ngayTao AS DATE ) >=  :ngayBatDau)
                and (:ngayKetThuc IS NULL OR  CAST(hd.ngayTao AS DATE ) <=  :ngayKetThuc)
                and (:loaiDon IS NULL OR hd.loaiDon = :loaiDon)
                order by hd.ngayTao ASC 
                    """)
    Page<HoaDonResponse>getPhanTrangSearch(Pageable pageable,
                                  @Param("trangThaiGiaoHang") Integer trangThaiGiaoHang,
                                  @Param("keyword") String keyword,
                                  @Param("ngayBatDau") LocalDate ngayBatDau,
                                  @Param("ngayKetThuc") LocalDate ngayKetThuc,
                                  @Param("loaiDon") Integer loaiDon);

    @Query("""
                    select new com.java.project.dtos.HoaDonResponse(
                 hd.khachHang.tenKhachHang,
                hd.nhanVien.maNhanVien,
                hd.maHoaDon,
                hd.loaiDon,
                hd.ghiChu,
                hd.hoTenNguoiNhan,
                hd.soDienThoai,
                hd.email,
                hd.diaChiNhanHang,
                hd.ngayNhanMongMuon,
                hd.ngayDuKienNhan,
                hd.trangThaiGiaoHang,
                hd.phiShip,
                hd.tongTien,
                hd.ngayTao,
                hd.trangThai
                    ) 
                from HoaDon hd
                where (:trangThaiGiaoHang IS NULL OR hd.trangThaiGiaoHang = :trangThaiGiaoHang)
                and (:keyword IS NULL OR LOWER(hd.maHoaDon) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(hd.khachHang.tenKhachHang) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(hd.nhanVien.maNhanVien) LIKE LOWER(CONCAT('%', :keyword, '%')))
                and (:ngayBatDau IS NULL OR CAST(hd.ngayTao AS DATE ) >=  :ngayBatDau)
                and (:ngayKetThuc IS NULL OR CAST(hd.ngayTao AS DATE ) <=  :ngayKetThuc)
                and (:loaiDon IS NULL OR hd.loaiDon = :loaiDon)
                order by hd.ngayTao ASC 
                    """)
    List<HoaDonResponse>getSearchAll(
                                    @Param("trangThaiGiaoHang") Integer trangThaiGiaoHang,
                                    @Param("keyword") String keyword,
                                    @Param("ngayBatDau") LocalDate ngayBatDau,
                                    @Param("ngayKetThuc") LocalDate ngayKetThuc,
                                    @Param("loaiDon") Integer loaiDon);

    @Query("""
    SELECT hd.trangThaiGiaoHang, COUNT(hd) 
    FROM HoaDon hd 
    WHERE
    (:ngayBatDau IS NULL OR CAST(hd.ngayTao AS DATE ) >=  :ngayBatDau)
    and (:ngayKetThuc IS NULL OR CAST(hd.ngayTao AS DATE ) <=  :ngayKetThuc)
    AND (:loaiDon IS NULL OR hd.loaiDon = :loaiDon)
    GROUP BY hd.trangThaiGiaoHang
""")
    List<Object[]> countOrdersByStatus(
            @Param("ngayBatDau") LocalDate ngayBatDau,
            @Param("ngayKetThuc") LocalDate ngayKetThuc,
            @Param("loaiDon") Integer loaiDon
    );
}


