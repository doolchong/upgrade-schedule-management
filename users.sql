create table users
(
    user_id     bigint auto_increment
        primary key,
    created_at  datetime(6)            null,
    modified_at datetime(6)            null,
    email       varchar(255)           not null,
    password    varchar(255)           not null,
    role        enum ('ADMIN', 'USER') not null,
    user_name   varchar(255)           not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UKk8d0f2n7n88w1a16yhua64onx
        unique (user_name)
);

