package com.zymosy3.bioinf;

import java.util.function.Function;
import java.util.stream.IntStream;

/**
 *
 */
public class PatternMatchingApx implements Function<Genome, Function<Genome, Function<Integer, int[]>>> {
    @Override
    public Function<Genome, Function<Integer, int[]>> apply(Genome g) {
        return p -> d -> IntStream.range(0, g.size() - p.size() + 1).
                filter(i -> g.chunk(i, p.size()).hammingDistance(p) <= d).
                toArray();
    }
}
