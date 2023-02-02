package com.artzvrzn.mapper;

import com.artzvrzn.domain.Location;
import com.artzvrzn.dto.LocationDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface LocationDtoToEntityConverter extends Converter<LocationDto, Location> {

  Location convert(LocationDto source);
}
