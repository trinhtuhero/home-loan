create table IF NOT EXISTS `loan_advise_customer`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `loan_application_id`   VARCHAR(36) NOT NULL,
    `advise_date`   DATETIME NULL,
    `advise_time_frame`   VARCHAR(100) NULL,
    `content`  TEXT NULL,
    `created_at` timestamp(6) default CURRENT_TIMESTAMP(6) null,
    `updated_at` timestamp(6) default CURRENT_TIMESTAMP(6) null,
    PRIMARY KEY (`uuid`)
);
