package com.bazz.automatic_okr.mapper;

public interface TwoToOneMapper <T, V, S>{

    public S map(T t, V v);
}
