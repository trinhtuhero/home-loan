package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.core.model.Profile;

public interface ProfileService {

  Profile updateProfile(Profile profile);

  Profile getProfileById(String profileId);
}
