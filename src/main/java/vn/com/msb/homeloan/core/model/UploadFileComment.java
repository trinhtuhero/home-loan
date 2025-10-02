package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
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
public class UploadFileComment {

  String uuid;

  String loanApplicationId;

  String fileConfigId;

  String comment;

  CommentStatusEnum status;

  String emplId;

  String emplFullName;

  String emplEmail;

  String emplRole;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Saigon")
  Instant updatedAt;
}
