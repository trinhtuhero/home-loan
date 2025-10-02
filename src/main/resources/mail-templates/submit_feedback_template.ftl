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
        <img class="msb-logo" src="${logoStaticUrl}" style="width:120px" alt="MSB Logo"/>
      </div>
      <div class="content" style="background-color:#ffffff;color:#090909">
        <div style="border-radius:5px">
          <img src="${headerStaticUrl}" alt="" style="width:100%"/>
        </div>
        <div class="message" style="padding:30px;text-align:justify;line-height:25px">
          <p style="Margin-Top:0;font-size:20px">
            Kính gửi Quý Khách hàng <#if loanApplication.fullName??>loanApplication.fullName</#if>,
          </p>
          <p>MSB trân trọng cảm ơn Quý Khách hàng đã quan tâm đến Sản phẩm vay mua bất động sản của
            MSB.</p>
          <p>Trong quá trình kiểm tra hồ sơ cung cấp online, Quý Khách hàng cần bổ sung các hồ sơ
            theo hướng dẫn sau:</p>

          <p>
              <#list loanApplicationCommentMap?keys as key>
                - ${strMap[key]}:<br>
                  <#assign comment = "">
                  <#list loanApplicationCommentMap[key] as str>
                      <#if str??>
                          <#assign comment += str + "<br>">
                      </#if>
                  </#list>
                  ${comment}
              </#list>

              <#list uploadFileCommentMap2?keys as key>

              </#list>

          </p>

          <p style="text-align: center; margin: 24px;">
            <a href="#"
               style="color: #FFFFFF; padding: 10px 12px; background-color: #eb6719; border: none; border-radius: 4px;text-decoration:none">
              BỔ SUNG HỒ SƠ NGAY
            </a>
          </p>
          <p>
            Trong quá trình thực hiện, nếu Quý Khách hàng cần hỗ trợ xin vui lòng liên hệ Cán bộ bán
            hàng hoặc qua Contact center cho Khách hàng Cá nhân của MSB: 02439445566
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
