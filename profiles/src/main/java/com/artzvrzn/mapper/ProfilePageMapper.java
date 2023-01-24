package com.artzvrzn.mapper;

import com.artzvrzn.domain.Profile;
import com.artzvrzn.dto.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(uses = ProfileMapper.class)
public interface ProfilePageMapper extends PageMapper<Profile, ProfileDto> {

}
