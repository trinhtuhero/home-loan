package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadFileStatus {

  String uuid;

  String loanApplicationId;

  String fileConfigId;

  Integer isEnough;

  CommentStatusEnum status;

  String emplId;
}
