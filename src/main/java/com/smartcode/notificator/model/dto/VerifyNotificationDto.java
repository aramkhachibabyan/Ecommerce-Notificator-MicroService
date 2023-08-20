package com.smartcode.notificator.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class VerifyNotificationDto {

//    @NotBlank
//    @Size(min = 6, max = 6)
    private String content;

    @Email
    @NotBlank
    private String email;

    @Positive
    private Integer userId;
}
