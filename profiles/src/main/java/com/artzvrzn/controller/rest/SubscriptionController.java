package com.artzvrzn.controller.rest;

import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @GetMapping(
    value = "/subscribers/profile_id/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<ProfileDto> getSubscribers(
    @PathVariable("profileId") Long profileId,
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return subscriptionService.getSubscribers(profileId, page, size);
  }

  @GetMapping(
    value = "/subscribers/user_id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<ProfileDto> getSubscribers(
    @PathVariable("userId") String userId,
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return subscriptionService.getSubscribers(userId, page, size);
  }

  @GetMapping(
    value = "/subscriptions/profile_id/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<ProfileDto> getSubscriptions(
    @PathVariable("profileId") Long profileId,
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return subscriptionService.getSubscriptions(profileId, page, size);
  }

  @GetMapping(
    value = "/subscriptions/user_id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Page<ProfileDto> getSubscriptions(
    @PathVariable("userId") String userId,
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return subscriptionService.getSubscriptions(userId, page, size);
  }

  @PostMapping(value = "/subscription/follow/{id}")
  public void follow(@PathVariable("id") Long id) {
    subscriptionService.follow(id);
  }

  @DeleteMapping(value = "/subscription/unfollow/{id}")
  public void unfollow(@PathVariable("id") Long id) {
    subscriptionService.unfollow(id);
  }
}
