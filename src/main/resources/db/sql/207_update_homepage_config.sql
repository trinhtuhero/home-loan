UPDATE properties
SET current_value = '{
   "data": {
     "banner": {
       "slide": [
         {
           "text1": "Giải pháp vay thế chấp trực tuyến ưu việt",
           "text2": "Hiện thực hoá mọi kế hoạch, vươn tầm ước mơ cùng MSB",
           "image": [
             "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_banner_1.png"
           ],
           "image-mobile": [
             "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_mobile_banner_1.png"
           ]
         },
         {
           "text1": "Giải pháp vay thế chấp trực tuyến ưu việt",
           "text2": "Hiện thực hoá mọi kế hoạch, vươn tầm ước mơ cùng MSB",
           "image": [
             "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_banner_1.png"
           ],
           "image-mobile": [
             "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_mobile_banner_1.png"
           ]
         },
         {
           "text1": "Giải pháp vay thế chấp trực tuyến ưu việt",
           "text2": "Hiện thực hoá mọi kế hoạch, vươn tầm ước mơ cùng MSB",
           "image": [
             "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_banner_1.png"
           ],
           "image-mobile": [
             "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_mobile_banner_1.png"
           ]
         }
       ],
       "background": ""
     },
     "benefit": {
       "text": "Lý do chọn vay thế chấp tại MSB",
       "item": [
         {
           "icon": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_loi_ich_icon_1.svg",
           "text1": "Đăng ký vay 100% trực tuyến",
           "text2": "Need content"
         },
         {
           "icon": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_loi_ich_icon_1.svg",
           "text1": "Hồ sơ vay đơn giản, thủ tục nhanh gọn",
           "text2": "Need content"
         },
         {
           "icon": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_loi_ich_icon_1.svg",
           "text1": "Linh hoạt thiết kế phương án vay phù hợp",
           "text2": "Need content"
         }
       ],
       "condition": [
         "Độ tuổi từ 20 đến 65 tuổi",
         "Thu nhập tối thiểu 5 triệu đồng/tháng"
       ],
       "collateral": [
         "Có thể dùng chính bất động sản dự định mua/Bất động sản khác hoặc Giấy tờ có giá để thế chấp"
       ],
       "loan_purpose": [
         "Vay mua bất động sản đã có sổ đỏ/chưa sang tên sổ đỏ cho bạn hoặc người thân. Áp dụng cho cả trường hợp bù đắp phần vốn vay cá nhân để mua bất động sản."
       ]
     },
     "product": {
       "text1": "Giải pháp vay thế chấp",
       "text2": "Đáp ứng đa dạng nhu cầu của bạn",
       "item": [
         {
           "image": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_san_pham_vay_mua_nha_tho_cu.png",
           "text1": "Vay mua nhà thổ cư",
           "text2": "Tậu nhà liền tay - Click vay trực tuyến",
           "code": "BAT_DONG_SAN"
         },
         {
           "image": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_san_pham_vay_tieu_dung.png",
           "text1": "Vay tiêu dùng thế chấp",
           "text2": "Nâng tầm cuộc sống - Vươn tầm ước mơ",
           "code": "TIEU_DUNG"
         },
         {
           "image": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_san_pham_vay_xay_sua_nha.png",
           "text1": "Vay xây sửa nhà",
           "text2": "Nhà đẹp khang trang - Ngập tràn hạnh phúc",
           "code": "XAY_SUA_NHA"
         },
         {
           "image": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_san_pham_vay_kinh_doanh.png",
           "text1": "Vay thế chấp kinh doanh",
           "text2": "Vốn về liền tay, click vay trực tuyến",
           "code": "HOUSEHOLD"
         }
       ]
     },
     "process": {
       "text1": "4 bước vay vốn dễ dàng",
       "text2": [
         "Đăng ký và nộp hồ sơ 100% trực tuyến",
         "Thẩm định hồ sơ và thông báo kết quả phê duyệt",
         "Ký kết hợp đồng",
         "Nhận vốn vay"
       ],
       "image": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/vay_the_chap_bon_buoc_vay_von_de_dang.png"
     }
   },
   "status": 200
 }' where `name` = 'homepage';

UPDATE properties
SET current_value = '{
   "data": {
     "header": {
       "product": [
         {
           "code": "BAT_DONG_SAN",
           "text": "Vay mua nhà thổ cư"
         },
         {
           "code": "TIEU_DUNG",
           "text": "Vay tiêu dùng"
         },
         {
           "code": "XAY_SUA_NHA",
           "text": "Vay xây sửa nhà"
         },
         {
           "code": "HOUSEHOLD",
           "text": "Vay thế chấp kinh doanh"
         }
       ],
       "logo": "https://hl.dev.df.msb.com.vn/static/media/msb-logo.c6d8abcde672a9633c30a2baeada159f.svg"
     },
     "footer": {
       "contact": {
         "contact_bank": "Ngân hàng TMCP Hàng Hải Việt Nam",
         "contact_address": "Trụ sở chính: 54A Nguyễn Chí Thanh, Phường Láng Thượng, Quận Đống Đa, Thành phố Hà Nội",
         "contact_email": "cskhcanhan@msb.com.vn",
         "contact_phone": "1900 6083"
       },
       "network": [
         {
           "icon": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/network_facebook.svg",
           "text": "Facebook",
           "link": "https://www.facebook.com/MSBVietnam/"
         },
         {
           "icon": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/network_msb.svg",
           "text": "Website",
           "link": "https://www.msb.com.vn/"
         },
         {
           "icon": "https://common-services-dev-public-cj2.s3.ap-southeast-1.amazonaws.com/static/network_zalo.svg",
           "text": "Zalo",
           "link": "https://zalo.me/3788059764700985421"
         }
       ]
     }
   },
   "status": 200
 }' where name = 'header-footer';