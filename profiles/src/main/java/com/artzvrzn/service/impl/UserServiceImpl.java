package com.artzvrzn.service.impl;

import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.dao.UserRepository;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.domain.User;
import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.UserDto;
import com.artzvrzn.service.SubscriptionService;
import com.artzvrzn.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, SubscriptionService {
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;

  @Override
  public void createUser(UserDto dto) {
    User entity = User.builder()
      .username(dto.getUsername())
      .build();
    userRepository.save(entity);
  }

  @Override
  public UserDto getUser(Long userId) {
    User entity = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    return new UserDto(entity.getId(), entity.getUsername());
  }

  @Override
  public List<UserDto> getAllUsers() {
    List<User> entities = userRepository.findAll();
    return entities.stream().map(e -> new UserDto(e.getId(), e.getUsername())).collect(
      Collectors.toList());
  }

  @Override
  public List<UserDto> getUserFollowers(Long userId) {
    User userEntity = userRepository.getReferenceById(userId);
    return userEntity.getSubscribers().stream()
      .map(s -> s.getSubscriber())
      .map(u -> new UserDto(u.getId(), u.getUsername()))
      .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUserFollowings(Long userId) {
    User userEntity = userRepository.getReferenceById(userId);
    return userEntity.getSubscriptions().stream()
      .map(s -> s.getTargetUser())
      .map(u -> new UserDto(u.getId(), u.getUsername()))
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void followUser(SubscriptionDto dto) {
    User targetUserId = userRepository.getReferenceById(dto.getTargetUserId());
    User subscriberId = userRepository.getReferenceById(dto.getSubscriberId());
    Subscription subscriptionEntity = Subscription.builder()
      .id(new SubscriptionId(targetUserId.getId(), subscriberId.getId()))
      .targetUser(targetUserId)
      .subscriber(subscriberId)
      .build();
    subscriptionRepository.save(subscriptionEntity);
  }

  @Override
  public void unfollowUser(SubscriptionDto dto) {
//    User followedEntity = userRepository.getReferenceById(dto.getFollowedId());
//    User followerEntity = userRepository.getReferenceById(dto.getFollowerId());
    SubscriptionId id = new SubscriptionId(dto.getTargetUserId(), dto.getSubscriberId());
    subscriptionRepository.deleteById(id);
  }
}
