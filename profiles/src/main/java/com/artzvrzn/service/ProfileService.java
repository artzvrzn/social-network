package com.artzvrzn.service;

import com.artzvrzn.dto.ProfileDto;
import org.springframework.data.domain.Page;

public interface ProfileService {

  void createProfile(ProfileDto dto);

  ProfileDto getProfile(Long profileId);

  ProfileDto getProfile();

  Page<ProfileDto> getProfiles(int page, int size);

  void updateProfile(Long profileId, ProfileDto dto);

  void updateProfile(String owner, ProfileDto dto);

  void deleteProfile(Long profileId);

  void deleteProfile(String owner);
}
