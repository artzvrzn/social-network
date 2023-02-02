package com.artzvrzn.service.impl;

import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.dao.ProfileRepository;
import com.artzvrzn.domain.Profile;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.dto.PageDto;
import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.mapper.ProfileMapper;
import com.artzvrzn.mapper.ProfilePageMapper;
import com.artzvrzn.service.AuthService;
import com.artzvrzn.service.SubscriptionService;
import com.artzvrzn.service.ProfileService;
import com.artzvrzn.util.ClaimsUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, readOnly = true)
@Log4j2
public class ProfileServiceImpl implements ProfileService {
  private final ProfileRepository profileRepository;
  private final ProfileMapper profileMapper;
  private final ProfilePageMapper pageMapper;
  private final AuthService authService;
  @Autowired
  private ProfileService self;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createProfile(ProfileDto dto) {
    Profile entity = profileMapper.map(dto);
    String owner = authService.getAuthenticatedUser();
    entity.setOwner(owner);
    profileRepository.save(entity);
    log.info("profile of user {} has been successfully created", owner);
  }

  @Override
  public ProfileDto getProfile(Long profileId) {
    Profile entity = profileRepository.findById(profileId)
      .orElseThrow(() -> new IllegalArgumentException("Profile doesn't exist"));
    return profileMapper.map(entity);
  }

  @Override
  public ProfileDto getProfile() {
    String owner = authService.getAuthenticatedUser();
    Optional<Profile> optional = profileRepository.findProfileByOwner(owner);
    if (optional.isEmpty()) {
      log.info("{} tries to access profile, but profile doesn't exist", owner);
      ProfileDto dto = dtoFromClaims(authService.getClaims());
      self.createProfile(dto);
      optional = profileRepository.findProfileByOwner(owner);
    }
    if (optional.isPresent()) {
      return profileMapper.map(optional.get());
    }
    throw new IllegalStateException("failed to find profile");
  }

  @Override
  public PageDto<ProfileDto> getProfiles(int page, int size) {
    Pageable pageable = buildPageable(page, size);
    Page<Profile> entities = profileRepository.findAll(pageable);
    return pageMapper.map(entities);
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
    entity.setLocation(profileMapper.map(dto.getLocation()));
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

  private Pageable buildPageable(int page, int size) {
    return PageRequest.of(page - 1, size);
  }

  private ProfileDto dtoFromClaims(Map<String, Object> claims) {
    return ClaimsUtil.profileDtoFromClaims(claims);
  }
}
