package vn.com.msb.homeloan.core.model.cic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * CIC base model
 */
@Getter
@Setter
public abstract class CicBaseModel {

  @JsonIgnore
  private UUID requestId;
}
