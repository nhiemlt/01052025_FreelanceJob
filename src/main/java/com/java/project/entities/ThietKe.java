package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "thiet_ke")
public class ThietKe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_thiet_ke", length = 50)
    private String maThietKe;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_thiet_ke")
    private String tenThietKe;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}