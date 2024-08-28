create table users_and_schedules
(
    id          bigint auto_increment
        primary key,
    schedule_id bigint null,
    user_id     bigint null,
    constraint FK92h2i0509j3v4x483bsuur2ry
        foreign key (user_id) references users (user_id),
    constraint FKi6evab2jw50614r8gpnfomlof
        foreign key (schedule_id) references schedules (schedule_id)
);

