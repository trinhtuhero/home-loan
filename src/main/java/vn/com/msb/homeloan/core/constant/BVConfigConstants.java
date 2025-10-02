package vn.com.msb.homeloan.core.constant;

public class BVConfigConstants {

  private BVConfigConstants() {
    throw new IllegalStateException("BVConfigConstant class");
  }

  public static final String RELATIONSHIP_CODE_SELF = "30";
  public static final String RELATIONSHIP_CODE_CHILD = "32";
  public static final String[] BV_RELATIONSHIP = {"30", "31", "32", "33", "34"};

  public static final String BV_HEALTH_PROGRAM_PLATINUM = "4";
  public static final String BV_HEALTH_PROGRAM_DIAMOND = "5";
  public static final String[] BV_HEALTH_PROGRAM = {"1", "2", "3", "4", "5"};

  public static final String BV_CONTRACT_TYPE_NEW = "New";
  public static final String BV_CONTRACT_TYPE_REUSE = "Reuse";
  public static final String[] BV_CONTRACT_TYPE = {"New", "Reuse"};

  public static final String[] BV_FILE_EXTENSION_TYPE = {"pdf", "jpg", "jpeg", "png"};
  public static final String BV_DOCUMENTS = "BV_DOCUMENTS";

  public static final int BV_NUMBER_OF_QUESTION = 3;
  public static final long BV_SMCN_AMOUNT_MIN = 50000000;
  public static final long BV_SMCN_AMOUNT_MAX = 100000000;
  public static final long BV_TNCN_AMOUNT_MIN = 50000000;
  public static final long BV_TNCN_AMOUNT_MAX = 1000000000;
}
