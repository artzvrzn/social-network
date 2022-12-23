package com.artzvrzn.mapper;

import com.artzvrzn.domain.Location;
import com.artzvrzn.domain.User;
import com.artzvrzn.dto.LocationDto;
import com.artzvrzn.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperImpl implements Mapper {
  private final UserMapper userMapper;

  @Override
  public <T1, T2> T1 map(T2 source, Class<T1> target) {
    if (User.class.equals(target)) {
      return (T1) userMapper.map((UserDto) source);
    }
    if (UserDto.class.equals(target)) {
      return (T1) userMapper.map((User) source);
    }
    if (Location.class.equals(target)) {
      return (T1) userMapper.map((LocationDto) source);
    }
    if (LocationDto.class.equals(target)) {
      return (T1) userMapper.map((Location) source);
    }
    throw new IllegalArgumentException(String.format("failed to convert {} to {}",
      source.getClass().getSimpleName(), target.getSimpleName()));
  }
}
