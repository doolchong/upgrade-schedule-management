create table comments
(
    comment_id  bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    comment     varchar(500) null,
    writer_name varchar(255) null,
    schedule_id bigint       null,
    constraint FKbef7m370enopdpf7yp6nmv0oo
        foreign key (schedule_id) references schedules (schedule_id)
);

