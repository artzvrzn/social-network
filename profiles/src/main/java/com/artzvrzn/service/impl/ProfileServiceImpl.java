package com.artzvrzn.service.impl;

import com.artzvrzn.dao.ProfileRepository;
import com.artzvrzn.domain.Location;
import com.artzvrzn.domain.Profile;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.model.projection.ProfileView;
import com.artzvrzn.service.AuthService;
import com.artzvrzn.service.ProfileService;
import com.artzvrzn.util.ClaimsUtil;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, readOnly = true)
@Log4j2
public class ProfileServiceImpl implements ProfileService {
  private final ProfileRepository profileRepository;
  private final AuthService authService;
  private final ConversionService converter;
  @Autowired
  private ProfileService self;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createProfile(ProfileDto dto) {
    Profile entity = converter.convert(dto, Profile.class);
    String subject = authService.getAuthenticatedUser();
    entity.setOwner(subject);
    profileRepository.save(entity);
    log.info("profile of user {} has been successfully created", subject);
  }

  @Override
  public ProfileDto getProfile(Long profileId) {
    String subject = authService.getAuthenticatedUser();
    ProfileView profileView =
      profileRepository.findByIdWithSubscriptionStatusAndQuantity(profileId, subject)
      .orElseThrow(() -> new IllegalArgumentException("Profile doesn't exist"));
    return converter.convert(profileView, ProfileDto.class);
  }

  @Override
  public ProfileDto getProfile() {
    String subject = authService.getAuthenticatedUser();
    Optional<ProfileView> optional =
      profileRepository.findByOwnerWithSubscriptionQuantity(subject);
    if (optional.isEmpty()) {
      log.info("{} tries to access profile, but profile doesn't exist", subject);
      ProfileDto dto = dtoFromClaims(authService.getClaims());
      self.createProfile(dto);
      optional = profileRepository.findByOwnerWithSubscriptionQuantity(subject);
    }
    if (optional.isPresent()) {
      return converter.convert(optional.get(), ProfileDto.class);
    }
    throw new IllegalStateException("failed to find profile");
  }

  @Override
  public Page<ProfileDto> getProfiles(int page, int size) {
    String subject = authService.getAuthenticatedUser();
    Pageable pageable = PageRequest.of(page, size);
    Page<ProfileView> projections =
      profileRepository.findAllWithSubscriptionStatusAndQuantity(subject, pageable);
    return projections.map(e -> converter.convert(e, ProfileDto.class));
  }

  @Override
  @Transactional
  public void updateProfile(Long profileId, ProfileDto dto) {
    Profile entity = profileRepository.getReferenceById(profileId);
    entity.setName(dto.getName());
    entity.setMiddleName(dto.getMiddleName());
    entity.setFamilyName(dto.getFamilyName());
    entity.setUsername(dto.getUsername());
    entity.setEmail(dto.getEmail());
    entity.setLocation(converter.convert(dto.getLocation(), Location.class));
    entity.setBirthDate(dto.getBirthDate());
    entity.setImageSmall(dto.getImageSmall());
    entity.setImageLarge(dto.getImageLarge());
    profileRepository.save(entity);
  }

  @Override
  public void updateProfile(String owner, ProfileDto dto) {

  }

  @Override
  @Transactional
  public void deleteProfile(Long profileId) {
    profileRepository.deleteById(profileId);
  }

  @Override
  public void deleteProfile(String owner) {

  }

  private ProfileDto dtoFromClaims(Map<String, Object> claims) {
    return ClaimsUtil.profileDtoFromClaims(claims);
  }
}
