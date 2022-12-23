package com.artzvrzn.service.impl;

import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.dao.UserRepository;
import com.artzvrzn.domain.Location;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.domain.User;
import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.UserDto;
import com.artzvrzn.mapper.Mapper;
import com.artzvrzn.service.SubscriptionService;
import com.artzvrzn.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.helpers.BasicMarkerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, readOnly = true)
@Log4j2
public class UserServiceImpl implements UserService, SubscriptionService {
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final Mapper mapper;

  @Override
  @Transactional
  public void createUser(UserDto dto) {
    User entity = mapper.map(dto, User.class);
    userRepository.save(entity);
  }

  @Override
  public UserDto getUser(Long userId) {
    User entity = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    return mapper.map(entity, UserDto.class);
  }

  @Override
  public List<UserDto> getAllUsers() {
    List<User> entities = userRepository.findAll();
    return entities.stream()
      .map(e -> mapper.map(e, UserDto.class))
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateUser(Long userId, UserDto dto) {
    User entity = userRepository.getReferenceById(userId);
    entity.setName(dto.getName());
    entity.setMiddleName(dto.getMiddleName());
    entity.setFamilyName(dto.getFamilyName());
    entity.setFullName(dto.getFullName());
    entity.setUsername(dto.getUsername());
    entity.setEmail(dto.getEmail());
    entity.setLocation(mapper.map(dto.getLocation(), Location.class));
    entity.setBirthDate(dto.getBirthDate());
    userRepository.save(entity);
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public List<UserDto> getUserSubscribers(Long userId) {
    User userEntity = userRepository.getReferenceById(userId);
    log.info("QUERY SUBSCRIBERS");
//    return userRepository.findAllSubscribers(userId).stream()
//      .map(s -> s.getSubscriber())
//    return userEntity.getSubscribers().stream()
//      .map(s -> s.getSubscriber())
//      .map(e -> mapper.map(e, UserDto.class))
//      .collect(Collectors.toList());
    return userRepository.findAllSubscribers(userId).stream()
      .map(e -> mapper.map(e, UserDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUserSubscriptions(Long userId) {
    log.info("QUERY SUBSCRIPTIONS");
    return userRepository.findAllSubscriptions(userId).stream()
//      .map(s -> s.getTargetUser())
      .map(e -> mapper.map(e, UserDto.class))
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
  @Transactional
  public void unfollowUser(SubscriptionDto dto) {
    SubscriptionId id = new SubscriptionId(dto.getTargetUserId(), dto.getSubscriberId());
    subscriptionRepository.deleteById(id);
  }
}
