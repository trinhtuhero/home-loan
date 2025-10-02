package vn.com.msb.homeloan.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.client.MvalueClient;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.*;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.Mvalue;
import vn.com.msb.homeloan.core.model.MvalueAssetInfo;
import vn.com.msb.homeloan.core.model.mapper.CollateralMapper;
import vn.com.msb.homeloan.core.model.mapper.CollateralOwnerMapper;
import vn.com.msb.homeloan.core.model.mapper.MvalueAssetInfoMapper;
import vn.com.msb.homeloan.core.model.mapper.MvalueMapper;
import vn.com.msb.homeloan.core.model.request.mvalue.*;
import vn.com.msb.homeloan.core.model.response.mvalue.*;
import vn.com.msb.homeloan.core.repository.*;
import vn.com.msb.homeloan.core.service.MvalueService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.core.util.ObjectUtil;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class MvalueServiceImpl implements MvalueService {

  private final MvalueClient mvalueClient;
  private final EnvironmentProperties envProperties;
  private final MvalueRepository mvalueRepository;
  private final MvalueAssetInfoRepository mvalueAssetInfoRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final CollateralRepository collateralRepository;
  private final CollateralOwnerRepository collateralOwnerRepository;
  private final CollateralOwnerMapRepository collateralOwnerMapRepository;
  private final MarriedPersonRepository marriedPersonRepository;
  private final LoanPayerRepository loanPayerRepository;
  private final CmsUserRepository cmsUserRepository;
  private final RequestPricingRepository requestPricingRepository;
  private final OrganizationRepository organizationRepository;
  private final ProvinceRepository provinceRepository;
  private final DistrictRepository districtRepository;
  private final WardRepository wardRepository;
  private final MvalueUploadFilesRepository mvalueUploadFilesRepository;

  @Override
  public Mvalue getByCode(GetCollateralByCodeRequest getCollateralByCode, String loanApplicationId)
      throws Exception {
    loanApplicationRepository.findById(loanApplicationId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    MvalueEntity mvalueEntity = mvalueRepository.findByLoanApplicationIdAndAssetCode(
        loanApplicationId, getCollateralByCode.getAssetCode());
    if (mvalueEntity == null) {
      mvalueEntity = MvalueEntity.builder()
          .assetCode(getCollateralByCode.getAssetCode())
          .loanApplicationId(loanApplicationId).build();
    }
    mvalueEntity.setCheckDate(new Date());
    List<MvalueAssetInfoEntity> mvalueAssetInfoEntities = new ArrayList<>();
    GetCollateralByCodeData response = callApiGetByCode(getCollateralByCode);
    ObjectMapper objectMapper = new ObjectMapper();
    mvalueEntity.setMetaData(objectMapper.writeValueAsString(response));
    mvalueRepository.save(mvalueEntity);
    if (response.getResponse() != null && !CollectionUtils.isEmpty(
        response.getResponse().getAssetInfo())) {
      List<GetCollateralByCodeData.Response.AssetInfo> lstAssetInfo = response.getResponse()
          .getAssetInfo();
      //xóa phần tử trùng nhau
      Set<GetCollateralByCodeData.Response.AssetInfo> setAssetInfo = new HashSet<>(lstAssetInfo);
      for (GetCollateralByCodeData.Response.AssetInfo assetInfo : setAssetInfo) {
        MvalueAssetInfoEntity mvalueAssetInfoEntity = mvalueAssetInfoRepository.findByMvalueIdAndAssetIdMvalue(
            mvalueEntity.getUuid(), assetInfo.getId());
        if (mvalueAssetInfoEntity == null) {
          mvalueAssetInfoEntity = MvalueAssetInfoEntity.builder()
              .mvalueId(mvalueEntity.getUuid())
              .assetIdMvalue(assetInfo.getId()).build();
        }
        mvalueAssetInfoEntity.setValuationNotice(assetInfo.getValuationNotice());
        mvalueAssetInfoEntity.setValuation(assetInfo.getValuation());
        if (assetInfo.getLegalPaper() != null) {
          mvalueAssetInfoEntity.setOwnershipCertificateNo(
              assetInfo.getLegalPaper().getOwnershipCertificateNO() != null
                  ? assetInfo.getLegalPaper().getOwnershipCertificateNO() : null);
          mvalueAssetInfoEntity.setIssuedDate(
              assetInfo.getLegalPaper().getIssuedDate() != null ? DateUtils.convertUTCDate(
                  new Date(assetInfo.getLegalPaper().getIssuedDate())) : null);
          mvalueAssetInfoEntity.setIssuedBy(
              assetInfo.getLegalPaper().getIssuedBy() != null ? assetInfo.getLegalPaper()
                  .getIssuedBy() : null);
        }
        if (assetInfo.getGeoProvince() != null) {
          mvalueAssetInfoEntity.setProvinceId(
              assetInfo.getGeoProvince().getId() != null ? assetInfo.getGeoProvince().getId()
                  : null);
          mvalueAssetInfoEntity.setDistrictId(
              assetInfo.getGeoDistrict().getId() != null ? assetInfo.getGeoDistrict().getId()
                  : null);
        }
        if (assetInfo.getGeoWard() != null) {
          mvalueAssetInfoEntity.setWardId(
              assetInfo.getGeoWard().getId() != null ? assetInfo.getGeoWard().getId() : null);
        }
        if (assetInfo.getGeoStreet() != null) {
          mvalueAssetInfoEntity.setStreetName(
              assetInfo.getGeoStreet().getName() != null ? assetInfo.getGeoStreet().getName()
                  : null);
        }
        mvalueAssetInfoEntity.setAddress(assetInfo.getDetail());
        mvalueAssetInfoEntity.setFileLink(assetInfo.getFileLink());
        mvalueAssetInfoEntity.setValuer(assetInfo.getValuer());
        mvalueAssetInfoEntity.setValuationDate(assetInfo.getDate());
        mvalueAssetInfoEntity.setStatus(assetInfo.getStatusZ());
        mvalueAssetInfoEntities.add(mvalueAssetInfoEntity);
      }
      mvalueAssetInfoRepository.saveAll(mvalueAssetInfoEntities);
    }
    Mvalue result = MvalueMapper.INSTANCE.toModel(mvalueEntity);
    result.setMessage(response.getResponse() != null ? response.getResponse().getMessage() : null);
    List<MvalueAssetInfo> mvalueAssetInfo = MvalueAssetInfoMapper.INSTANCE.toModels(
        mvalueAssetInfoEntities);
    result.setAssetInfo(mvalueAssetInfo);
    return result;
  }

  @Override
  public List<Collateral> checkDuplicate(String loanId) {
    List<Collateral> collaterals =
        CollateralMapper.INSTANCE.toModels(
            collateralRepository.findByLoanIdOrderByCreatedAtAsc(loanId));
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
      .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    List<CollateralOwner> collateralOwners = CollateralOwnerMapper.INSTANCE.toModels(
      collateralOwnerRepository.findByLoanIdOrderByCreatedAtAsc(loanId));
    List<CollateralOwnerMapEntity> collateralOwnerMapEntities = collateralOwnerMapRepository.findByLoanId(
      loanId);
    for (Collateral collateral : collaterals) {
      Optional<ProvinceEntity> province = provinceRepository.findByCode(collateral.getProvince());
      Optional<DistrictEntity> district = districtRepository.findByCode(collateral.getDistrict());
      Optional<WardEntity> ward = wardRepository.findByCode(collateral.getWard());

      province.ifPresent(provinceEntity -> collateral.setMvalueProvince(provinceEntity.getMvalueCode()));
      district.ifPresent(districtEntity -> collateral.setMvalueDistrict(districtEntity.getMvalueCode()));
      ward.ifPresent(wardEntity -> collateral.setMvalueWard(wardEntity.getMvalueCode()));
      collateral.setCollateralOwnerName(getCollateralOwnerName(collateral,collateralOwnerMapEntities,loanApplication,collateralOwners));
      if (ObjectUtil.isNotEmpty(collateral.getSoTsMvalue())) {
        collateral.setMValueValuationStatus(MValueValuationStatusEnum.DNDG.getCode());
      } else {
        CheckDuplicateData response = checkDuplicateData(genCheckDuplicateRequest(collateral));
        int yearValuationLast = 0;
        StringBuilder dsTBDG = new StringBuilder();
        if (response != null
            && response.getCode() != null
            && response.getCode().equalsIgnoreCase("API_000")) {
          List<CheckDuplicateData.Asset> assets = response.getAsset();
          for (CheckDuplicateData.Asset asset : assets) {
            if (ObjectUtil.isNotEmpty(asset.getSoTBDG())) {
              int index = asset.getSoTBDG().indexOf(".TBĐG");
              int start = index - 4;
              int year = Integer.parseInt(asset.getSoTBDG().substring(Math.max(start, 0), index));
              yearValuationLast = Math.max(year, yearValuationLast);
              dsTBDG.append(", ").append(asset.getSoTBDG());
            }
          }
          collateral.setMValueValuationStatus(MValueValuationStatusEnum.TBDG.getCode());
          collateral.setYearValuationLast(yearValuationLast);
          if(!StringUtils.isEmpty(dsTBDG.toString()))
            collateral.setDsTBDG(dsTBDG.substring(1).trim());
        } else {
          collateral.setMValueValuationStatus(MValueValuationStatusEnum.NULL.getCode());
        }
      }
    }
    return collaterals;
  }

  @Override
  public String createProfile(CMSCreateProfileRequest request) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(request.getLoanId())
      .orElseThrow(
        () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "loanId", request.getLoanId()));
    List<CMSTsdg> lst = request.getListTsdg();
    String email = AuthorizationUtil.getEmail();
    String user = "";
    if (ObjectUtil.isNotEmpty(email)) {
      user = email.split("@")[0];
    }
    CreateProfileRequest createProfileRequest = MvalueMapper.INSTANCE.toClientRequest(request);

    CmsUserEntity cmsUserEntity = cmsUserRepository.findByEmail(email)
      .orElseThrow(() -> new ApplicationException(ErrorEnum.CMS_USER_EMAIL_FOUND));
    OrganizationEntity organization = organizationRepository.findByCode(cmsUserEntity.getBranchCode());
    String dvDndg = organization.getMvalueCode();


//    createProfileRequest.setDonVidndg(dvDndg);
//    createProfileRequest.setUsernameCbkt(user);
    createProfileRequest.setDonVidndg("165");
    createProfileRequest.setUsernameCbkt("RB_caugiay_RM");
    createProfileRequest.setTenKH(loanApplication.getFullName());
    createProfileRequest.setDkkdCmnd(loanApplication.getIdNo());
    createProfileRequest.setPhanKhucKh("RB");
    createProfileRequest.setLoaiGiayTo("RPL05");
    createProfileRequest.setSdtCbpt(cmsUserEntity.getPhone());
    createProfileRequest.setBenDndg("KH");
    createProfileRequest.setDauMoiLienHe(loanApplication.getFullName());
    createProfileRequest.setSdtDauMoi(loanApplication.getPhone());
    createProfileRequest.setDiaChiDauMoi(
        loanApplication.getAddress()
            + ", "
            + loanApplication.getWardName()
            + ", "
            + loanApplication.getDistrictName()
            + ", "
            + loanApplication.getProvinceName());
    String soCif = ObjectUtil.isEmpty(loanApplication.getCifNo())?"":loanApplication.getCifNo();
    String finalDvDndg = dvDndg;
    createProfileRequest.getListTsdg().stream()
        .forEach(
            t -> {
              if (t.getCollateralGroup() == 2) {
                t.setTenTs(
                    soCif
                        + finalDvDndg
                        + (ObjectUtil.isEmpty(t.getCollateralType()) ? "" : t.getCollateralType())
                        + (ObjectUtil.isEmpty(t.getSoGCN()) ? "" : t.getSoGCN()));
              }
            });
    log.info("MValue create profile request :{}",new Gson().toJson(createProfileRequest));
    String result = "";
    try{
      CreateProfileResponse response = mvalueClient.createProfile(createProfileRequest, envProperties.getClientCode()).getBody();
      if (response!=null)log.info("MValue create profile response :{}",new Gson().toJson(response));
      if(response!=null && response.getData()!=null && response.getData().getCode()!=null&&
        response.getData().getCode().equalsIgnoreCase("API_000")){
        Profile profile = response.getData().getProfile();
        RequestPricingEntity entity = new RequestPricingEntity();

        List<MvalueUploadFilesEntity> lstUploadFiles = mvalueUploadFilesRepository.findByIdIn(request.getLsTS());
        if(ObjectUtil.isNotEmpty(profile)){
          entity.setContractCode(profile.getProfileId());
          entity.setStatus(RequestPricingEnum.WAITING.name());
          entity.setLoanId(request.getLoanId());
          lstUploadFiles.stream().forEach(t->t.setProfileId(profile.getProfileId()));
          mvalueUploadFilesRepository.saveAll(lstUploadFiles);
        }

        requestPricingRepository.save(entity);
        List<CollateralEntity> entities = new ArrayList<>();
        if (!profile.getAssets().isEmpty()
            && !lst.isEmpty()
            && profile.getAssets().size() == lst.size()) {
          for (int i = 0; i < profile.getAssets().size(); i++) {
            String collateralId = lst.get(i).getCollateralId();
            CollateralEntity collateralEntity =
                collateralRepository
                    .findById(collateralId)
                    .orElseThrow(
                        () ->
                            new ApplicationException(ErrorEnum.COLLATERAL_NOT_FOUND, collateralId));
            collateralEntity.setSoTsMvalue(profile.getAssets().get(i).getId());
            entities.add(collateralEntity);
          }
          collateralRepository.saveAll(entities);
        }else {
          log.info("MValue error fill lst asset mvalue: {}",profile);
        }

      }
      result = response.getData().getMessage();
    }catch (Exception e){
      log.info("MValue create profile Exception:",e.getMessage());
      return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    return result;
  }

  @Override
  public GetPriceBracketResponse getPriceBracketInfo(GetPriceBracketInfoRequest request) {
    try {
      GetPriceBracketResponse result =
          mvalueClient.getPriceBracketInfo(request, envProperties.getClientCode()).getBody();
      log.info("MValuegetPriceBracketInfo: {}", result);
      return result;
    } catch (Exception e) {
      log.error("getPriceBracketInfo", e.getMessage());
      return new GetPriceBracketResponse();
    }
  }

  private CheckDuplicateRequest genCheckDuplicateRequest(Collateral collateral) {
    CheckDuplicateRequest request = new CheckDuplicateRequest();
    if (collateral.getType().equals(CollateralTypeEnum.PTVT)) {
      request.setNhomTaiSan(NhomTSMValueEnum.PTVT.getName());
      request.setHdSkSmBs(collateral.getRegistrationOrContractNo());
    } else if (collateral.getType().equals(CollateralTypeEnum.GTCG)) {
      return null;
    } else {
      request.setNhomTaiSan(NhomTSMValueEnum.BDS.getName());
      request.setSoGCN(collateral.getLegalDoc());
    }
    return request;
  }

  private CheckDuplicateData checkDuplicateData(CheckDuplicateRequest request) {
    CheckDuplicateResponse response;
    try {
      response = mvalueClient.checkDuplicate(request, envProperties.getClientCode()).getBody();
      log.info("MValue check duplicate info: {}", response);
    } catch (Exception e) {
      log.error("MValue check duplicate exception: {}", e);
      return null;
    }
    if (response != null) {
      return response.getData();
    } else {
      return null;
    }
  }

  private GetCollateralByCodeData callApiGetByCode(
      GetCollateralByCodeRequest getCollateralByCodeRequest) throws Exception {
    GetCollateralByCodeResponse response = mvalueClient.getCollateralByCode(
        getCollateralByCodeRequest, envProperties.getClientCode()).getBody();
    if (response != null) {
      GetCollateralByCodeData getCollateralByCodeData = response.getData();
      log.info("M-value get-by-code: " + getCollateralByCodeData);
      return getCollateralByCodeData;
    } else {
      throw new Exception("Api m-value get-by-code fail");
    }
  }

  private String getCollateralOwnerName(
      Collateral collateral,
      List<CollateralOwnerMapEntity> collateralOwnerMapEntities,
      LoanApplicationEntity loanApplication,
      List<CollateralOwner> collateralOwners) {
    String name = "", marriedName = "";
    MarriedPersonEntity marriedPersonEntity =
        marriedPersonRepository.findOneByLoanId(loanApplication.getUuid());
    if (ObjectUtil.isNotEmpty(marriedPersonEntity)) marriedName = marriedPersonEntity.getFullName();
    if (CollateralStatusEnum.THIRD_PARTY.equals(collateral.getStatus())) {
      List<CollateralOwnerMapEntity> temp =
          collateralOwnerMapEntities.stream()
              .filter(x -> x.getCollateralId().equalsIgnoreCase(collateral.getUuid()))
              .collect(Collectors.toList());
      name = getCollateralOwnerByCollateral(loanApplication, collateralOwners, temp);
    }
    if (CollateralStatusEnum.ME.equals(collateral.getStatus())) {
      name = loanApplication.getFullName();
    }
    if (CollateralStatusEnum.COUPLE.equals(collateral.getStatus())) {
      name = loanApplication.getFullName() + ", "+ marriedName;
    }
    if (CollateralStatusEnum.SPOUSE.equals(collateral.getStatus())) {
      name = marriedName;
    }
    return name.trim();
  }

  private String getCollateralOwnerByCollateral(
      LoanApplicationEntity loanApplication,
      List<CollateralOwner> collateralOwners,
      List<CollateralOwnerMapEntity> collateralOwnerMapEntities) {
    if (org.apache.commons.collections4.CollectionUtils.isEmpty(collateralOwnerMapEntities)) {
      return "";
    }

    StringBuilder results = new StringBuilder();
    // cua khach hang
    List<String> customers =
        collateralOwnerMapEntities.stream()
            .filter(x -> CollateralOwnerMapTypeEnum.ME.equals(x.getType()))
            .map(CollateralOwnerMapEntity::getCollateralOwnerId)
            .collect(Collectors.toList());
    if (!org.apache.commons.collections4.CollectionUtils.isEmpty(customers)) {
      results.append(", " + loanApplication.getFullName());
    }

    // Cua vk chong khach hang
    List<String> couples =
        collateralOwnerMapEntities.stream()
            .filter(x -> CollateralOwnerMapTypeEnum.COUPLE.equals(x.getType()))
            .map(CollateralOwnerMapEntity::getCollateralOwnerId)
            .collect(Collectors.toList());
    if (!org.apache.commons.collections4.CollectionUtils.isEmpty(couples)) {
      Optional<MarriedPersonEntity> marriedPersonEntity =
          marriedPersonRepository.findById(couples.get(0));
      if (marriedPersonEntity.isPresent()) {
        results.append(", " + marriedPersonEntity.get().getFullName());
      }
    }

    // Nguoi dong tra no
    List<String> loanPayers =
        collateralOwnerMapEntities.stream()
            .filter(x -> CollateralOwnerMapTypeEnum.LOAN_PAYER.equals(x.getType()))
            .map(CollateralOwnerMapEntity::getCollateralOwnerId)
            .collect(Collectors.toList());

    if (!org.apache.commons.collections4.CollectionUtils.isEmpty(loanPayers)) {
      List<LoanPayerEntity> loanPayerEntities =
          loanPayerRepository.findByLoanId(loanApplication.getUuid());
      if (!org.apache.commons.collections4.CollectionUtils.isEmpty(loanPayerEntities)) {
        List<LoanPayerEntity> temp =
            loanPayerEntities.stream()
                .filter(x -> loanPayers.contains(x.getUuid()))
                .collect(Collectors.toList());
        emptyIfNull(temp)
            .forEach(loanPayer -> results.append(", " + loanPayer.getFullName()));
      }
    }

    // Ben thu 3
    List<String> thirdParties =
        collateralOwnerMapEntities.stream()
            .filter(x -> CollateralOwnerMapTypeEnum.THIRD_PARTY.equals(x.getType()))
            .map(CollateralOwnerMapEntity::getCollateralOwnerId)
            .collect(Collectors.toList());
    if (!org.apache.commons.collections4.CollectionUtils.isEmpty(thirdParties)
        && !org.apache.commons.collections4.CollectionUtils.isEmpty(collateralOwners)) {
      List<CollateralOwner> temps =
          collateralOwners.stream()
              .filter(x -> thirdParties.contains(x.getUuid()))
              .collect(Collectors.toList());
      emptyIfNull(temps)
          .forEach(temp -> results.append(", " + temp.getFullName()));
    }
    if(!StringUtils.isEmpty(results.toString())){
      return results.substring(1);
    }
    return results.toString();
  }
}
