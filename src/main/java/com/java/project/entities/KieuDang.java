package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "kieu_dang")
public class KieuDang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_kieu_dang", length = 50)
    private String maKieuDang;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_kieu_dang")
    private String tenKieuDang;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}