package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

public enum Headers {
  X_PRODUCT_NAME("x-product-name"),
  X_DOCUMENT_TYPE("x-document-type"),
  SSE_ALGORITHM("x-amz-server-side-encryption"),
  KMS_KEY_ID("x-amz-server-side-encryption-aws-kms-key-id"),
  CLIENT_CODE("client_code"),
  API_KEY("apikey"),
  CONTENT_TYPE("Content-Type");

  @Getter
  private final String value;

  Headers(String value) {
    this.value = value;
  }

}