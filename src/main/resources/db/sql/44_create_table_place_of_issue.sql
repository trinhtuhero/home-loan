create TABLE IF NOT EXISTS `place_of_issue_id_card`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `code`       VARCHAR(50) NOT NULL,
    `name`       VARCHAR(150) NULL,
    `province`    VARCHAR(150) NULL,
    PRIMARY KEY (uuid)
);

CREATE INDEX place_of_issue_id_card_code_idx
ON place_of_issue_id_card (code);

Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '1', 'Cục Cảnh sát ĐKQL Cư trú và DLQG về dân cư', 'Cục Cảnh sát ĐKQL Cư trú và DLQG về dân cư');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '2', 'Cục Cảnh sát QLHC về TTXH', 'Cục Cảnh sát QLHC về TTXH');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '3', 'Cục Quản lý xuất nhập cảnh', 'Cục Quản lý xuất nhập cảnh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '4', 'Cục Quản lý khác', 'Cục Quản lý khác');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '5', 'Công an Thành phố Hà Nội ', 'Hà Nội');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '6', 'Công an Thành phố Hồ Chí Minh', 'Hồ Chí Minh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '7', 'Công an Tỉnh An Giang', 'An Giang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '8', 'Công an Tỉnh Bà Rịa – Vũng Tàu', 'Bà Rịa – Vũng Tàu');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '9', 'Công an Tỉnh Bạc Liêu', 'Bạc Liêu');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '10', 'Công an Tỉnh Bắc Giang', 'Bắc Giang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '11', 'Công an Tỉnh Bắc Kạn', 'Bắc Kạn');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '12', 'Công an Tỉnh Bắc Ninh', 'Bắc Ninh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '13', 'Công an Tỉnh Bến Tre', 'Bến Tre');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '14', 'Công an Tỉnh Bình Dương', 'Bình Dương');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '15', 'Công an Tỉnh Bình Định', 'Bình Định');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '16', 'Công an Tỉnh Bình Phước', 'Bình Phước');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '17', 'Công an Tỉnh Bình Thuận', 'Bình Thuận');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '18', 'Công an Tỉnh Cao Bằng', 'Cao Bằng');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '19', 'Công an Tỉnh Cà Mau', 'Cà Mau');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '20', 'Công an Thành phố Cần Thơ', 'Cần Thơ');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '21', 'Công an Thành phố Hải Phòng', 'Hải Phòng');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '22', 'Công an Thành phố Đà Nẵng', 'Đà Nẵng');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '23', 'Công an Tỉnh Gia Lai', 'Gia Lai');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '24', 'Công an Tỉnh Hòa Bình', 'Hòa Bình');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '25', 'Công an Tỉnh Hà Giang', 'Hà Giang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '26', 'Công an Tỉnh Hà Nam', 'Hà Nam');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '27', 'Công an Tỉnh Hà Tĩnh', 'Hà Tĩnh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '28', 'Công an Tỉnh Hưng Yên', 'Hưng Yên');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '29', 'Công an Tỉnh Hải Dương', 'Hải Dương');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '30', 'Công an Tỉnh Hậu Giang', 'Hậu Giang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '31', 'Công an Tỉnh Điện Biên', 'Điện Biên');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '32', 'Công an Tỉnh Đắk Lắk', 'Đắk Lắk');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '33', 'Công an Tỉnh Đắk Nông', 'Đắk Nông');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '34', 'Công an Tỉnh Đồng Nai', 'Đồng Nai');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '35', 'Công an Tỉnh Đồng Tháp', 'Đồng Tháp');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '36', 'Công an Tỉnh Khánh Hòa', 'Khánh Hòa');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '37', 'Công an Tỉnh Kiên Giang', 'Kiên Giang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '38', 'Công an Tỉnh Kon Tum', 'Kon Tum');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '39', 'Công an Tỉnh Lai Châu', 'Lai Châu');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '40', 'Công an Tỉnh Long An', 'Long An');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '41', 'Công an Tỉnh Lào Cai', 'Lào Cai');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '42', 'Công an Tỉnh Lâm Đồng', 'Lâm Đồng');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '43', 'Công an Tỉnh Lạng Sơn', 'Lạng Sơn');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '44', 'Công an Tỉnh Nam Định', 'Nam Định');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '45', 'Công an Tỉnh Nghệ An', 'Nghệ An');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '46', 'Công an Tỉnh Ninh Bình', 'Ninh Bình');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '47', 'Công an Tỉnh Ninh Thuận', 'Ninh Thuận');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '48', 'Công an Tỉnh Phú Thọ', 'Phú Thọ');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '49', 'Công an Tỉnh Phú Yên', 'Phú Yên');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '50', 'Công an Tỉnh Quảng Bình', 'Quảng Bình');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '51', 'Công an Tỉnh Quảng Nam', 'Quảng Nam');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '52', 'Công an Tỉnh Quảng Ngãi', 'Quảng Ngãi');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '53', 'Công an Tỉnh Quảng Ninh', 'Quảng Ninh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '54', 'Công an Tỉnh Quảng Trị', 'Quảng Trị');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '55', 'Công an Tỉnh Sóc Trăng', 'Sóc Trăng');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '56', 'Công an Tỉnh Sơn La', 'Sơn La');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '57', 'Công an Tỉnh Thanh Hóa ', 'Thanh Hóa');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '58', 'Công an Tỉnh Thái Bình ', 'Thái Bình');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '59', 'Công an Tỉnh Thái Nguyên', 'Thái Nguyên');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '60', 'Công an Tỉnh Thừa Thiên – Huế', 'Thừa Thiên – Huế');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '61', 'Công an Tỉnh Tiền Giang', 'Tiền Giang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '62', 'Công an Tỉnh Trà Vinh', 'Trà Vinh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '63', 'Công an Tỉnh Tuyên Quang', 'Tuyên Quang');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '64', 'Công an Tỉnh Tây Ninh', 'Tây Ninh');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '65', 'Công an Tỉnh Vĩnh Long', 'Vĩnh Long');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '66', 'Công an Tỉnh Vĩnh Phúc', 'Vĩnh Phúc');
Insert into place_of_issue_id_card(uuid, code, name, province) values(uuid(), '67', 'Công an Tỉnh Yên Bái', 'Yên Bái');
