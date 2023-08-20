package com.smartcode.notificator.mapper;

import com.smartcode.notificator.model.dto.CreateNotificationDto;
import com.smartcode.notificator.model.dto.NotificationDto;
import com.smartcode.notificator.model.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationEntity toEntity(CreateNotificationDto cardDto);
    NotificationDto toDto(NotificationEntity cardEntity);
}
