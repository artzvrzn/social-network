package com.artzvrzn.controller.rest;

import com.artzvrzn.dao.ProfileRepository;
import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileController {
  private final ProfileService profileService;
  private final ProfileRepository repository;

  @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createProfile(@RequestBody ProfileDto dto) {
    profileService.createProfile(dto);
  }

  @GetMapping(value = "/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ProfileDto getProfile(@PathVariable("profileId") Long profileId) {
    return profileService.getProfile(profileId);
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ProfileDto getProfile() {
    return profileService.getProfile();
  }

  @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Page<ProfileDto> getAllUsers(
    @RequestParam("page") int page,
    @RequestParam("size") int size
  ) {
    return profileService.getProfiles(page, size);
  }

  @PutMapping(value = "/{profileId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void updateProfile(@PathVariable("profileId") Long profileId, @RequestBody ProfileDto dto) {
    profileService.updateProfile(profileId, dto);
  }

  @DeleteMapping(value = "/{profileId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteProfile(@PathVariable("profileId") Long profileId) {
    profileService.deleteProfile(profileId);
  }
}
