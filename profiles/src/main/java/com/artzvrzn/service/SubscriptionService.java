package com.artzvrzn.service;

import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.UserDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface SubscriptionService {

  Page<UserDto> getUserSubscribers(Long userId, int page, int size);

  Page<UserDto> getUserSubscriptions(Long userId, int page, int size);

  void followUser(SubscriptionDto dto);

  void unfollowUser(SubscriptionDto dto);
}
