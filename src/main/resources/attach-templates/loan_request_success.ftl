<#setting locale="de_DE">

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
        rel="stylesheet">
  <style type="text/css">
    body {
      font-family: 'Inter', sans-serif;
      font-size: 14px;
    }

    table {
      width: 100%;
      font-size: 10px;
    }

    td {
      padding: 2px 0;
    }

    table tr td:first-child {
      width: 40%;
    }

    ol.f {
      list-style-type: decimal;
      padding: 0px 10px;
    }

    .container-cover {
      max-width: 600px;
      margin: auto;
      height: 950px;
      background-color: #eb6719;
    }

    .msb-logo-cover {
      padding: 40px;
      margin-left: 40px;
    }

    .container {
      max-width: 600px;
      margin: auto;
      padding-bottom: 30px;
    }

    .content {

    }

    .msb-logo {
      padding: 30px 30px 20px 30px;
    }

    .image-banner {
      margin-right: 50px;
    }

    .image-banner img {
      width: 100%;
      border-end-end-radius: 20px;
      border-start-end-radius: 20px
    }

    .image-banner-cover {
      margin-right: 50px;
    }

    .image-banner-cover img {
      width: 100%;
      border-end-end-radius: 20px;
      border-start-end-radius: 20px
    }


    .text-white {
      color: #ffff;
    }

    .t-primary {
      color: #eb6719;
    }

    .t-title {
      font-weight: 600;
      font-size: 14px;
    }

    .t-title-b {
      font-weight: 700;
      font-size: 13px;
    }

    .t-span {
      font-weight: 400;
      color: #353945;
    }

    .t-bold {
      font-weight: bold;
    }

    .pt-10 {
      padding-top: 10px;
    }

    .table-result {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #E6E8EC;
    }

    .table-result td {
      border: 1px solid #E6E8EC;
      padding: 8px;
    }

    .text-box {
      box-sizing: border-box;

      display: flex;
      flex-direction: row;
      align-items: flex-start;
      padding: 8px;
      gap: 8px;

      background: #FEF7F3;

      border: 0.5px solid #FCCFB6;
      border-radius: 4px;

      font-family: 'Inter';
      font-style: normal;
      font-weight: 400;
      font-size: 10px;
      line-height: 16px;

      text-align: justify;
      letter-spacing: -0.01em;

      color: #667085;
    }
  </style>
</head>

<body>
<div class="container-cover">
  <header>
    <div style="padding-top:20px;padding-bottom:20px">
      <img class="msb-logo-cover" src="${logoStaticUrl}" style="width:120px" alt="MSB Logo"/>
    </div>
    <div class="image-banner-cover">
      <img src="${bannerStaticUrl}" alt="banner_cover"/>
    </div>
    <div class="text-white" style="padding: 40px;">
      <p style="font-weight: 500;">VAY THẾ CHẤP</p>
      <h1 style="font-weight: 700;font-size: 32px; line-height: 42px;">Vay Nhanh Dễ Dàng, <br/> Tậu
        Nhà Mơ
        Ước</h1>
      <p style="font-weight: 300;color: #FEEFE7;">Giải pháp vay mua nhà đơn giản, nhanh gọn</p>
    </div>
  </header>
</div>
<div class="container">
  <header>
    <div style="padding-top:20px">
      <img class="msb-logo" src="${logoPageStaticUrl}" style="width:120px" alt="MSB Logo"/>
    </div>
  </header>

  <div class="content">
      <#if primaryCards?size gt 0>
        <h3 class="t-primary"
            style="font-weight: 600;font-size: 28px; line-height: 32px; text-transform: uppercase;">
          Đơn đề nghị cấp tín dụng kiêm phát hành và sử dụng thẻ tín dụng
        </h3>
      <#else>
        <h1 class="t-primary"
            style="font-weight: 600;font-size: 28px; line-height: 32px; text-transform: uppercase;">
          Đề nghị vay vốn online
        </h1>
      </#if>
    <section>
      <div>
        <p class="pt-10 t-title">Thông tin khoản vay đề xuất
        <p>
      </div>

        <#if loanApplicationItems?size gt 0>
            <#list loanApplicationItems as loanApplicationItem>
              <div>
                <table>
                    <#if loanApplicationItems?size gt 1>
                      <tr>
                        <p class="t-primary t-title-b pt-10">Khoản
                          vay ${loanApplicationItem?index+1}</p>
                      </tr>
                    </#if>

                  <tr>
                    <td>
                      <span class="t-span">Mục đích vay vốn:</span>
                    </td>
                    <td>
                        <#if loanApplicationItem.loanPurpose??>
                          <span>${loanApplicationItem.loanPurpose.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Giá trị tài sản cần mua:</span>
                    </td>
                    <td>
                        <#if loanApplicationItem.loanAssetValue??>
                          <span>${loanApplicationItem.loanAssetValue} đồng</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số tiền vay:</span>
                    </td>
                    <td>
                        <#if loanApplicationItem.loanAmount??>
                          <span>${loanApplicationItem.loanAmount} đồng</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Thời gian vay (tháng):</span>
                    </td>
                    <td>
                        <#if loanApplicationItem.loanTime??>
                          <span>${loanApplicationItem.loanTime}</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
            </#list>
        </#if>

    </section>

      <#if primaryCards?size gt 0>
        <section>
          <div>
            <p class="pt-10 t-title">Thông tin thẻ tín dụng đề xuất
            <p>
            <p class="t-primary t-title-b pt-10">Thông tin thẻ chính</p>
          </div>

            <#list primaryCards as primaryCard>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Loại thẻ tín dụng:</span>
                    </td>
                    <td>
                        <#if primaryCard.type??>
                          <span>${cardTypeMap[primaryCard.type].name}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Đối tượng cấp thẻ:</span>
                    </td>
                    <td>
                        <#if primaryCard.object??>
                          <span>${primaryCard.object}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Tên in trên thẻ:</span>
                    </td>
                    <td>
                        <#if primaryCard.nameInCard??>
                          <span>${primaryCard.nameInCard}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Hạn mức thẻ:</span>
                    </td>
                    <td>
                        <#if primaryCard.creditLimit??>
                          <span>${primaryCard.creditLimit} đồng</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Trường tiểu học đầu tiên:</span>
                    </td>
                    <td>
                        <#if primaryCard.firstPrimarySchool??>
                          <span>${primaryCard.firstPrimarySchool}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Đăng ký trích nợ tự động:</span>
                    </td>
                    <td>
                        <#if primaryCard.autoDebit!false>
                          <span>Có</span>
                        <#else>
                          <span>Không</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Tỉ lệ trích nợ:</span>
                    </td>
                    <td>
                        <#if primaryCard.debtDeductionRate??>
                          <span>${primaryCard.debtDeductionRate.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số tài khoản trích nợ:</span>
                    </td>
                    <td>
                        <#if primaryCard.debitAccountNumber??>
                          <span>${primaryCard.debitAccountNumber}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Địa chỉ nhận thẻ:</span>
                    </td>
                    <td>
                        <#if primaryCard.receivingAddress??>
                            <#if primaryCard.receivingAddress == "Địa chỉ liên hệ">

                                <#assign addressStr = "">
                                <#if loanApplication.address??>
                                    <#assign addressStr += loanApplication.address>
                                </#if>
                                <#if loanApplication.wardName??>
                                    <#if loanApplication.address?? && loanApplication.address?trim?has_content>
                                        <#assign addressStr += ", " + loanApplication.wardName>
                                    <#else>
                                        <#assign addressStr += loanApplication.wardName>
                                    </#if>
                                </#if>
                                <#if loanApplication.districtName??>
                                    <#assign addressStr += ", " + loanApplication.districtName>
                                </#if>
                                <#if loanApplication.provinceName??>
                                    <#assign addressStr += ", " + loanApplication.provinceName>
                                </#if>
                              <span>${addressStr?html}</span>
                            <#elseif primaryCard.receivingAddress == "Địa chỉ thường trú">
                                <#assign addressStr = "">
                                <#if loanApplication.residenceAddress??>
                                    <#assign addressStr += loanApplication.residenceAddress>
                                </#if>
                                <#if loanApplication.residenceWardName??>
                                    <#if loanApplication.residenceAddress?? && loanApplication.residenceAddress?trim?has_content>
                                        <#assign addressStr += ", " + loanApplication.residenceWardName>
                                    <#else>
                                        <#assign addressStr += loanApplication.residenceWardName>
                                    </#if>
                                </#if>
                                <#if loanApplication.residenceDistrictName??>
                                    <#assign addressStr += ", " + loanApplication.residenceDistrictName>
                                </#if>
                                <#if loanApplication.residenceProvinceName??>
                                    <#assign addressStr += ", " + loanApplication.residenceProvinceName>
                                </#if>
                              <span>${addressStr?html}</span>
                            <#else>
                              <span>${primaryCard.receivingAddress}</span>
                            </#if>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
                <#if primaryCards?size gt 1 && primaryCard?index lt primaryCards?size - 1>
                  <div
                      style="width: 100%; height: 5px; margin: 10px 0px; border-bottom: 1px solid #dddddd;"></div>
                </#if>
            </#list>
          <div class="text-box">
            Phí thường niên được thể hiện trên kỳ sao kê tháng đầu tiên ngay sau ngày phát hành thẻ
            tín dụng. Trong trường hợp Quý khách không được cấp hạng thẻ / hạn mức như yêu cầu, Ngân
            hàng sẽ cấp cho Quý khách một hạng thẻ / hạn mức khác nếu Quý khách đủ tiêu chuẩn.
          </div>
        </section>
      </#if>

      <#if primaryCards?size gt 0 && secondaryCards?size gt 0>
        <section>
          <div>
            <p class="t-primary t-title-b pt-10">Thông tin thẻ phụ</p>
          </div>
            <#list secondaryCards as secondaryCard>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Đối tượng cấp thẻ:</span>
                    </td>
                    <td>
                        <#if secondaryCard.object??>
                          <span>${secondaryCard.object}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Tên in trên thẻ:</span>
                    </td>
                    <td>
                        <#if secondaryCard.nameInCard??>
                          <span>${secondaryCard.nameInCard}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Hạn mức thẻ:</span>
                    </td>
                    <td>
                        <#if secondaryCard.creditLimit??>
                          <span>${secondaryCard.creditLimit} đồng</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
                <#if secondaryCards?size gt 1 && secondaryCard?index lt secondaryCards?size - 1>
                  <div
                      style="width: 100%; height: 5px; margin: 10px 0px; border-bottom: 1px solid #dddddd;"></div>
                </#if>
            </#list>
          <div class="text-box">
            Tôi đồng ý và đề nghị MSB phát hành thẻ tín dụng phụ (theo điều kiện và điều khoản sử
            dụng thẻ tín dụng của MSB) cho cá nhân được nêu bên trên, phí thường niên theo biểu phí
            hiện hành. Mọi giao dịch do (các) thẻ phụ này thực hiện sẽ được liệt kê và tính chung
            trên cùng bảng sao kê giao dịch thẻ tín dụng của tôi. Đồng thời, thẻ phụ sẽ được gửi đến
            địa chỉ mà tôi đã đăng ký.
          </div>
        </section>
      </#if>

      <#if overdraft??>
        <section>
          <div>
            <p class="pt-10 t-title">Thông tin thấu chi
            <p>
          </div>

          <div>
            <table>
              <tr>
                <td>
                  <span class="t-span">Mục đích vay:</span>
                </td>
                <td>
                    <#if overdraft.overdraftPurpose??>
                      <span>${overdraft.overdraftPurpose.getName()}</span>
                    </#if>
                </td>
              </tr>
              <tr>
                <td>
                  <span class="t-span">Hạn mức:</span>
                </td>
                <td>
                    <#if overdraft.loanAmount??>
                      <span>${overdraft.loanAmount} đồng</span>
                    </#if>
                </td>
              </tr>
              <tr>
                <td>
                  <span class="t-span">Đối tượng cấp:</span>
                </td>
                <td>
                    <#if overdraft.overdraftSubject??>
                      <span>${overdraft.overdraftSubject.getName()}</span>
                    </#if>
                </td>
              </tr>
            </table>
          </div>
        </section>
      </#if>

    <section>
      <div>
        <p class="pt-10 t-title">Thông tin khách hàng và những người liên quan
        <p>
        <p class="t-primary t-title-b pt-10">Khách hàng</p>
      </div>
      <div>
        <table>
          <tr>
            <td>
              <span class="t-span">Họ và tên:</span>
            </td>
            <td>
                <#if loanApplication.fullName??>
                  <span>${loanApplication.fullName?html}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Ngày sinh:</span>
            </td>
            <td>
                <#if loanApplication.birthday??>
                  <span>${loanApplication.birthday?datetime?string("dd/MM/yyyy")}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Giới tính:</span>
            </td>
            <td>
                <#if loanApplication.gender??>
                  <span>${loanApplication.gender.getName()}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Quốc tịch:</span>
            </td>
            <td>
                <#if loanApplication.nationality??>
                  <span>${nationalityMap[loanApplication.nationality]}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Số CMND/CCCD hiệu lực:</span>
            </td>
            <td>
                <#if loanApplication.idNo??>
                  <span>${loanApplication.idNo}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Ngày cấp:</span>
            </td>
            <td>
                <#if loanApplication.issuedOn??>
                  <span>${loanApplication.issuedOn?datetime?string("dd/MM/yyyy")}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Nơi cấp:</span>
            </td>
            <td>
                <#if loanApplication.placeOfIssueName??>
                  <span>${loanApplication.placeOfIssueName}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Số giấy tờ cũ (nếu có):</span>
            </td>
            <td>
                <#if loanApplication.oldIdNo??>
                  <span>${loanApplication.oldIdNo}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Số điện thoại:</span>
            </td>
            <td>
                <#if loanApplication.phone??>
                  <span>${loanApplication.phone}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Email:</span>
            </td>
            <td>
                <#if loanApplication.email??>
                  <span>${loanApplication.email}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Tình trạng hôn nhân:</span>
            </td>
            <td>
                <#if loanApplication.maritalStatus??>
                  <span>${loanApplication.maritalStatus.getName()}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Địa chỉ sinh sống hiện tại:</span>
            </td>
            <td>
                <#if loanApplication.address??>
                  <span>${loanApplication.address?html}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Phường/xã:</span>
            </td>
            <td>
                <#if loanApplication.wardName??>
                  <span>${loanApplication.wardName?html}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Quận/Huyện:</span>
            </td>
            <td>
                <#if loanApplication.districtName??>
                  <span>${loanApplication.districtName?html}</span>
                </#if>
            </td>
          </tr>
          <tr>
            <td>
              <span class="t-span">Tỉnh/Thành phố:</span>
            </td>
            <td>
                <#if loanApplication.provinceName??>
                  <span>${loanApplication.provinceName?html}</span>
                </#if>
            </td>
          </tr>
        </table>
      </div>
    </section>

      <#if marriedPersonList?size gt 0>
        <section>
          <div>
            <p class="t-primary t-title-b pt-10">Vợ/chồng khách hàng</p>
          </div>
            <#list marriedPersonList as marriedPerson>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Họ và tên:</span>
                    </td>
                    <td>
                        <#if marriedPerson.fullName??>
                          <span>${marriedPerson.fullName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Ngày sinh:</span>
                    </td>
                    <td>
                        <#if marriedPerson.birthday??>
                          <span>${marriedPerson.birthday?datetime?string("dd/MM/yyyy")}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Giới tính:</span>
                    </td>
                    <td>
                        <#if marriedPerson.gender??>
                          <span>${marriedPerson.gender.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Quốc tịch:</span>
                    </td>
                    <td>
                        <#if marriedPerson.nationality??>
                          <span>${nationalityMap[marriedPerson.nationality]}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số CMND/CCCD hiệu lực:</span>
                    </td>
                    <td>
                        <#if marriedPerson.idNo??>
                          <span>${marriedPerson.idNo}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Ngày cấp:</span>
                    </td>
                    <td>
                        <#if marriedPerson.issuedOn??>
                          <span>${marriedPerson.issuedOn?datetime?string("dd/MM/yyyy")}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Nơi cấp:</span>
                    </td>
                    <td>
                        <#if marriedPerson.placeOfIssueName??>
                          <span>${marriedPerson.placeOfIssueName}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số giấy tờ cũ (nếu có):</span>
                    </td>
                    <td>
                        <#if marriedPerson.oldIdNo??>
                          <span>${marriedPerson.oldIdNo}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số điện thoại:</span>
                    </td>
                    <td>
                        <#if marriedPerson.phone??>
                          <span>${marriedPerson.phone}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Địa chỉ sinh sống hiện tại:</span>
                    </td>
                    <td>
                        <#if marriedPerson.address??>
                          <span>${marriedPerson.address?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Phường/xã:</span>
                    </td>
                    <td>
                        <#if marriedPerson.wardName??>
                          <span>${marriedPerson.wardName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Quận/Huyện:</span>
                    </td>
                    <td>
                        <#if marriedPerson.districtName??>
                          <span>${marriedPerson.districtName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Tỉnh/Thành phố:</span>
                    </td>
                    <td>
                        <#if marriedPerson.provinceName??>
                          <span>${marriedPerson.provinceName?html}</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
            </#list>
        </section>
      </#if>

      <#if loanPayerList?size gt 0>
        <section>
          <div>
            <p class="t-primary t-title-b pt-10">Người đồng trả nợ khoản vay đang đề xuất (khác
              Vợ/Chồng)
            </p>
          </div>
            <#list loanPayerList as loanPayer>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Mối quan hệ:</span>
                    </td>
                    <td>
                        <#if loanPayer.relationshipType??>
                          <span>${loanPayer.relationshipType.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Họ và tên:</span>
                    </td>
                    <td>
                        <#if loanPayer.fullName??>
                          <span>${loanPayer.fullName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Ngày sinh:</span>
                    </td>
                    <td>
                        <#if loanPayer.birthday??>
                          <span>${loanPayer.birthday?datetime?string("dd/MM/yyyy")}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Giới tính:</span>
                    </td>
                    <td>
                        <#if loanPayer.gender??>
                          <span>${loanPayer.gender.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Quốc tịch:</span>
                    </td>
                    <td>
                        <#if loanPayer.nationality??>
                          <span>${nationalityMap[loanPayer.nationality]}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số CMND/CCCD hiệu lực:</span>
                    </td>
                    <td>
                        <#if loanPayer.idNo??>
                          <span>${loanPayer.idNo}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Ngày cấp:</span>
                    </td>
                    <td>
                        <#if loanPayer.issuedOn??>
                          <span>${loanPayer.issuedOn?datetime?string("dd/MM/yyyy")}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Nơi cấp:</span>
                    </td>
                    <td>
                        <#if loanPayer.placeOfIssueName??>
                          <span>${loanPayer.placeOfIssueName}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số giấy tờ cũ (nếu có):</span>
                    </td>
                    <td>
                        <#if loanPayer.oldIdNo??>
                          <span>${loanPayer.oldIdNo}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số điện thoại:</span>
                    </td>
                    <td>
                        <#if loanPayer.phone??>
                          <span>${loanPayer.phone}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Tình trạng hôn nhân:</span>
                    </td>
                    <td>
                        <#if loanPayer.maritalStatus??>
                          <span>${loanPayer.maritalStatus.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Địa chỉ sinh sống hiện tại:</span>
                    </td>
                    <td>
                        <#if loanPayer.address??>
                          <span>${loanPayer.address?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Phường/xã:</span>
                    </td>
                    <td>
                        <#if loanPayer.wardName??>
                          <span>${loanPayer.wardName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Quận/Huyện:</span>
                    </td>
                    <td>
                        <#if loanPayer.districtName??>
                          <span>${loanPayer.districtName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Tỉnh/Thành phố:</span>
                    </td>
                    <td>
                        <#if loanPayer.provinceName??>
                          <span>${loanPayer.provinceName?html}</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
                <#if loanPayerList?size gt 1 && loanPayer?index lt loanPayerList?size - 1>
                  <div
                      style="width: 100%; height: 5px; margin: 10px 0px; border-bottom: 1px solid #dddddd;"></div>
                </#if>
            </#list>
        </section>
      </#if>

      <#if contactPersonList?size gt 0>
        <section>
          <div>
            <p class="t-primary t-title-b pt-10">Người liên hệ</p>
          </div>
            <#list contactPersonList as contactPerson>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Mối quan hệ:</span>
                    </td>
                    <td>
                        <#if contactPerson.type??>
                          <span>${contactPerson.type.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Họ và tên:</span>
                    </td>
                    <td>
                        <#if contactPerson.fullName??>
                          <span>${contactPerson.fullName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số điện thoại:</span>
                    </td>
                    <td>
                        <#if contactPerson.phone??>
                          <span>${contactPerson.phone}</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
            </#list>
        </section>
      </#if>

    <section>
      <div>
        <p class="pt-10 t-title">Thông tin nguồn thu
        <p>
            <#if salaryIncomeList?size gt 0>
        <p class="t-primary t-title-b pt-10">Nguồn thu từ lương</p>
          </#if>
      </div>
        <#if salaryIncomeList?size gt 0>
            <#list salaryIncomeList as salaryIncome>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Chủ nguồn thu nhập:</span>
                    </td>
                    <td>
                        <#assign payerStr = "">

                        <#if salaryIncome.ownerType??>
                            <#assign payerStr += salaryIncome.ownerType.getName()>
                        </#if>
                        <#if salaryIncome.payerName??>
                            <#assign payerStr += " - " + salaryIncome.payerName>
                        </#if>

                      <span>${payerStr?html}</span>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Cơ quan công tác:</span>
                    </td>
                    <td>
                        <#if salaryIncome.officeName??>
                          <span>${salaryIncome.officeName?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Địa chỉ công ty:</span>
                    </td>
                    <td>
                        <#assign addressStr = "">
                        <#if salaryIncome.officeAddress??>
                            <#assign addressStr += salaryIncome.officeAddress>
                        </#if>
                        <#if salaryIncome.officeWardName??>
                            <#if salaryIncome.officeAddress?? && salaryIncome.officeAddress?trim?has_content>
                                <#assign addressStr += ", " + salaryIncome.officeWardName>
                            <#else>
                                <#assign addressStr += salaryIncome.officeWardName>
                            </#if>
                        </#if>
                        <#if salaryIncome.officeDistrictName??>
                            <#assign addressStr += ", " + salaryIncome.officeDistrictName>
                        </#if>
                        <#if salaryIncome.officeProvinceName??>
                            <#assign addressStr += ", " + salaryIncome.officeProvinceName>
                        </#if>
                      <span>${addressStr?html}</span>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Số điện thoại cơ quan:</span>
                    </td>
                    <td>
                        <#if salaryIncome.officePhone??>
                          <span>${salaryIncome.officePhone}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Chức vụ hiện tại:</span>
                    </td>
                    <td>
                        <#if salaryIncome.officeTitle??>
                          <span>${salaryIncome.officeTitle?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Hình thức trả lương:</span>
                    </td>
                    <td>
                        <#if salaryIncome.paymentMethod??>
                          <span>${salaryIncome.paymentMethod.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Giá trị thu nhập/ tháng:</span>
                    </td>
                    <td>
                        <#if salaryIncome.value??>
                          <span>${salaryIncome.value} đồng</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
                <#if salaryIncomeList?size gt 1 && salaryIncome?index lt salaryIncomeList?size - 1>
                  <div
                      style="width: 100%; height: 5px; margin: 10px 0px; border-bottom: 1px solid #dddddd;"></div>
                </#if>
            </#list>
        </#if>
    </section>

      <#if businessIncomeList?size gt 0>
        <section>
          <div>
            <p class="t-primary t-title-b pt-10">Nguồn thu từ Hộ kinh doanh/Doanh nghiệp</p>
          </div>
            <#list businessIncomeList as businessIncome>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Chủ nguồn thu nhập:</span>
                    </td>
                    <td>
                        <#assign payerStr = "">

                        <#if businessIncome.ownerType??>
                            <#assign payerStr += businessIncome.ownerType.getName()>
                        </#if>
                        <#if businessIncome.payerName??>
                            <#assign payerStr += " - " + businessIncome.payerName>
                        </#if>

                      <span>${payerStr?html}</span>
                    </td>
                  </tr>
                  <tr>
                    <td>
                                <span class="t-span">Tên cơ sở kinh doanh,
                                    doanh nghiệp:</span>
                    </td>
                    <td>
                        <#if businessIncome.name??>
                          <span>${businessIncome.name?html}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Địa chỉ:</span>
                    </td>
                    <td>
                        <#assign addressStr = "">
                        <#if businessIncome.address??>
                            <#assign addressStr += businessIncome.address>
                        </#if>
                        <#if businessIncome.wardName??>
                            <#if businessIncome.address?? && businessIncome.address?trim?has_content>
                                <#assign addressStr += ", " + businessIncome.wardName>
                            <#else>
                                <#assign addressStr += businessIncome.wardName>
                            </#if>
                        </#if>
                        <#if businessIncome.districtName??>
                            <#assign addressStr += ", " + businessIncome.districtName>
                        </#if>
                        <#if businessIncome.provinceName??>
                            <#assign addressStr += ", " + businessIncome.provinceName>
                        </#if>
                      <span>${addressStr?html}</span>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Điện thoại:</span>
                    </td>
                    <td>
                        <#if businessIncome.phone??>
                          <span>${businessIncome.phone}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Ngành nghề kinh doanh:</span>
                    </td>
                    <td>
                        <#if businessIncome.businessLine??>
                          <span>${businessIncome.businessLine.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Hình thức kinh doanh:</span>
                    </td>
                    <td>
                        <#if businessIncome.businessType??>
                          <span>${businessIncome.businessType.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Giá trị thu nhập/ tháng:</span>
                    </td>
                    <td>
                        <#if businessIncome.value??>
                          <span>${businessIncome.value} đồng</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
                <#if businessIncomeList?size gt 1 && businessIncome?index lt businessIncomeList?size - 1>
                  <div
                      style="width: 100%; height: 5px; margin: 10px 0px; border-bottom: 1px solid #dddddd;"></div>
                </#if>
            </#list>
        </section>
      </#if>

      <#if otherIncomeList?size gt 0>
        <section>
          <div>
            <p class="t-primary t-title-b pt-10">Nguồn thu khác</p>
          </div>
            <#list otherIncomeList as otherIncome>
              <div>
                <table>
                  <tr>
                    <td>
                      <span class="t-span">Chủ nguồn thu nhập:</span>
                    </td>
                    <td>
                        <#assign payerStr = "">

                        <#if otherIncome.ownerType??>
                            <#assign payerStr += otherIncome.ownerType.getName()>
                        </#if>
                        <#if otherIncome.payerName??>
                            <#assign payerStr += " - " + otherIncome.payerName>
                        </#if>

                      <span>${payerStr?html}</span>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Nguồn thu đến từ:</span>
                    </td>
                    <td>
                        <#if otherIncome.incomeFrom??>
                          <span>${otherIncome.incomeFrom.getName()}</span>
                        </#if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <span class="t-span">Giá trị thu nhập/ tháng:</span>
                    </td>
                    <td>
                        <#if otherIncome.value??>
                          <span>${otherIncome.value} đồng</span>
                        </#if>
                    </td>
                  </tr>
                </table>
              </div>
                <#if otherIncomeList?size gt 1 && otherIncome?index lt otherIncomeList?size - 1>
                  <div
                      style="width: 100%; height: 5px; margin: 10px 0px; border-bottom: 1px solid #dddddd;"></div>
                </#if>
            </#list>
        </section>
      </#if>

  </div>
  <div style="margin: 40px 0px ;">
    <div style="height: 100px; padding: 15px 24px; background-color: #eb6719;">
      <p class="text-white">Tổng thu nhập của khách hàng</p>
      <h2 class="text-white">${totalIncome} đồng/tháng</h2>
    </div>

      <#if collateralList?size gt 0>
        <div>
          <p class="t-title pt-10">Tài sản đảm bảo</p>
          <table class="table-result">
            <tbody>
            <tr style="color: #777E91;">
              <td style="width: 10%;">STT</td>
              <td>Loại tài sản</td>
              <td>Tình trạng sở hữu tài sản</td>
              <td>Địa chỉ/ Mô tả tài sản</td>
            </tr>
            <#list collateralList as collateral>
              <tr>
                <td style="width: 10%;">${collateral?counter}</td>

                <td>
                    <#if collateral.type??>
                        ${collateral.type.getName()}
                    </#if>
                </td>

                <td>
                    <#if collateral.status??>
                        ${collateral.status.getName()}
                    </#if>
                </td>

                  <#assign addressStr = "">
                  <#if collateral.address??>
                      <#assign addressStr += collateral.address>
                  </#if>
                  <#if collateral.wardName??>
                      <#if collateral.address?? && collateral.address?trim?has_content>
                          <#assign addressStr += ", " + collateral.wardName>
                      <#else>
                          <#assign addressStr += collateral.wardName>
                      </#if>
                  </#if>
                  <#if collateral.districtName??>
                      <#assign addressStr += ", " + collateral.districtName>
                  </#if>
                  <#if collateral.provinceName??>
                      <#assign addressStr += ", " + collateral.provinceName>
                  </#if>
                <td>
                    <#if collateral.description??>
                        ${collateral.description?html}
                    <#else>
                        ${addressStr?html}
                    </#if>
                </td>
              </tr>
            </#list>
            </tbody>
          </table>
        </div>
      </#if>

  </div>
  <div class="content" style="font-size: 10px;">
    <div style="display: flex; align-items: center;">
                <span>
                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none"
                         xmlns="http://www.w3.org/2000/svg">
                        <rect x="2.22218" y="2.22224" width="15.5556" height="15.5556" rx="2.22222"
                              fill="#F4600C"
                              stroke="#F4600C" stroke-width="1.11111"/>
                        <path fill-rule="evenodd" clip-rule="evenodd"
                              d="M14.036 7.15335C14.2103 7.32607 14.2103 7.6061 14.036 7.77883L8.92062 12.8466C8.74628 13.0194 8.46362 13.0194 8.28928 12.8466L5.96413 10.5431C5.78979 10.3704 5.78979 10.0903 5.96413 9.91761C6.13847 9.74489 6.42113 9.74489 6.59548 9.91761L8.60495 11.9084L13.4046 7.15335C13.5789 6.98062 13.8616 6.98062 14.036 7.15335Z"
                              fill="white"/>
                    </svg>
                </span>
      <span style="font-weight: 600; font-size: 10px; margin-left: 6px;">Tôi cam kết:</span>
    </div>

      <#if primaryCards?size gt 0>
        <div style="padding: 10px 0px;">
          <ol class="f">
            <li>
              <p style="padding-left: 15px;">
                Những thông tin, số liệu kê khai trên là hoàn toàn đầy đủ, đúng sự thật và tôi hoàn
                toàn chịu trách nhiệm về các thông tin trên.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi đồng ý và cho phép MSB sử dụng bất kỳ thông tin nào trên đây để trao đổi, xác
                minh những thông tin liên quan đến khoản vay và thẻ tín dụng của tôi từ/cho bất kỳ
                bên thứ 3 nào khác theo các quy định của MSB.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi đồng ý rằng MSB có quyền sử dụng các thông tin trên để cung cấp, giới thiệu, hỗ
                trợ cho tôi về các thông tin liên quan đến sản phẩm, dịch vụ của MSB.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Sử dụng vốn vay đúng mục đích, thanh toán các khoản nợ và chi phí có liên quan đầy
                đủ, đúng hạn.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi có trách nhiệm cung cấp hồ sơ, tài liệu bản cứng đầy đủ, đúng hình thức (bản
                gốc/bản sao) và khớp đúng với file đã gửi qua Landing Page khi MSB hoặc cơ quan có
                thẩm quyền yêu cầu.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi có trách nhiệm và cam kết đã đạt được chấp thuận từ vợ/chồng tôi và người bảo
                lãnh trả nợ (khác vợ/chồng tôi) với các nội dung kê khai trên Đơn đề nghị cấp tín
                dụng kiêm đăng ký phát hành và sử dụng thẻ tín dụng này. Tại đây, tôi đồng ý miễn
                trừ hoàn toàn mọi trách nhiệm, rủi ro, tổn thất cho MSB, cam kết không có bất kỳ
                khiếu kiện nào đối với MSB liên quan đến việc sử dụng thông tin kê khai nêu trên để
                thực hiện giao dịch theo đề nghị của tôi.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi đã đọc, được giải thích chi tiết và hiểu rõ, chấp nhận và tuân thủ với bản Điều
                khoản điều kiện giao dịch chung về việc phát hành và sử dụng thẻ tín dụng được niêm
                yết trên website tại đường link: <a href="http://www.msb.com.vn">www.msb.com.vn</a>.
                Ngoài ra, tôi được tư vấn đầy đủ về phí, lãi suất, chính sách ưu đãi phí theo biểu
                phí thẻ tín dụng hiện hành, và các quy định liên quan đến việc phát hành và sử dụng
                thẻ tín dụng quốc tế của MSB. Đồng ý để MSB chủ động lựa chọn một hoặc đồng thời tất
                cả các hình thức gửi thông báo như: gửi email, gọi điện trực tiếp, gửi tin nhắn di
                dộng khi có các thay đổi, điều chỉnh trong quá trình sử dụng thẻ tín dụng … Việc tôi
                tiếp tục thực hiện các giao dịch sử dụng thẻ tín dụng được xem như là đã đồng ý với
                các thay đổi, điều chỉnh này.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Đơn đề nghị cấp tín dụng kiêm đăng ký phát hành và sử dụng thẻ tín dụng này cùng với
                (i) bản Điều khoản điều kiện giao dịch chung về việc phát hành và sử dụng thẻ tín
                dụng, (ii) thông báo hạn mức thẻ tín dụng MSB gửi tôi và (iii) các thỏa thuận khác
                giữa MSB với tôi (nếu có), tạo thành một thỏa thuận thống nhất, không tách rời, có
                giá trị pháp lý như một bản hợp đồng phát hành và sử dụng thẻ tín dụng hoàn chỉnh.
                Tôi chấp nhận trả phí thường niên cho thẻ tín dụng sau khi ký vào Đơn đề nghị này.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tuân thủ các quy định khác của MSB liên quan đến khoản vay và thẻ tín dụng của tôi.
                Nếu không thực hiện đúng và đầy đủ các hợp đồng, văn bản đã ký/xác nhận qua Landing
                Page của MSB, tôi xin hoàn toàn chịu trách nhiệm trước pháp luật.
              </p>
            </li>
          </ol>
        </div>
      <#else>
        <div style="padding: 10px 0px;">
          <ol class="f">
            <li>
              <p style="padding-left: 15px;">
                Những thông tin, số liệu kê khai trên là hoàn toàn đúng sự thật và tôi hoàn toàn
                chịu trách nhiệm về các thông tin trên.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi đồng ý và cho phép MSB sử dụng bất kỳ thông tin nào trên đây để chia sẻ, trao
                đổi những thông tin liên quan đến tôi và khoản vay của tôi từ/cho bất kỳ bên thứ ba
                nào khác theo các quy định của MSB.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Ngoài ra tôi cũng đồng ý rằng MSB có quyền sử dụng thông tin trên để cung cấp, giới
                thiệu, hỗ trợ cho tôi về các thông tin liên quan đến sản phẩm, dịch vụ của MSB.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Sử dụng vốn đúng mục đích vay, thanh toán các khoản nợ và chi phí có liên quan đầy
                đủ, đúng hạn.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi có trách nhiệm cung cấp hồ sơ, tài liệu bản cứng đầy đủ, đúng hình thức (bản
                gốc/bản sao) và khớp đúng với file đã gửi qua Landing Page khi MSB hoặc cơ quan có
                thẩm quyền yêu cầu.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tôi có trách nhiệm và cam kết đã đạt được chấp thuận từ vợ/chồng tôi
                và người bảo lãnh trả nợ (khác vợ/chồng tôi) với các nội dung kê khai
                trên Đơn đề nghị cấp tín dụng này. Tại đây, tôi đồng ý miễn trừ hoàn toàn
                mọi trách nhiệm, rủi ro, tổn thất cho MSB, cam kết không có bất kỳ khiếu kiện nào
                đối với MSB liên quan đến việc sử dụng thông tin kê khai nêu trên để thực
                hiện giao dịch theo đề nghị của tôi.
              </p>
            </li>
            <li>
              <p style="padding-left: 15px;">
                Tuân thủ các quy định khác của MSB liên quan đến khoản vay của tôi. Nếu không thực
                hiện đúng và đầy đủ các hợp đồng, văn bản đã ký/xác nhận qua Landing Page với MSB,
                tôi xin hoàn toàn chịu trách nhiệm trước pháp luật.
              </p>
            </li>
          </ol>
        </div>
      </#if>

    <div
        style="padding: 20px 0px; padding-bottom: 10px; float: right; width: 300px; text-align: center;">
      <div style="width: 300px">
        Ngày <span>${dateElements[3]}</span> tháng <span>${dateElements[4]}</span> năm
        <span>${dateElements[5]}</span>
      </div>
      <p>KHÁCH HÀNG</p>
      <p style="margin-top: 70px;">
          <#if loanApplication.fullName??>${loanApplication.fullName?html}</#if>
      </p>

      <div style="width: 100%; text-align: center;">
        <p>Đã xác nhận <#if (count > 0)>lần ${count}</#if> vào lúc <span>${dateElements[0]}</span>
          giờ <span>${dateElements[1]}</span> phút <span>${dateElements[2]}</span> giây ngày
          <span>${dateElements[3]}</span> tháng <span>${dateElements[4]}</span> năm
          <span>${dateElements[5]}</span> trên hệ thống <a
              href="#"><#if linkLdp??>${linkLdp}</#if></a></p>
      </div>
    </div>

  </div>
</div>

</body>

</html>