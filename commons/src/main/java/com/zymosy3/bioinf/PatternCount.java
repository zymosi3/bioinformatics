package com.zymosy3.bioinf;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Genome(Text, Pattern)
 *    count ← 0
 *    for i ← 0 to |Text| − |Pattern|
 *        if Text(i, |Pattern|) = Pattern
 *            count ← count + 1
 *    return count
 */
public class PatternCount implements Function<Genome, Function<Genome, Integer>>{

    @Override
    public Function<Genome, Integer> apply(Genome g) {
        return pattern ->
                IntStream.range(0, g.size() - pattern.size() + 1).
                        map(i -> g.chunk(i, pattern.size()).equals(pattern) ? 1 : 0).
                        sum();
    }
}
