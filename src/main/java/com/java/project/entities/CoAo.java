package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "co_ao")
public class CoAo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_co_ao", length = 50)
    private String maCoAo;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_co_ao")
    private String tenCoAo;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}