ALTER TABLE `province`
ADD COLUMN `order_by` int(5) NULL AFTER `mercury_name`;

update province set order_by = mobio_code;
update province set order_by = order_by + 1 where order_by > 1;
update province set order_by = 2 where code ='79';
