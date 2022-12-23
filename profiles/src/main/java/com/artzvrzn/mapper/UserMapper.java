package com.artzvrzn.mapper;

import com.artzvrzn.domain.Location;
import com.artzvrzn.domain.User;
import com.artzvrzn.dto.LocationDto;
import com.artzvrzn.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

  UserDto map(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "subscriptions", ignore = true)
  @Mapping(target = "subscribers", ignore = true)
  User map(UserDto user);

  Location map(LocationDto location);

  LocationDto map(Location location);
}
