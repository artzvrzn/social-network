package com.artzvrzn.mapper;

import com.artzvrzn.dto.PageDto;
import org.springframework.data.domain.Page;

public interface PageMapper<E, D> {

  PageDto<D> map(Page<E> page);
}
