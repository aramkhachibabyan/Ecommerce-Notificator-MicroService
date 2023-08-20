package com.smartcode.notificator.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private  String content;

    private String description;

    @Column(nullable = false)
    private Long notificationDateTime;

    @Column(nullable = false)
    private Long creationDateTime;

    @Column(nullable = false)
    private Boolean sent;

    @Column(nullable = false)
    private String  email;

}
