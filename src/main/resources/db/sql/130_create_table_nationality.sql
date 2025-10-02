create TABLE IF NOT EXISTS `nationality`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `code`       VARCHAR(30) NOT NULL,
    `name`  VARCHAR(150)  NULL,
    PRIMARY KEY (`code`)
);


insert into nationality (code,name) values ('VIETNAM','Việt Nam');