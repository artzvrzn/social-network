package com.artzvrzn.service;

import com.artzvrzn.dto.PageDto;
import com.artzvrzn.dto.ProfileDto;

public interface ProfileService {

  void createProfile(ProfileDto dto);

  ProfileDto getProfile(Long profileId);

  PageDto<ProfileDto> getProfiles(int page, int size);

  void updateProfile(Long profileId, ProfileDto dto);

  void deleteProfile(Long profileId);
}
