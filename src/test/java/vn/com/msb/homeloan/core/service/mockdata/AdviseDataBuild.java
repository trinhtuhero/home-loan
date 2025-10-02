package vn.com.msb.homeloan.core.service.mockdata;

import java.util.Date;
import vn.com.msb.homeloan.core.constant.AdviseStatusEnum;
import vn.com.msb.homeloan.core.constant.AdviseTimeFrameEnum;
import vn.com.msb.homeloan.core.constant.ContactStatusEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.entity.AdviseCustomerEntity;
import vn.com.msb.homeloan.core.entity.LoanAdviseCustomerEntity;
import vn.com.msb.homeloan.core.entity.ProvinceEntity;
import vn.com.msb.homeloan.core.model.AdviseCustomer;
import vn.com.msb.homeloan.core.model.MtkTracking;

public class AdviseDataBuild {

  public static AdviseCustomer buildCustomerRegistryDTO() {
    AdviseCustomer dto = new AdviseCustomer();
    dto.setCustomerName("DQKhanh");
    dto.setCustomerPhone("0968441766");
    dto.setProduct(LoanPurposeEnum.LAND.getCode());
    dto.setProvince("01");

    return dto;
  }

  public static AdviseCustomerEntity buildAdviseCustomerEntity() {
    AdviseCustomerEntity entity = new AdviseCustomerEntity();
    entity.setProvince("Thành phố Hà Nội");
    entity.setProduct(LoanPurposeEnum.LAND.getCode());
    entity.setCustomerName("DQKhanh");
    entity.setCustomerPhone("0968441766");
    entity.setStatus(AdviseStatusEnum.OPEN);

    return entity;
  }

  public static AdviseCustomerEntity buildAdviseCustomerEntitySaved() {
    AdviseCustomerEntity entity = new AdviseCustomerEntity();
    entity.setUuid("db9eec32-351c-47c7-a4f0-6cb56f97ff1f");
    entity.setProvince("Thành phố Hà Nội");
    entity.setProduct(LoanPurposeEnum.LAND.getCode());
    entity.setCustomerName("DQKhanh");
    entity.setCustomerPhone("0968441766");
    entity.setStatus(AdviseStatusEnum.OPEN);

    return entity;
  }

  public static ProvinceEntity buildProvinceEntity() {
    ProvinceEntity entity = new ProvinceEntity();
    entity.setCode("01");
    entity.setName("Thành phố Hà Nội");

    return entity;
  }

  public static MtkTracking buildMtkTracking() {
    return MtkTracking.builder()
        .loanId("03f9e024-7ec4-4ed3-8f1f-330d232b6399")
        .utmCampaign("utmCampaign")
        .utmContent("utmContent")
        .utmSource("utmSource")
        .utmMedium("utmMedium")
        .build();
  }

  public static LoanAdviseCustomerEntity buildLoanAdviseCustomerEntity() {
    return LoanAdviseCustomerEntity.builder()
        .uuid("2a82b70d-4b67-4495-8649-84a6decfcce0")
        .adviseDate(new Date())
        .adviseTimeFrame(AdviseTimeFrameEnum.FROM_8_TO_12)
        .status(ContactStatusEnum.CONTACTED)
        .loanApplicationId("2a82b70d-4b67-4495-8649-84a6decfcce0")
        .build();
  }
}
