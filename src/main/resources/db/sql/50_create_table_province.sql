create TABLE IF NOT EXISTS `province`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `code`       VARCHAR(30) NOT NULL,
    `name`  VARCHAR(150)  NULL,
    `mobio_code`    VARCHAR(30)  NULL,
    `mercury_name`    VARCHAR(150)  NULL,
    PRIMARY KEY (`code`)
);

/*
-- Query: SELECT * FROM homeloan.province
LIMIT 0, 1000

-- Date: 2022-08-08 11:39
*/
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('01','Thành phố Hà Nội','1','Thành phố Hà Nội');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('02','Tỉnh Hà Giang','2','Tỉnh Hà Giang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('04','Tỉnh Cao Bằng','4','Tỉnh Cao Bằng');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('06','Tỉnh Bắc Kạn','6','Tỉnh Bắc Kạn');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('08','Tỉnh Tuyên Quang','8','Tỉnh Tuyên Quang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('10','Tỉnh Lào Cai','10','Tỉnh Lào Cai');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('11','Tỉnh Điện Biên','11','Tỉnh Điện Biên');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('12','Tỉnh Lai Châu','12','Tỉnh Lai Châu');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('14','Tỉnh Sơn La','14','Tỉnh Sơn La');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('15','Tỉnh Yên Bái','15','Tỉnh Yên Bái');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('17','Tỉnh Hoà Bình','17','Tỉnh Hoà Bình');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('19','Tỉnh Thái Nguyên','19','Tỉnh Thái Nguyên');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('20','Tỉnh Lạng Sơn','20','Tỉnh Lạng Sơn');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('22','Tỉnh Quảng Ninh','22','Tỉnh Quảng Ninh');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('24','Tỉnh Bắc Giang','24','Tỉnh Bắc Giang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('25','Tỉnh Phú Thọ','25','Tỉnh Phú Thọ');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('26','Tỉnh Vĩnh Phúc','26','Tỉnh Vĩnh Phúc');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('27','Tỉnh Bắc Ninh','27','Tỉnh Bắc Ninh');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('30','Tỉnh Hải Dương','30','Tỉnh Hải Dương');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('31','Thành phố Hải Phòng','31','Thành phố Hải Phòng');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('33','Tỉnh Hưng Yên','33','Tỉnh Hưng Yên');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('34','Tỉnh Thái Bình','34','Tỉnh Thái Bình');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('35','Tỉnh Hà Nam','35','Tỉnh Hà Nam');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('36','Tỉnh Nam Định','36','Tỉnh Nam Định');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('37','Tỉnh Ninh Bình','37','Tỉnh Ninh Bình');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('38','Tỉnh Thanh Hóa','38','Tỉnh Thanh Hóa');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('40','Tỉnh Nghệ An','40','Tỉnh Nghệ An');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('42','Tỉnh Hà Tĩnh','42','Tỉnh Hà Tĩnh');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('44','Tỉnh Quảng Bình','44','Tỉnh Quảng Bình');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('45','Tỉnh Quảng Trị','45','Tỉnh Quảng Trị');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('46','Tỉnh Thừa Thiên Huế','46','Tỉnh Thừa Thiên Huế');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('48','Thành phố Đà Nẵng','48','Thành phố Đà Nẵng');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('49','Tỉnh Quảng Nam','49','Tỉnh Quảng Nam');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('51','Tỉnh Quảng Ngãi','51','Tỉnh Quảng Ngãi');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('52','Tỉnh Bình Định','52','Tỉnh Bình Định');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('54','Tỉnh Phú Yên','54','Tỉnh Phú Yên');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('56','Tỉnh Khánh Hòa','56','Tỉnh Khánh Hòa');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('58','Tỉnh Ninh Thuận','58','Tỉnh Ninh Thuận');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('60','Tỉnh Bình Thuận','60','Tỉnh Bình Thuận');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('62','Tỉnh Kon Tum','62','Tỉnh Kon Tum');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('64','Tỉnh Gia Lai','64','Tỉnh Gia Lai');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('66','Tỉnh Đắk Lắk','66','Tỉnh Đắk Lắk');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('67','Tỉnh Đắk Nông','67','Tỉnh Đắk Nông');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('68','Tỉnh Lâm Đồng','68','Tỉnh Lâm Đồng');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('70','Tỉnh Bình Phước','70','Tỉnh Bình Phước');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('72','Tỉnh Tây Ninh','72','Tỉnh Tây Ninh');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('74','Tỉnh Bình Dương','74','Tỉnh Bình Dương');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('75','Tỉnh Đồng Nai','75','Tỉnh Đồng Nai');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('77','Tỉnh Bà Rịa - Vũng Tàu','77','Tỉnh Bà Rịa - Vũng Tàu');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('79','Thành phố Hồ Chí Minh','79','Thành phố Hồ Chí Minh');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('80','Tỉnh Long An','80','Tỉnh Long An');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('82','Tỉnh Tiền Giang','82','Tỉnh Tiền Giang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('83','Tỉnh Bến Tre','83','Tỉnh Bến Tre');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('84','Tỉnh Trà Vinh','84','Tỉnh Trà Vinh');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('86','Tỉnh Vĩnh Long','86','Tỉnh Vĩnh Long');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('87','Tỉnh Đồng Tháp','87','Tỉnh Đồng Tháp');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('89','Tỉnh An Giang','89','Tỉnh An Giang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('91','Tỉnh Kiên Giang','91','Tỉnh Kiên Giang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('92','Thành phố Cần Thơ','92','Thành phố Cần Thơ');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('93','Tỉnh Hậu Giang','93','Tỉnh Hậu Giang');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('94','Tỉnh Sóc Trăng','94','Tỉnh Sóc Trăng');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('95','Tỉnh Bạc Liêu','95','Tỉnh Bạc Liêu');
INSERT INTO province (code,name,mobio_code,mercury_name) VALUES ('96','Tỉnh Cà Mau','96','Tỉnh Cà Mau');
