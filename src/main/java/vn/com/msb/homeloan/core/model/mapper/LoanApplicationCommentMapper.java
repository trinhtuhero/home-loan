package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;

@Mapper
public abstract class LoanApplicationCommentMapper {

  public static final LoanApplicationCommentMapper INSTANCE = Mappers.getMapper(
      LoanApplicationCommentMapper.class);

  public abstract LoanApplicationComment toModel(LoanApplicationCommentEntity entity);

  public abstract List<LoanApplicationComment> toModels(
      List<LoanApplicationCommentEntity> entities);

  public abstract LoanApplicationCommentEntity toEntity(LoanApplicationComment model);

  public abstract List<LoanApplicationCommentEntity> toEntitys(
      List<LoanApplicationComment> entities);
}
