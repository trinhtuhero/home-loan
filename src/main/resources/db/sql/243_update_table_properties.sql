UPDATE properties
SET current_value = '{
    "data": {
        "income": {
            "rules": [
                {
                    "type": "min",
                    "value": "5000000",
                    "message_error": "Tổng thu nhập bạn cần có ít nhất là 5 triệu"
                },
                {
                    "required": true
                }
            ]
        },
        "monthly_payment_amount": {
            "formula": "0.7*income-2000000",
            "rules": [
                {
                    "type": "validate",
                    "condition": "(0.7*income-2000000) < value",
                    "message_error": "Dư nợ vượt quá khả năng tài chính của bạn"
                },
                {
                    "required": false
                }
            ]
        },
        "collateral_value": {
            "formula": "loan_amount/0.8",
            "rules": [
                {
                    "value": "200000000",
                    "type": "min",
                    "message_error": "Giá trị TSBĐ tối thiểu là 200 triệu đồng"
                },
                {
                    "required": true
                },
                {
                    "value": "100000000000",
                    "type": "max",
                    "message_error": "Giá trị TSBĐ tối đa là 100 tỷ"
                }
            ]
        },
        "loan_time": {
            "formulas": [
                {
                    "formula": "0.7*income-monthly_payment_amount"
                },
                {
                    "type": "case",
                    "condition": "value >= 2_000_000 && value < 30_000_000",
                    "value": 240
                },
                {
                    "type": "case",
                    "condition": "value >= 30_000_000",
                    "value": 300
                }
            ],
            "rules": [
                {
                    "required": true
                },
                {
                    "type": "min",
                    "value": 3,
                    "message_error": "Thời hạn vay tối thiểu là 3 tháng"
                },
                {
                    "type": "validate",
                    "condition": "value < 84 && debt_method === `GTD`",
                    "message_error": "Kỳ hạn vay >= 84 tháng với Phương thức trả nợ Gốc tăng dần, lãi theo dư nợ thực tế"
                },
                {
                    "type": "validate",
                    "condition": "value < 84 && debt_method === `GGD`",
                    "message_error": "Kỳ hạn vay >= 84 tháng với Phương thức trả nợ Gốc giảm dần, lãi theo dư nợ thực tế"
                },
                {
                    "type": "max",
                    "value": 300,
                    "message_error": "Thời hạn vay tối đa là 300 tháng"
                }
            ]
        },
        "loan_amount": {
            "formula": "(365*(0.7*income-monthly_payment_amount)*loan_time)/(365+loan_time*(0.126*30))",
            "rules": [
                {
                    "type": "min",
                    "value": 100000000,
                    "message_error": "Số tiền cần vay tối thiểu là 100 triệu"
                },
                {
                    "required": true
                },
                {
                    "type": "max",
                    "value": 20000000000,
                    "message_error": "Số tiền cần vay tối đa là 20 tỷ"
                },
                {
                    "condition": "collateral_value*0.8 < value",
                    "type": "validate",
                    "message_error": "Số tiền cần vay tối đa không vượt quá 80% giá trị TSBĐ"
                }
            ]
        },
        "interest_rate": {
            "values": [
                {
                    "name": "11.8% - 12 tháng đầu",
                    "key": "799",
                    "default": true
                }
            ]
        },
        "debt_method": {
            "values": [
                {
                    "name": "Gốc trả đều hàng tháng, lãi giảm dần",
                    "key": "GTDHH",
                    "default": true
                },
                {
                    "name": "Gốc trả đều 02 tháng/lần, lãi giảm dần",
                    "key": "GTD2T",
                    "default": false
                },
                {
                    "name": "Gốc trả đều 03 tháng/lần, lãi giảm dần",
                    "key": "GTD3T",
                    "default": false
                },
                {
                    "name": "Gốc tăng dần, lãi theo dư nợ thực tế",
                    "key": "GTD",
                    "default": false
                },
                {
                    "name": "Gốc giảm dần, lãi theo dư nợ thực tế",
                    "key": "GGD",
                    "default": false
                }
            ]
        },
        "status": 200
    }
}' where name = 'loan-calculation';