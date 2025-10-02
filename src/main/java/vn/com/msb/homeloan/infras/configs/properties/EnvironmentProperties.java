package vn.com.msb.homeloan.infras.configs.properties;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class EnvironmentProperties {

  @Value("${info.application.name}")
  private String applicationName;

  @Value("${info.application.version}")
  private String applicationVersion;

  @Value("${msb.common.client.code}")
  String clientCode;

  @Value("${msb.common.client.scopes}")
  String clientScopes;

  @Value("${msb.hash.secret}")
  private String hashSecret;

  @Value("${feign.hris.auth.user-name}")
  private String hrisUserName;

  @Value("${feign.hris.auth.password}")
  private String hrisPassword;

  @Value("${msb.hash.expired-time: 5}")
  private Integer hashExpiredTime;

  @Value("${msb.jwt.expiration-paymentsuccess}")
  private Integer timeExpirePaymentSuccess;

  @Value("${msb.jwt.expiration}")
  private Integer timeExpireToken;

  @Value("${msb.province-special}")
  private String provinceSpecial;

  @Value("${msb.loan-upload-file-allow}")
  private String fileAllow;

  @Value("${msb.loan-upload-file-mvalue-allow}")
  private String fileMValueAllow;

  @Value("${msb.loan-upload-file-max-size}")
  private int fileUploadMaxSize;

  @Value("${msb.loan-upload-file-max-item}")
  private int fileUploadMaxItem;

  @Value("${msb.loan-upload-limit-maximum-choose}")
  private int limitMaximumChoose;

  @Value("${msb.common.authen.public-key}")
  private String publicKey;

  @Value("${upload.loan.zip.job.select.top}")
  private Integer uploadLoanZipJobSelectTop;

  @Value("${msb.sha256.secret}")
  private String sha256Secret;

  @Value("${msb.loan-applications-status-allow-close}")
  private List<String> loanStatusAllowClose;

  @Value("${msb.resources.static.header}")
  private String headerStaticUrl;

  @Value("${msb.resources.static.apple-store}")
  private String appleStoreStaticUrl;

  @Value("${msb.resources.static.google-play}")
  private String googlePlayStaticUrl;

  @Value("${msb.resources.static.banner}")
  private String bannerStaticUrl;

  @Value("${msb.resources.static.logo}")
  private String logoStaticUrl;

  @Value("${msb.resources.static.logo-page}")
  private String logoPageStaticUrl;

  @Value("${msb.cj5.ldp.url}")
  private String cj5LdpUrl;

  @Value("${msb.cj5.ldp.danh-sach-vay}")
  private String danhSachVayPath;

  @Value("${msb.cj5.ldp.chi-tiet-vay}")
  private String chiTietVayPath;

  @Value("${msb.cj5.cms.url}")
  private String cj5CmsUrl;

  @Value("${msb.cj5.cms.loan-detail}")
  private String cmsLoanDetailPath;

  @Value("${msb.cj5.email-template.number-of-working-days}")
  private String numberOfWorkingDays;

  @Value("${msb.cj5.customer-support-email}")
  private String customerSupportEmail;

  @Value("${msb.deal-application-link}")
  private String dealApplicationLink;

  @Value("${msb.common.cms.role.admin}")
  private String cj5Admin;

  @Value("${feign.cj4.secret}")
  private String cj4Secret;

  @Value("${feign.cj4.channel}")
  private String cj4Channel;
}
