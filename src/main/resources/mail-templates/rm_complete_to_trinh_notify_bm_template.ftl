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
              <#if nameBM??>${nameBM}</#if> thân mến,
          </p>
          <p>Nhân viên <#if nameRM??>${nameRM}</#if> vừa hoàn thành xong việc soạn thảo tờ
            trình cấp tín dụng của Khách hàng sau: </p>
          <ul>
            <li>Mã hồ sơ: <#if loanApplication.loanCode??>${loanApplication.loanCode}</#if></li>
            <li>Tên Khách
              hàng: <#if loanApplication.fullName??>${loanApplication.fullName?html}</#if></li>
            <li>Số điện thoại: <#if loanApplication.phone??>${loanApplication.phone}</#if></li>
            <li>Thông tin khoản vay: </p>

              <ul>
                  <#if loanApplicationItems?size gt 0>
                      <#list loanApplicationItems as loanApplicationItem>
                          <#if loanApplicationItem??>
                            <li>Khoản vay ${loanApplicationItem?index+1}:</li>
                            <ul>
                              <li>Số tiền
                                vay: <#if loanApplicationItem.loanAmount??>${loanApplicationItem.loanAmount} đồng</#if></li>
                              <li>Thời hạn
                                vay: <#if loanApplicationItem.loanTime??>${loanApplicationItem.loanTime} tháng</#if></li>
                            </ul>
                          </#if>
                      </#list>
                  </#if>
              </ul>

              <ul>
                  <#if primaryCard??>
                    <section>
                      <li>Thẻ chính:</li>
                      <ul>
                        <li>Hạn mức
                          thẻ: <#if primaryCard.creditLimit??>${primaryCard.creditLimit} đồng</#if></li>
                      </ul>
                    </section>
                  </#if>
              </ul>

              <ul>
                  <#if secondaryCards?? && secondaryCards?size gt 0>
                      <#list secondaryCards as secondaryCard>
                          <#if secondaryCard??>
                            <li>Thẻ phụ ${secondaryCard?index+1}:</li>
                            <ul>
                              <li>Hạn mức
                                thẻ: <#if secondaryCard.creditLimit??>${secondaryCard.creditLimit} đồng</#if></li>
                            </ul>
                          </#if>
                      </#list>
                  </#if>
              </ul>
          </ul>

          <p>Anh/Chị truy cập vào link sau để xem chi tiết tờ trình và hồ sơ của Khách
            hàng.</p>
          <p style="text-align: center; margin: 24px;">
            <a href="<#if chiTietVayUrl??>${chiTietVayUrl}</#if>"
               style="color: #FFFFFF; padding: 10px 12px; background-color: #eb6719; border: none; border-radius: 4px;text-decoration:none">
              Tại đây.
            </a>
          </p>
          <p>
            Trân trọng cảm ơn Anh/chị.
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
