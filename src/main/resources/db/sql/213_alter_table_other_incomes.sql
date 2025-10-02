alter table other_incomes
    add income_per_month bigint null;

alter table other_incomes
    add quantity int null;

create table land_transaction
(
    uuid                varchar(36)                               not null
        primary key,
    transaction_time  datetime                                  null,
    property_sale     varchar(256)                              null,
    buyer             varchar(256)                              null,
    transaction_value bigint                                    null,
    initial_capital   bigint                                    null,
    brokerage_fees    bigint                                    null,
    fee_transfer      bigint                                    null,
    profit            bigint                                    null,
    other_income_id   varchar(36)                               not null,
    created_at        timestamp(6) default CURRENT_TIMESTAMP(6) null,
    updated_at        timestamp(6) default CURRENT_TIMESTAMP(6) null
);
