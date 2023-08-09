package com.smartcode.notificator.service.impl;

import com.smartcode.notificator.mapper.NotificationMapper;
import com.smartcode.notificator.model.CreateNotificationDto;
import com.smartcode.notificator.model.NotificationDto;
import com.smartcode.notificator.model.NotificationEntity;
import com.smartcode.notificator.repository.NotificationRepository;
import com.smartcode.notificator.service.NotificationService;
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
    @Async
    public void sendNotifications(NotificationEntity entity) {

    }
}
