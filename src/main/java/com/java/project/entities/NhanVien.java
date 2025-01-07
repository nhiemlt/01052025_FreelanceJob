package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vai_tro_id")
    private VaiTro vaiTro;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_nhan_vien", length = 50)
    private String maNhanVien;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_nhan_vien")
    private String tenNhanVien;

    @Size(max = 255)
    @Nationalized
    @Column(name = "email")
    private String email;

    @Size(max = 15)
    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    @Size(max = 255)
    @Nationalized
    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

    @Column(name = "gioi_tinh", columnDefinition = "tinyint")
    private Short gioiTinh;

}