alter table `collateral_owner_map`
ADD COLUMN `type` VARCHAR(20) NOT NULL;

update collateral_owner_map set type='THIRD_PARTY' where 1 = 1;