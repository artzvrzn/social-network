package com.artzvrzn.service.impl;

import com.artzvrzn.dao.SubscriptionRepository;
import com.artzvrzn.dao.UserRepository;
import com.artzvrzn.domain.Subscription;
import com.artzvrzn.domain.Subscription.SubscriptionId;
import com.artzvrzn.domain.User;
import com.artzvrzn.dto.PageDto;
import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.UserDto;
import com.artzvrzn.mapper.PageMapper;
import com.artzvrzn.mapper.UserMapper;
import com.artzvrzn.mapper.UserPageMapper;
import com.artzvrzn.service.SubscriptionService;
import com.artzvrzn.service.UserService;
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
public class ProfileService implements UserService, SubscriptionService {
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final UserMapper userMapper;
  private final UserPageMapper pageMapper;

  @Override
  @Transactional
  public void createUser(UserDto dto) {
    User entity = userMapper.map(dto);
    userRepository.save(entity);
  }

  @Override
  public UserDto getUser(Long userId) {
    User entity = userRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));
    return userMapper.map(entity);
  }

  @Override
  public PageDto<UserDto> getAllUsers(int page, int size) {
    Pageable pageable = buildPageable(page, size);
    Page<User> entities = userRepository.findAll(pageable);
    return pageMapper.map(entities);
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
    entity.setLocation(userMapper.map(dto.getLocation()));
    entity.setBirthDate(dto.getBirthDate());
    entity.setImageSmall(dto.getImageSmall());
    entity.setImageLarge(dto.getImageLarge());
    userRepository.save(entity);
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public Page<UserDto> getUserSubscribers(Long userId, int page, int size) {
    Pageable pageable = buildPageable(page, size);
    return subscriptionRepository.getAllSubscribers(userId, pageable)
      .map(Subscription::getSubscriber)
      .map(userMapper::map);
  }

  @Override
  public Page<UserDto> getUserSubscriptions(Long userId, int page, int size) {
    Pageable pageable = buildPageable(page, size);
    return subscriptionRepository.getAllSubscriptions(userId, pageable)
      .map(Subscription::getTargetUser)
      .map(userMapper::map);
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

  private Pageable buildPageable(int page, int size) {
    return PageRequest.of(page - 1, size);
  }
}
