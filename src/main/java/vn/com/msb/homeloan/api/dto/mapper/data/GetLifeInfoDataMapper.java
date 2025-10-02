package vn.com.msb.homeloan.api.dto.mapper.data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.data.cj4.GetLifeInfoData;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.model.response.cj4.GetLifeInfo;

@Mapper
public interface GetLifeInfoDataMapper {

  GetLifeInfoDataMapper INSTANCE = Mappers.getMapper(GetLifeInfoDataMapper.class);


  @Mapping(source = "isInterested", target = "interestedStatus", qualifiedByName = "mapInterestedStatus")
  @Mapping(source = "gender", target = "gender", qualifiedByName = "mapGender")
  @Mapping(source = "dob", target = "dob", qualifiedByName = "mapDob")
  GetLifeInfoData toData(GetLifeInfo data);

  List<GetLifeInfoData> toDatas(List<GetLifeInfo> data);

  @Named("mapInterestedStatus")
  default String mapInterestedStatus(Integer isInterested) {
    if (isInterested == null) {
      return null;
    } else if (isInterested == 1) {
      return InterestedEnum.INTERESTED.getCode();
    } else if (isInterested == 0) {
      return InterestedEnum.NOT_INTERESTED.getCode();
    } else {
      return null;
    }
  }

  @Named("mapGender")
  default String mapGender(String gender) {
    if ("1".equals(gender)) {
      return GenderEnum.MALE.getCode();
    } else if ("0".equals(gender)) {
      return GenderEnum.FEMALE.getCode();
    } else {
      return null;
    }
  }

  @Named("mapDob")
  default String mapDob(String birth) throws ParseException {
    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
    Date date = format1.parse(birth);
    return format2.format(date);
  }
}
