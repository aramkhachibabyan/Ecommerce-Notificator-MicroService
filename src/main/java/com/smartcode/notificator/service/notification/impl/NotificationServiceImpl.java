package com.smartcode.notificator.service.notification.impl;

import com.smartcode.notificator.mapper.NotificationMapper;
import com.smartcode.notificator.model.dto.CreateNotificationDto;
import com.smartcode.notificator.model.dto.NotificationDto;
import com.smartcode.notificator.model.dto.VerifyNotificationDto;
import com.smartcode.notificator.model.entity.NotificationEntity;
import com.smartcode.notificator.repository.NotificationRepository;
import com.smartcode.notificator.service.notification.NotificationService;
import com.smartcode.notificator.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public NotificationDto create(CreateNotificationDto notificationDto) {
        NotificationEntity entity = notificationMapper.toEntity(notificationDto);
        entity.setNotificationDateTime(
                notificationDto.getNotificationDate().atZone(
                        ZoneId.systemDefault()).toInstant().toEpochMilli());
        entity.setCreationDateTime(Instant.now().toEpochMilli());
        entity.setSent(false);
        NotificationEntity save = notificationRepository.save(entity);
        NotificationDto dto = notificationMapper.toDto(save);
        dto.setNotificationDate(notificationDto.getNotificationDate());
        dto.setCreationDate(Instant.ofEpochMilli(save.getCreationDateTime())
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotifications(Boolean ready, Integer userId) {
        List<NotificationEntity> allBySentAndUserId = notificationRepository.findAllBySentAndUserId(ready, userId);
        List<NotificationDto> list = new ArrayList<>();
        for (int i = 0; i < allBySentAndUserId.size(); i++) {
            NotificationDto dto = notificationMapper.toDto(allBySentAndUserId.get(i));
            dto.setNotificationDate(
                    Instant.ofEpochMilli(allBySentAndUserId.get(i).getNotificationDateTime())
                            .atZone(ZoneId.systemDefault()).toLocalDateTime());
            dto.setCreationDate(
                    Instant.ofEpochMilli(allBySentAndUserId.get(i).getCreationDateTime())
                            .atZone(ZoneId.systemDefault()).toLocalDateTime());
            list.add(dto);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationEntity> getReadyNotifications() {
        return notificationRepository.getReadyNotifications(System.currentTimeMillis());
    }

    @Override
    @Async("notificationSenderExecutors")
    public void sendNotifications(NotificationEntity entity) throws InterruptedException {
        long l = entity.getNotificationDateTime() - System.currentTimeMillis();
        Thread.sleep(l);
        emailService.sendSimpleMessage(entity.getEmail(),entity.getTitle(),entity.getContent());
        entity.setSent(true);
        notificationRepository.save(entity);
    }

    @Override
    @Transactional
    public void verify(VerifyNotificationDto dto) {
        emailService.sendSimpleMessage(dto.getEmail(), "Verification",
                "Your verification code is " + dto.getContent());
        NotificationEntity entity = new NotificationEntity();
        entity.setContent(dto.getContent());
        entity.setSent(true);
        entity.setEmail(dto.getEmail());
        entity.setTitle("Verification");
        entity.setUserId(dto.getUserId());
        entity.setCreationDateTime(Instant.now().toEpochMilli());
        entity.setNotificationDateTime(Instant.now().toEpochMilli());
        notificationRepository.save(entity);
    }
}
