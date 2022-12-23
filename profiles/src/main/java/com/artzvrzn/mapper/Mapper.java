package com.artzvrzn.mapper;

public interface Mapper {

    <T1, T2> T1 map(T2 source, Class<T1> target);
}
