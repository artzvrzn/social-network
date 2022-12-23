package com.artzvrzn.controller;

import com.artzvrzn.dto.UserDto;
import com.artzvrzn.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createUser(@RequestBody UserDto dto) {
    userService.createUser(dto);
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public UserDto getUser(@PathVariable("userId") Long userId) {
    return userService.getUser(userId);
  }
  @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void updateUser(@PathVariable("userId") Long userId, @RequestBody UserDto dto) {
    userService.updateUser(userId, dto);
  }

  @DeleteMapping(value = "/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUser(@PathVariable("userId") Long userId) {
    userService.deleteUser(userId);
  }
}
