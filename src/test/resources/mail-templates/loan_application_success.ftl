<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Loan Product</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <style type="text/css">
    body {
      font-family: lato, "helvetica neue", helvetica, arial, sans-serif;
      font-size: 14px;
      mso-background: #FFFFFF;
    }

    .wrapper {
      background-color: #eb6719;
      mso-background: #EB6719;
      width: 600px;
      Margin: 0 auto;
    }

    .content {
      background-color: #ffffff;
      mso-background: #FFFFFF;
      color: #090909;
    }

    .footer {
      background-color: #090909;
      mso-background: #090909;
      color: #FFFFFF;
    }

    .msb-logo {
      width: 36%;
    }

    .row:after {
      content: "";
      display: table;
      clear: both;
      Margin-Bottom: 10px;
    }

    .column {
      float: left;
    }

    .left {
      width: 32%;
    }

    .right {
      width: 68%;
    }

    @media (max-width: 600px) {
      .msb-logo {
        width: 50%;
      }

      .wrapper {
        width: 340px !important;
        padding-bottom: 10px !important;
      }

      .container {
        width: 320px !important;
        padding: 10px !important;
        -webkit-padding-start: 10px;
        -webkit-padding-before: 10px;
      }

      .message {
        padding: 15px 10px !important;
      }

      .left {
        width: 47% !important;
      }

      .right {
        width: 53% !important;
      }
    }
  </style>
</head>

<body>
<div id="cmm-email" style="background-color:#ffffff">
  <div class="wrapper" style="background-color:#eb6719;width:600px;Margin:0 auto">
    <div class="container" style="padding:20px;Margin:0 auto">
      <div style="text-align:center;padding-top:20px;padding-bottom:20px">
        <img class="msb-logo" src="<#if logoStaticUrl??>${logoStaticUrl}</#if>" style="width:120px"
             alt="MSB Logo"/>
      </div>
      <div class="content" style="background-color:#ffffff;color:#090909">
        <div style="border-radius:5px">
          <img src="<#if headerStaticUrl??>${headerStaticUrl}</#if>" alt="" style="width:100%"/>
        </div>
        <div class="message" style="padding:30px;text-align:justify;line-height:25px">
          <p style="Margin-Top:0;font-size:20px">
            Kính gửi Quý Khách hàng <#if name??>${name}</#if>,
          </p>
          <p>Chúc mừng Quý Khách hàng đã nộp hồ sơ vay thành công tại Ngân hàng MSB!
            lúc <#if successTime??>${successTime}</#if></p>
          <p>Tình trạng xử lý hồ sơ của Quý Khách hàng sẽ được thông báo qua email hoặc Khách hàng
            có thể chủ động theo dõi. Trân trọng cảm ơn Quý Khách!</p>
          <p style="text-align: center; margin: 24px;">
            <a href="<#if danhSachVayUrl??>${danhSachVayUrl}</#if>"
               style="color: #FFFFFF; padding: 10px 12px; background-color: #eb6719; border: none; border-radius: 4px;text-decoration:none">
              Theo dõi khoản vay
            </a>
          </p>
          <p>
            Nếu Quý Khách hàng gặp khó khăn khi thao tác, MSB sẽ liên hệ và hỗ trợ trực tiếp trong
            thời gian sớm nhất có thể.
          </p>
          <p>
            Trân trọng cảm ơn Quý Khách hàng!
          </p>

        </div>
      </div>
      <div class="footer"
           style="background-color:#090909;padding:20px;text-align:center;color:#ffffff;line-height:25px">
        <h4 style="Margin:0">NGÂN HÀNG TMCP HÀNG HẢI VIỆT NAM</h4>
        <p>Hội sở: Số 54A Nguyễn Chí Thanh, Quận Đống Đa, Hà Nội<br/>
          Hotline (24/7): 1800 599999 (Miễn phí)<br/>
          <span>
                            <a href="tel:02439445566" style="color:#00AEC7;text-decoration:none">02439445566</a>
                            |
                            <a href="mailto:CSKHCanhan@msb.com.vn"
                               style="color:#00AEC7;text-decoration:none">CSKHCanhan@msb.com.vn</a>
                        </span>

        </p>
        <p>Copyright &copy; 2021 *MSB* <br/> All rights reserved.</p>
      </div>
    </div>
  </div>
</div>
</body>

</html>