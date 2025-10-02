create TABLE IF NOT EXISTS `product_code_config`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `uuid`        VARCHAR(36) NOT NULL,
    `product_code`        VARCHAR(100) NOT NULL,
    `loan_time_from`        int(5) NOT NULL,
    `loan_time_to`        int(5) NOT NULL,
    `approval_flow`      VARCHAR(50) NOT NULL,
    `income_method`      VARCHAR(50) NOT NULL,
    PRIMARY KEY (`uuid`)
);

insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFHLT - HOMELOAN ONLINE - NGẮN HẠN', 0, 12, 'NORMALLY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFHLT - HOMELOAN ONLINE - TRUNG HẠN', 13, 60, 'NORMALLY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFHLT - HOMELOAN ONLINE - DÀI HẠN', 61, 1000, 'NORMALLY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFMHT - MHOUSING ONLINE - NGẮN HẠN', 0, 12, 'NORMALLY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFMHT - MHOUSING ONLINE - TRUNG HẠN', 13, 60, 'NORMALLY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFMHT - MHOUSING ONLINE - DÀI HẠN', 61, 1000, 'NORMALLY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFHLN - HOMELOAN ONLINE NHANH - NGẮN HẠN', 0, 12, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFHLN - HOMELOAN ONLINE NHANH - TRUNG HẠN', 13, 60, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFHLN - HOMELOAN ONLINE NHANH - DÀI HẠN', 61, 1000, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFMQD - MHOUSING ONLINE QUY ĐỔI - NGẮN HẠN', 0, 12, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFMQD - MHOUSING ONLINE QUY ĐỔI - TRUNG HẠN', 13, 60, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFMQD - MHOUSING ONLINE QUY ĐỔI - DÀI HẠN', 61, 1000, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFHQD - HOMELOAN ONLINE QUY ĐỔI - NGẮN HẠN', 0, 12, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFHQD - HOMELOAN ONLINE QUY ĐỔI - TRUNG HẠN', 13, 60, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFHQD - HOMELOAN ONLINE QUY ĐỔI - DÀI HẠN', 61, 1000, 'FAST', 'EXCHANGE');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFHLT - HOMELOAN ONLINE - NGẮN HẠN', 0, 12, 'PRIORITY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFHLT - HOMELOAN ONLINE - TRUNG HẠN', 13, 60, 'PRIORITY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFHLT - HOMELOAN ONLINE - DÀI HẠN', 61, 1000, 'PRIORITY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNSTDFMHT - MHOUSING ONLINE - NGẮN HẠN', 0, 12, 'PRIORITY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNMTDFMHT - MHOUSING ONLINE - TRUNG HẠN', 13, 60, 'PRIORITY', 'ACTUALLY_RECEIVED');
insert into product_code_config(uuid, product_code, loan_time_from, loan_time_to, approval_flow, income_method) values(uuid(), 'RLNLTDFMHT - MHOUSING ONLINE - DÀI HẠN', 61, 1000, 'PRIORITY', 'ACTUALLY_RECEIVED');
