create table allotment_user
(
    id int auto_increment,
    empl_code     varchar(10) null,
    email     varchar(256) null,
    name     varchar(256) null,
    province_code varchar(10) null,
    province_name varchar(256) null,
    constraint allotment_pk
        primary key (id)
);