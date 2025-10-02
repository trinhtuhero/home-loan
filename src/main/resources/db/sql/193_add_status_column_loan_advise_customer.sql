alter table loan_advise_customer
    add status varchar(25) null;

create index loan_advise_customer_loan_application_id_idx
    on loan_advise_customer (loan_application_id);