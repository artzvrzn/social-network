package com.artzvrzn.mapper;

import com.artzvrzn.domain.Profile;
import com.artzvrzn.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(uses = {LocationEntityToDtoConverter.class, LocationDtoToEntityConverter.class})
public interface ProfileDtoToEntityConverter extends Converter<ProfileDto, Profile> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "subscriptions", ignore = true)
  @Mapping(target = "subscribers", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  Profile convert(ProfileDto source);
}
