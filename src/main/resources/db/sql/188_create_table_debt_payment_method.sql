create table debt_payment_method
(
    uuid              varchar(36)                               not null
        primary key
        unique,
    name              varchar(256)                              null,
    `key`             varchar(10)                               null,
    `from_month`            int                                       null,
    `to_month`              int                                       null,
    `condition`       varchar(256)                              null,
    formula_principal varchar(256)                              null,
    formula_interest  varchar(256)                              null,
    created_at        timestamp(6) default CURRENT_TIMESTAMP(6) null,
    updated_at        timestamp(6) default CURRENT_TIMESTAMP(6) null
);