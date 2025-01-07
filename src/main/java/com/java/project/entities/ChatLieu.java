package com.java.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "chat_lieu")
public class ChatLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma_chat_lieu", length = 50)
    private String maChatLieu;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ten_chat_lieu")
    private String tenChatLieu;

    @Column(name = "trang_thai", columnDefinition = "tinyint")
    private Short trangThai;

}