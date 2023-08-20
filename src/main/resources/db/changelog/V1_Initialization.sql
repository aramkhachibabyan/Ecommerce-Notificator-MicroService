create table notifications
(
    id                     serial primary key,
    content                varchar(255) not null,
    creation_date_time     bigint       not null,
    description            varchar(255),
    email                  varchar(255) not null,
    notification_date_time bigint       not null,
    sent                   boolean      not null,
    title                  varchar(255) not null,
    user_id                integer      not null
);