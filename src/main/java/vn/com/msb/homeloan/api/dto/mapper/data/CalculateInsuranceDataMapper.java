package vn.com.msb.homeloan.api.dto.mapper.data;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.data.cj4.CalculateInsuranceData;
import vn.com.msb.homeloan.core.model.response.cj4.CalculateInsurance;

@Mapper
public interface CalculateInsuranceDataMapper {

  CalculateInsuranceDataMapper INSTANCE = Mappers.getMapper(CalculateInsuranceDataMapper.class);

  CalculateInsuranceData toData(CalculateInsurance data);

  List<CalculateInsuranceData> toDatas(List<CalculateInsurance> data);
}
