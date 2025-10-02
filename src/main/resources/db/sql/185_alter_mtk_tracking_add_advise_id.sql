alter table mtk_tracking
    add mtk_type varchar(20) null;

alter table mtk_tracking
    add advise_id varchar(36) null;
alter table mtk_tracking
    modify loan_code varchar(50) null;
alter table mtk_tracking
    modify loan_id varchar(50) null;