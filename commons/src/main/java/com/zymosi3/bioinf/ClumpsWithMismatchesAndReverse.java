package com.zymosi3.bioinf;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class ClumpsWithMismatchesAndReverse implements
        Function<Genome,
                Function<Integer,
                        Function<Integer,
                                Function<Integer,
                                        Function<Integer, Set<Genome>>
                                        >
                                >
                        >
                > {
    @Override
    public Function<Integer, Function<Integer, Function<Integer, Function<Integer, Set<Genome>>>>> apply(Genome g) {
        return k -> l -> t -> d ->
            IntStream.range(0, g.size() - l + 1).
                    mapToObj(i -> g.chunk(i, l)).
                    map(chunk -> {
                        List<Genome> neighborhoods = chunk.kmerStream(k).
                                flatMap(pattern -> Stream.of(pattern, pattern.complementary())).
                                flatMap(p -> p.neighbors(d).stream()).
                                collect(Collectors.toList());
                        long[] index = IntStream.range(0, neighborhoods.size()).
                                mapToLong(i -> neighborhoods.get(i).toNumber()).
                                sorted().
                                toArray();
                        int[] count = new int[neighborhoods.size()];
                        Arrays.setAll(count, i -> 1);
                        IntStream.range(1, neighborhoods.size()).
                                filter(i -> index[i] == index[i - 1]).
                                forEach(i -> count[i] = count[i - 1] + 1);
                        return IntStream.range(0, neighborhoods.size()).
                                filter(i -> count[i] >= t).
                                mapToObj(i -> Genome.numberToPattern(index[i], k)).
                                map(Genome::new).
                                collect(Collectors.toSet());
                    }).
                    flatMap(Collection::stream).
                    collect(Collectors.toSet());

    }
}
