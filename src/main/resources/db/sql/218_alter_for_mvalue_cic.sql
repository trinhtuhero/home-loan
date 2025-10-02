alter table `organization`
    add `mvalue_code` varchar(30) DEFAULT NULL;


alter table collaterals
    add `so_ts_mvalue` varchar(50) DEFAULT NULL;

alter table province
    add mvalue_code varchar(30) default null,
add mvalue_name varchar(150) default null;

alter table district
    add mvalue_code varchar(30) default null,
add mvalue_name varchar(150) default null;

alter table ward
    add mvalue_code varchar(30) default null,
add mvalue_name varchar(150) default null;