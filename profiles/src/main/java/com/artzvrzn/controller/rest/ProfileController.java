package com.artzvrzn.controller.rest;

import com.artzvrzn.dto.PageDto;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileController {
  private final ProfileService profileService;

  @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createUser(@RequestBody ProfileDto dto) {
    profileService.createProfile(dto);
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ProfileDto getUser(@PathVariable("userId") Long userId) {
    return profileService.getProfile(userId);
  }
  @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public PageDto<ProfileDto> getAllUsers(
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return profileService.getProfiles(page, size);
  }

  @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void updateUser(@PathVariable("userId") Long userId, @RequestBody ProfileDto dto) {
    profileService.updateProfile(userId, dto);
  }

  @DeleteMapping(value = "/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUser(@PathVariable("userId") Long userId) {
    profileService.deleteProfile(userId);
  }
}
