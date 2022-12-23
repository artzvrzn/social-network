package com.artzvrzn.service;

import com.artzvrzn.dto.UserDto;
import java.util.List;

public interface UserService {

  void createUser(UserDto dto);

  UserDto getUser(Long userId);

  List<UserDto> getAllUsers();

}
