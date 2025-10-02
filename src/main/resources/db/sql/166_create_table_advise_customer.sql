CREATE TABLE advise_customer
(
    uuid           varchar(36),
    customer_name  varchar(255),
    customer_phone varchar(15),
    product        varchar(255),
    province       varchar(40),
    status         varchar(7),
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
)
