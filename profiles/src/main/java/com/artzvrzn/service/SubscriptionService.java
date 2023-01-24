package com.artzvrzn.service;

import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.ProfileDto;
import org.springframework.data.domain.Page;

public interface SubscriptionService {

  Page<ProfileDto> getSubscribers(Long profileId, int page, int size);

  Page<ProfileDto> getSubscriptions(Long profileId, int page, int size);

  void follow(SubscriptionDto dto);

  void unfollow(SubscriptionDto dto);
}
