package com.zymosy3.bioinf;

import java.util.function.Function;
import java.util.stream.IntStream;

/**
 *
 */
public class PatternCountApx implements Function<Genome, Function<Genome, Function<Integer, Integer>>> {

    @Override
    public Function<Genome, Function<Integer, Integer>> apply(Genome g) {
        return p -> d -> IntStream.range(0, g.size() - p.size() + 1).
                map(i -> g.chunk(i, p.size()).hammingDistance(p) <= d ? 1 : 0).
                sum();
    }
}
