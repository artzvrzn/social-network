package com.artzvrzn.controller;

import com.artzvrzn.dto.SubscriptionDto;
import com.artzvrzn.dto.UserDto;
import com.artzvrzn.service.SubscriptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @GetMapping(value = "/followers/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<UserDto> getUserFollowers(
    @PathVariable("userId") Long userId,
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return subscriptionService.getUserSubscribers(userId, page, size);
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<UserDto> getUserFollowings(
    @PathVariable("userId") Long userId,
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return subscriptionService.getUserSubscriptions(userId, page, size);
  }

  @PostMapping(value = "/follow", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void followUser(@RequestBody SubscriptionDto dto) {
    subscriptionService.followUser(dto);
  }

  @DeleteMapping(value = "/unfollow", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void unfollowUser(@RequestBody SubscriptionDto dto) {
    subscriptionService.unfollowUser(dto);
  }
}