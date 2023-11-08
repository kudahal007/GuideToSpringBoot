package com.np.kd.GuideToSpringBoot.domain.mappers;

public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B a);
}
