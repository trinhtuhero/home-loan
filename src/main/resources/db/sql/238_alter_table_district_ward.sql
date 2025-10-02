alter table ward
add mvalue_district_code varchar(30) DEFAULT NULL,
add mvalue_province_code varchar(30) DEFAULT NULL;

alter table district
add mvalue_province_code varchar(30) DEFAULT NULL;

