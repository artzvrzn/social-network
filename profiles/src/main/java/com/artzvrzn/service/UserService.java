package com.artzvrzn.service;

import com.artzvrzn.dto.UserDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface UserService {

  void createUser(UserDto dto);

  UserDto getUser(Long userId);

  Page<UserDto> getAllUsers(int page, int size);

  void updateUser(Long userId, UserDto dto);

  void deleteUser(Long userId);
}
