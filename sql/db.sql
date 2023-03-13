-- auto-generated definition
create table user
(
    id            bigint auto_increment
        primary key,
    user_name     varchar(256)                          null comment '用户名',
    user_avatar   varchar(1024)                         null comment '用户头像',
    user_account  varchar(256)                          not null comment '账号',
    user_password varchar(512)                          null comment '密码',
    phone         varchar(256)                          null comment '手机号',
    access_key    varchar(1024)                         not null comment '公钥',
    secret_key    varchar(1024)                         not null comment '私钥',
    role          varchar(50) default 'user'            not null comment '角色(user普通用户admin管理员）',
    status        tinyint     default 0                 not null comment '禁用状态（0未禁用，1禁用）',
    create_time   datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint     default 0                 not null comment '是否删除(0未删除1已删除）',
    constraint access_key
        unique (access_key),
    constraint id
        unique (id),
    constraint phone
        unique (phone),
    constraint secret_key
        unique (secret_key),
    constraint user_account
        unique (user_account)
)
    comment '用户表';

