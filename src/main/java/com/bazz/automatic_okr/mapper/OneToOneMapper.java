package com.bazz.automatic_okr.mapper;

public interface OneToOneMapper<T, S>{

    public S map(T t);

    public T reverseMap (S s);
}
