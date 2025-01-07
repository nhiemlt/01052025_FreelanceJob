package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "mo_ta")
public class MoTa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Lob
    @Column(name = "noi_dung_mo_ta")
    private String noiDungMoTa;

    @Size(max = 255)
    @Nationalized
    @Column(name = "hinh_anh_mo_ta")
    private String hinhAnhMoTa;

}