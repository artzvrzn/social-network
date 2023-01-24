package com.artzvrzn.mapper;

import com.artzvrzn.domain.Location;
import com.artzvrzn.domain.Profile;
import com.artzvrzn.dto.LocationDto;
import com.artzvrzn.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProfileMapper {

  ProfileDto map(Profile profile);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "subscriptions", ignore = true)
  @Mapping(target = "subscribers", ignore = true)
  Profile map(ProfileDto profile);

  Location map(LocationDto location);

  LocationDto map(Location location);
}
