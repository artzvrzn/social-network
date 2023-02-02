package com.artzvrzn.service.impl;

import com.artzvrzn.dao.ProfileRepository;
import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.domain.Profile;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.mapper.ProfileMapper;
import com.artzvrzn.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
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
  private final ProfileMapper profileMapper;

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
