alter table `ward`
ADD COLUMN `mobio_code` VARCHAR(50) NULL AFTER `name`,
CHANGE COLUMN `name` `name` VARCHAR(150) NOT NULL ;

update ward set mobio_code = CONVERT(code, decimal);



