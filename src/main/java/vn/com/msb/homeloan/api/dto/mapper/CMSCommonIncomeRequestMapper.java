package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSCommonIncomeRequest;
import vn.com.msb.homeloan.core.model.CommonIncome;

@Mapper
public interface CMSCommonIncomeRequestMapper {

  CMSCommonIncomeRequestMapper INSTANCE = Mappers.getMapper(CMSCommonIncomeRequestMapper.class);

  CommonIncome toModel(CMSCommonIncomeRequest request);
}
