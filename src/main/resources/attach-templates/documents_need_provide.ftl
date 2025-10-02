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
<div class="container">
  <header>
    <div style="padding-top:20px">
      <img class="msb-logo" src="${logoPageStaticUrl}" style="width:120px" alt="MSB Logo"/>
    </div>
  </header>

  <div class="content">
    <h1 class="t-primary"
        style="font-weight: 600;font-size: 28px; line-height: 32px; text-transform: uppercase;">
      Danh mục hồ sơ cần cung cấp
    </h1>

      <#if fileConfigCategories?size gt 0>
          <#list fileConfigCategories as fileConfigCategory1>
            <h2 class="pt-10 t-title"><#if fileConfigCategory1.name??>${fileConfigCategory1.name}</#if></h2>
              <#if fileConfigCategory1.fileConfigCategoryList?size gt 0>
                  <#list fileConfigCategory1.fileConfigCategoryList as fileConfigCategory2>
                    <h2 class="t-primary t-title-b pt-10"><#if fileConfigCategory2.name??>${fileConfigCategory2.name}</#if></h2>
                      <#list fileConfigCategory2.fileConfigList as fileConfig>
                        <li><#if fileConfig.name??>${fileConfig.name}</#if></li>
                      </#list>
                  </#list>
              <#else>
                  <#list fileConfigCategory1.fileConfigList as fileConfig>
                    <li><#if fileConfig.name??>${fileConfig.name}</#if></li>
                  </#list>
              </#if>
          </#list>
      </#if>
  </div>
</div>

</body>

</html>