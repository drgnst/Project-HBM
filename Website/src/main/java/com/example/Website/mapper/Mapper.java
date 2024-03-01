package com.example.Website.mapper;

public interface Mapper<A,B> {

    B mapTo(A a);

    A mapFrom(B b);
}
