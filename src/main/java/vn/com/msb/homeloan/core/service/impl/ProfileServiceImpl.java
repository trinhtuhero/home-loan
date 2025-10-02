package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.Profile;
import vn.com.msb.homeloan.core.model.mapper.ProfileMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.PlaceOfIssueIdCardRepository;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.ProfileService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository profileRepository;
  private final PlaceOfIssueIdCardRepository placeOfIssueIdCardRepository;
  private final LoanApplicationRepository loanApplicationRepository;

  @Transactional
  @Override
  public Profile updateProfile(Profile profile) {
    ProfileEntity profileInDB = profileRepository.findByUuid(profile.getUuid())
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND, profile.getUuid()));
    ProfileEntity profileUpdate = ProfileMapper.INSTANCE.toEntity(profile);
    profileUpdate.setStatus(profileInDB.getStatus());
    profileUpdate.setRefCode(profileInDB.getRefCode());
    List<LoanApplicationEntity> loanApplications = loanApplicationRepository.findByProfileId(
        profile.getUuid());
    List<LoanApplicationEntity> loanApplicationsDraft = loanApplications.stream()
        .filter(la -> la.getStatus().equals(LoanInfoStatusEnum.DRAFT)).collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(loanApplicationsDraft)) {
      LoanApplicationEntity loanApplication = loanApplicationsDraft.get(0);
      loanApplication.setFullName(profileUpdate.getFullName());
      loanApplication.setGender(profileUpdate.getGender());
      loanApplication.setNationality(profileUpdate.getNationality());
      loanApplication.setIdNo(profileUpdate.getIdNo());
      loanApplication.setIssuedOn(profileUpdate.getIssuedOn());
      loanApplication.setPlaceOfIssue(profileUpdate.getPlaceOfIssue());
      loanApplication.setOldIdNo(profileUpdate.getOldIdNo());
      loanApplication.setPhone(profileUpdate.getPhone());
      loanApplication.setEmail(profileUpdate.getEmail());
      loanApplication.setMaritalStatus(profileUpdate.getMaritalStatus());
      loanApplication.setNumberOfDependents(profileUpdate.getNumberOfDependents());
      loanApplication.setProvince(profileUpdate.getProvince());
      loanApplication.setProvinceName(profileUpdate.getProvinceName());
      loanApplication.setDistrict(profileUpdate.getDistrict());
      loanApplication.setDistrictName(profileUpdate.getDistrictName());
      loanApplication.setWard(profileUpdate.getWard());
      loanApplication.setWardName(profileUpdate.getWardName());
      loanApplication.setAddress(profileUpdate.getAddress());
      loanApplication.setBirthday(profileUpdate.getBirthday());
      loanApplicationRepository.save(loanApplication);
    }
    return ProfileMapper.INSTANCE.toModel(profileRepository.save(profileUpdate));
  }

  @Override
  public Profile getProfileById(String profileId) {
    ProfileEntity entity = profileRepository.findByUuid(profileId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND, profileId));
    Profile profile = ProfileMapper.INSTANCE.toModel(entity);
    Optional<PlaceOfIssueIdCardEntity> place = placeOfIssueIdCardRepository.findByCode(
        profile.getPlaceOfIssue());
    place.ifPresent(placeOfIssueIdCardEntity -> profile.setPlaceOfIssueName(
        placeOfIssueIdCardEntity.getName()));
    return profile;
  }
}
