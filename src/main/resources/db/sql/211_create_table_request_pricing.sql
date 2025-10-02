create table request_pricing
(
    loan_id       varchar(36)  null,
    contract_code varchar(256) null,
    status        varchar(20)  null,
    id            int auto_increment
        primary key
);