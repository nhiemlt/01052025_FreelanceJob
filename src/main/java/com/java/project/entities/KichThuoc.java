package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "kich_thuoc")
public class KichThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_kich_thuoc", length = 50)
    private String maKichThuoc;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_kich_thuoc")
    private String tenKichThuoc;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}