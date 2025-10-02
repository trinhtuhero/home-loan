package vn.com.msb.homeloan.api.dto.mapper.data;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.data.cj4.ProductData;
import vn.com.msb.homeloan.core.model.response.cj4.ProductInfo;

@Mapper
public interface ProductDataMapper {

  ProductDataMapper INSTANCE = Mappers.getMapper(ProductDataMapper.class);

  ProductData toData(ProductInfo data);

  List<ProductData> toDatas(List<ProductInfo> data);
}
