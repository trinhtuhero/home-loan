create TABLE IF NOT EXISTS `card_type`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `code`       VARCHAR(50) NOT NULL,
    `name`  VARCHAR(150) NOT NULL,
    PRIMARY KEY (`code`)
);

insert into card_type(code, name) values('MASTER_BLUE', 'Master Blue');
insert into card_type(code, name) values('MASTER_WHITE', 'Master White');
insert into card_type(code, name) values('MASTER_PLATINUM', 'Master Platinum');
insert into card_type(code, name) values('MASTER_PLATINUM_FCB', 'Master Platinum FCB');
insert into card_type(code, name) values('MASTER_BLUE_STAFF_MSB', 'Master Blue cho Staff MSB');
insert into card_type(code, name) values('MASTER_WHITE_STAFF_MSB', 'Master White cho Staff MSB');
insert into card_type(code, name) values('MASTER_PLATINUM_STAFF_MSB', 'Master Platinum cho Staff MSB');
insert into card_type(code, name) values('MASTER_LOTTE', 'Master Lotte');
insert into card_type(code, name) values('MASTER_LOTTE_STAFF_LOTTE', 'Master Lotte cho Staff của Lotte');
insert into card_type(code, name) values('MASTER_VPOINT', 'Master Vpoint');
insert into card_type(code, name) values('MASTER_VPOINT_STAFF_VNPT', 'Master Vpoint cho Staff của VNPT');
insert into card_type(code, name) values('VISA_TRAVEL', 'Visa Travel');
insert into card_type(code, name) values('VISA_TRAVEL_STAFF_MSB', 'Visa Travel cho Staff MSB');
insert into card_type(code, name) values('VISA_ONLINE', 'Visa Online');
insert into card_type(code, name) values('VISA_ONLINE_STAFF_MSB', 'Visa Online cho Staff MSB');
insert into card_type(code, name) values('NEW_NEVER_USED', 'Mới chưa dùng');
insert into card_type(code, name) values('VISA_DINING_SIGNATURE', 'Visa Dinning Signature');
insert into card_type(code, name) values('VISA_DINING_SIGNATURE_STAFF_MSB', 'Visa Dinning Signature cho Staff MSB');
insert into card_type(code, name) values('VISA_DINING_SIGNATURE_MFIRST', 'Visa Dinning Signature cho Mfirst');
insert into card_type(code, name) values('MSB_MASTERCARD_DAILY_SHOPPING', 'MSB Mastercard Daily Shopping');
insert into card_type(code, name) values('MSB_MASTERCARD_DAILY_SHOPPING_STAFF_MSB', 'MSB Mastercard Daily Shopping cho Staff MSB');
insert into card_type(code, name) values('MSB_MASTERCARD_MDIGI_MAIN_CARD', 'MSB MasterCard mDigi Main Card');
insert into card_type(code, name) values('MSB_MASTERCARD_MDIGI_SUB_CARD', 'MSB MasterCard mDigi Sub Card');
insert into card_type(code, name) values('MSB_MASTERCARD_MDIGI_STAFF_MAIN_CARD', 'MSB MasterCard mDigi Staff Main Card');
insert into card_type(code, name) values('MSB_MASTERCARD_MDIGI_STAFF_SUB_CARD', 'MSB MasterCard mDigi Staff Sub Card');