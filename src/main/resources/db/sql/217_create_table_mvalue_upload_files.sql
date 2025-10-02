CREATE TABLE `mvalue_upload_files`
(
    `id`                  int          NOT NULL AUTO_INCREMENT,
    `created_at`          timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`          timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP (6),
    `loan_application_id` varchar(36)  NOT NULL,
    `document_mvalue_id`  varchar(36)  NOT NULL,
    `mvalue_code`         varchar(36)  NOT NULL,
    `file_name`           varchar(250)          DEFAULT NULL,
    `folder`              varchar(250)          DEFAULT NULL,
    `status`              varchar(30)           DEFAULT NULL,
    `mvalue_status`       varchar(30)           DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                   `mvalue_upload_files_loan_application_id_idx` (`loan_application_id`)
);

alter table mvalue_upload_files
add `collateral_type_ecm` varchar(50) DEFAULT NULL,
add collateral_id varchar(50) DEFAULT NULL,
add upload_user varchar(50) DEFAULT NULL,
add file_config_id varchar(36) DEFAULT NULL;