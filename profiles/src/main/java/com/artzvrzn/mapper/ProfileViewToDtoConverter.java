package com.artzvrzn.mapper;

import com.artzvrzn.dto.ProfileDto;
import com.artzvrzn.model.projection.ProfileView;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper()
public interface ProfileViewToDtoConverter extends Converter<ProfileView, ProfileDto> {

  ProfileDto convert(ProfileView source);
}
