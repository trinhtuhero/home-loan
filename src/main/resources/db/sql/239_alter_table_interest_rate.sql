UPDATE organization set area_code='AREA6A' where code ='2104';


delete from interest_rate where `key` in ('499','699','875','915');

update interest_rate set name = '11.8% - 12 tháng đầu';

update interest_rate set interest_rate = '11.8' where prepayment_fee = 3;

update interest_rate set interest_rate = '14.6' where prepayment_fee in ('0','1','2');