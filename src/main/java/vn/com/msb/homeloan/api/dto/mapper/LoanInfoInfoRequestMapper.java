package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanInfoInfoRequestMapper {

  LoanInfoInfoRequestMapper INSTANCE = Mappers.getMapper(LoanInfoInfoRequestMapper.class);
}

