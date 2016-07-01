package com.zymosy3.bioinf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class FrequentWordsBySorting implements Function<Genome, Function<Integer, Set<Genome>>> {

    //    FindingFrequentWordsBySorting(Text , k)
    //        FrequentPatterns ← an empty set
    //        for i ← 0 to |Text| − k
    //            Pattern ← Text(i, k)
    //            Index(i) ← PatternToNumber(Pattern)
    //            Count(i) ← 1
    //        SortedIndex ← Sort(Index)
    //        for i ← 1 to |Text| − k
    //            if SortedIndex(i) = SortedIndex(i − 1)
    //                Count(i) = Count(i − 1) + 1
    //        maxCount ← maximum value in the array Count
    //        for i ← 0 to |Text| − k
    //            if Count(i) = maxCount
    //                Pattern ← NumberToPattern(SortedIndex(i), k)
    //                add Pattern to the set FrequentPatterns
    //        return FrequentPatterns
    @Override
    public Function<Integer, Set<Genome>> apply(Genome g) {
        return k -> {
            long[] index = IntStream.range(0, g.size() - k + 1).
                    mapToLong(i -> g.chunk(i, k). toNumber()).
                    sorted().toArray();
            int[] count = new int[g.size() - k + 1];
            Arrays.setAll(count, i -> 1);
            IntStream.range(1, g.size() - k + 1).
                    filter(i -> index[i] == index[i - 1]).
                    forEach(i -> count[i] = count[i - 1] + 1);
            int maxCount = IntStream.of(count).max().orElse(Integer.MIN_VALUE);
            return IntStream.range(0, g.size() - k + 1).
                    filter(i -> count[i] == maxCount).
                    mapToObj(i -> Genome.numberToPattern(index[i], k)).
                    map(Genome::new).
                    collect(Collectors.toSet());
        };
    }
}
