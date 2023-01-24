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
import com.artzvrzn.service.SubscriptionService;
import com.artzvrzn.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, readOnly = true)
@Log4j2
public class ProfileServiceImpl implements ProfileService, SubscriptionService {
  private final ProfileRepository profileRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final ProfileMapper profileMapper;
  private final ProfilePageMapper pageMapper;

  @Override
  @Transactional
  public void createProfile(ProfileDto dto) {
    Profile entity = profileMapper.map(dto);
    profileRepository.save(entity);
  }

  @Override
  public ProfileDto getProfile(Long userId) {
    Profile entity = profileRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException("Profile doesn't exist"));
    return profileMapper.map(entity);
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
  @Transactional
  public void deleteProfile(Long profileId) {
    profileRepository.deleteById(profileId);
  }

  @Override
  public Page<ProfileDto> getSubscribers(Long userId, int page, int size) {
    Pageable pageable = buildPageable(page, size);
    return subscriptionRepository.getAllSubscribers(userId, pageable)
      .map(Subscription::getSubscriber)
      .map(profileMapper::map);
  }

  @Override
  public Page<ProfileDto> getSubscriptions(Long userId, int page, int size) {
    Pageable pageable = buildPageable(page, size);
    return subscriptionRepository.getAllSubscriptions(userId, pageable)
      .map(Subscription::getTarget)
      .map(profileMapper::map);
  }

  @Override
  @Transactional
  public void follow(SubscriptionDto dto) {
    Profile targetId = profileRepository.getReferenceById(dto.getTargetId());
    Profile subscriberId = profileRepository.getReferenceById(dto.getSubscriberId());
    Subscription subscriptionEntity = Subscription.builder()
      .id(new SubscriptionId(targetId.getId(), subscriberId.getId()))
      .target(targetId)
      .subscriber(subscriberId)
      .build();
    subscriptionRepository.save(subscriptionEntity);
  }

  @Override
  @Transactional
  public void unfollow(SubscriptionDto dto) {
    SubscriptionId id = new SubscriptionId(dto.getTargetId(), dto.getSubscriberId());
    subscriptionRepository.deleteById(id);
  }

  private Pageable buildPageable(int page, int size) {
    return PageRequest.of(page - 1, size);
  }
}
