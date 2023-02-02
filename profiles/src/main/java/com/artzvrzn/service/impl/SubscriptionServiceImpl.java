package com.artzvrzn.service.impl;

import com.artzvrzn.dao.ProfileRepository;
import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.domain.Profile;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.dto.SubscriptionDto;
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

  @Override
  public Page<ProfileDto> getSubscribers(Long userId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return subscriptionRepository.getAllSubscribers(userId, pageable)
      .map(Subscription::getSubscriber)
      .map(e -> converter.convert(e, ProfileDto.class));
  }

  @Override
  public Page<ProfileDto> getSubscriptions(Long userId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return subscriptionRepository.getAllSubscriptions(userId, pageable)
      .map(Subscription::getTarget)
      .map(e -> converter.convert(e, ProfileDto.class));
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
}
