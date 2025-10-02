create table interest_rate
(
    uuid            varchar(36)                               not null
        primary key
        unique,
    name            varchar(256)                              null,
    `key`        varchar(10)                               null,
    `from_month` int                                       null,
    `to_month`   int                                       null,
    `condition`  varchar(256)                              null,
    interest_rate   double                                    null,
    prepayment_fee  double                                    null,
    created_at      timestamp(6) default CURRENT_TIMESTAMP(6) null,
    updated_at      timestamp(6) default CURRENT_TIMESTAMP(6) null
);