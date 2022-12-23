package com.artzvrzn.service;

import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.UserDto;
import java.util.List;

public interface SubscriptionService {

  List<UserDto> getUserSubscribers(Long userId);

  List<UserDto> getUserSubscriptions(Long userId);

  void followUser(SubscriptionDto dto);

  void unfollowUser(SubscriptionDto dto);
}
