package com.artzvrzn.service.impl;

import com.artzvrzn.dao.ProfileRepository;
import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.domain.Profile;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.service.AuthService;
import com.artzvrzn.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SubscriptionServiceImpl implements SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final ProfileRepository profileRepository;
  private final ConversionService converter;
  private final AuthService authService;

  @Override
  public Page<ProfileDto> getSubscribers(Long profileId, int page, int size) {
    return getSubscribers(profileId, Long.class, page, size);
  }

  @Override
  public Page<ProfileDto> getSubscribers(String userId, int page, int size) {
    return getSubscribers(userId, String.class, page, size);
  }

  @Override
  public Page<ProfileDto> getSubscriptions(Long profileId, int page, int size) {
    return getSubscriptions(profileId, Long.class, page, size);
  }

  @Override
  public Page<ProfileDto> getSubscriptions(String userId, int page, int size) {
    return getSubscriptions(userId, String.class, page, size);
  }

  @Override
  @Transactional
  public void follow(Long targetId) {
    Profile subscriber = getAuthenticatedUserProfile();
    Profile target = profileRepository.getReferenceById(targetId);
    Subscription subscriptionEntity = Subscription.builder()
      .id(new SubscriptionId(target.getId(), subscriber.getId()))
      .target(target)
      .subscriber(subscriber)
      .build();
    subscriptionRepository.save(subscriptionEntity);
  }

  @Override
  @Transactional
  public void unfollow(Long targetId) {
    Long subscriberId = getAuthenticatedUserProfile().getId();
    SubscriptionId id = new SubscriptionId(targetId, subscriberId);
    subscriptionRepository.deleteById(id);
  }

  private <T> Page<ProfileDto> getSubscriptions(T where, Class<T> whereClass, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Subscription> subscriptions = null;
    if (Long.class.equals(whereClass)) {
      subscriptions = subscriptionRepository.getAllSubscriptions((Long) where, pageable);
    }
    if (String.class.equals(whereClass)) {
      subscriptions = subscriptionRepository.getAllSubscriptions((String) where, pageable);
    }
    if (subscriptions != null) {
      return mapToProfilesPage(subscriptions);
    }
    throw new IllegalStateException("Failed to get subscriptions by " + whereClass.getName());
  }

  private <T> Page<ProfileDto> getSubscribers(T where, Class<T> whereClass, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Subscription> subscriptions = null;
    if (Long.class.equals(whereClass)) {
      subscriptions = subscriptionRepository.getAllSubscribers((Long) where, pageable);
    }
    if (String.class.equals(whereClass)) {
      subscriptions = subscriptionRepository.getAllSubscribers((String) where, pageable);
    }
    if (subscriptions != null) {
      return mapToProfilesPage(subscriptions);
    }
    throw new IllegalStateException("Failed to get subscribers by " + whereClass.getName());
  }

  private Page<ProfileDto> mapToProfilesPage(Page<Subscription> subscriptions) {
    return subscriptions
      .map(Subscription::getTarget)
      .map(e -> converter.convert(e, ProfileDto.class));
  }

  private Profile getAuthenticatedUserProfile() {
    String authenticatedUser = authService.getAuthenticatedUser();
    return getProfileByOwner(authenticatedUser);
  }

  private Profile getProfileByOwner(String owner) {
    return profileRepository.findProfileByOwner(owner)
      .orElseThrow(() -> new IllegalStateException("authenticated user's profile does not exist!"));
  }
}
