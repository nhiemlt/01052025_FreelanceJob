package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "vai_tro")
public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_vai_tro", length = 50)
    private String maVaiTro;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_vai_tro")
    private String tenVaiTro;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}