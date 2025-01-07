package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "danh_muc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_danh_muc", length = 50)
    private String maDanhMuc;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_danh_muc")
    private String tenDanhMuc;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}