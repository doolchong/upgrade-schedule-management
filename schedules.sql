create table schedules
(
    schedule_id      bigint auto_increment
        primary key,
    created_at       datetime(6)  null,
    modified_at      datetime(6)  null,
    schedule_content varchar(500) null,
    schedule_title   varchar(255) null,
    user_id          bigint       null,
    weather          varchar(255) null
);

