package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CreateLoanApplicationCommentRequest;
import vn.com.msb.homeloan.api.dto.request.UpdateLoanApplicationCommentRequest;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;

@Mapper
public interface LoanApplicationCommentMapper {

  LoanApplicationCommentMapper INSTANCE = Mappers.getMapper(LoanApplicationCommentMapper.class);

  //request to model
  LoanApplicationComment toModel(CreateLoanApplicationCommentRequest request);

  //request to model
  LoanApplicationComment toModel(UpdateLoanApplicationCommentRequest request);
}
