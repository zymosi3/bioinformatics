package com.zymosy3.bioinf;

import java.util.function.Function;
import java.util.stream.IntStream;

/**
 *
 */
public class HammingDistance implements Function<Genome, Function<Genome, Integer>> {

    @Override
    public Function<Genome, Integer> apply(Genome g1) {
        return g2 -> IntStream.range(0, g1.s.length()).
                map(i -> g1.s.charAt(i) == g2.s.charAt(i) ? 0 : 1).
                sum();
    }
}
