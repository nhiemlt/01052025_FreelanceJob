package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "mau_sac")
public class MauSac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_mau_sac", length = 50)
    private String maMauSac;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_mau_sac")
    private String tenMauSac;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}