package com.artzvrzn.mapper;

import com.artzvrzn.domain.Location;
import com.artzvrzn.dto.LocationDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface LocationEntityToDtoConverter extends Converter<Location, LocationDto> {

  LocationDto convert(Location source);
}
