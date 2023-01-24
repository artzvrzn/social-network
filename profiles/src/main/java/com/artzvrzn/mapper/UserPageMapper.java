package com.artzvrzn.mapper;

import com.artzvrzn.domain.User;
import com.artzvrzn.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class)
public interface UserPageMapper extends PageMapper<User, UserDto> {

}
