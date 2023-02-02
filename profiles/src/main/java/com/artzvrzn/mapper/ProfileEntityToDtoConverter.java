package com.artzvrzn.mapper;

import com.artzvrzn.domain.Profile;
import com.artzvrzn.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(uses = {LocationEntityToDtoConverter.class, LocationDtoToEntityConverter.class})
public interface ProfileEntityToDtoConverter extends Converter<Profile, ProfileDto> {

  @Mapping(target = "subscribers", ignore = true)
  @Mapping(target = "subscriptions", ignore = true)
  ProfileDto convert(Profile source);
}
