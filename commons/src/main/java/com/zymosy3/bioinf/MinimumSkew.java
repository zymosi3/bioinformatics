package com.zymosy3.bioinf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 *
 */
public class MinimumSkew implements Function<Genome, int[]> {

    @Override
    public int[] apply(Genome g) {
        int[] skews = g.skew.get();
        int min = IntStream.of(skews).min().orElse(Integer.MIN_VALUE);
        return IntStream.range(0, skews.length).filter(i -> skews[i] == min).map(i -> i + 1).toArray();
    }
}
