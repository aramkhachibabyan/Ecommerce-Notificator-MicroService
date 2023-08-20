package com.smartcode.notificator.controller;

import com.smartcode.notificator.model.dto.CreateNotificationDto;
import com.smartcode.notificator.model.dto.NotificationDto;
import com.smartcode.notificator.model.dto.VerifyNotificationDto;
import com.smartcode.notificator.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
@Validated
public class NotificationController {
    private final NotificationService notificationService;

//    @PostMapping
//    public ResponseEntity<NotificationDto> create(@RequestBody @Valid CreateNotificationDto dto) {
//        return ResponseEntity.ok(notificationService.create(dto));
//    }

    @GetMapping("/ready")
    public ResponseEntity<List<NotificationDto>> getReady(@RequestParam @Positive Integer userId){
        return ResponseEntity.ok(notificationService.getNotifications(true,userId));
    }

    @GetMapping("/waiting")
    public ResponseEntity<List<NotificationDto>> getSent(@RequestParam @Positive Integer userId){
        return ResponseEntity.ok(notificationService.getNotifications(false,userId));
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify(@RequestBody @Valid VerifyNotificationDto dto){
        notificationService.verify(dto);
        return ResponseEntity.ok().build();
    }


}
