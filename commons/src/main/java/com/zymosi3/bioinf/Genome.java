package com.zymosi3.bioinf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class Genome {

    public final String s;

    public Genome(String s) {
        this.s = Objects.requireNonNull(s);
    }

    private static final Function<Genome, int[]> skew = g -> g.stream().
            map(s -> {
                switch (s) {
                    case G:
                        return 1;
                    case C:
                        return -1;
                    default:
                        return 0;
                }
            }).
            reduce(
                    new ArrayList<Integer>(g.s.length()),
                    (l, i) -> { if (l.isEmpty()) l.add(i); else l.add(l.get(l.size() - 1) + i); return l; },
                    (l1, l2) -> l1
            ).
            stream().
            mapToInt(i -> i).
            toArray();

    private static final Function<Genome, Function<Genome, Integer>> hammingDistance = g1 -> g2 ->
            IntStream.range(0, g1.s.length()).
                    map(i -> g1.s.charAt(i) == g2.s.charAt(i) ? 0 : 1).
                    sum();

    public static final Function<Genome, int[]> minimumSkew = g -> {
        int[] skews = g.skew();
        int min = IntStream.of(skews).min().orElse(Integer.MIN_VALUE);
        return IntStream.range(0, skews.length).filter(i -> skews[i] == min).map(i -> i + 1).toArray();
    };

    public static final Function<Genome, Function<Genome, Function<Integer, int[]>>> patternMatchingApx =
            g -> p -> d -> IntStream.range(0, g.size() - p.size() + 1).
                    filter(i -> g.chunk(i, p.size()).hammingDistance(p) <= d).
                    toArray();

    public static final Function<Genome, Function<Genome, Integer>> patternCount = g -> p ->
            IntStream.range(0, g.size() - p.size() + 1).
                    map(i -> g.chunk(i, p.size()).equals(p) ? 1 : 0).
                    sum();;

    public static final Function<Genome, Function<Genome, Function<Integer, Integer>>> patternCountApx =
            g -> p -> d -> g.kmerStream(p.size()).
                    mapToInt(kmer -> kmer.hammingDistance(p) <= d ? 1 : 0).
                    sum();

    public final Function<Integer, Set<Genome>> frequentWordsBySorting = new FrequentWordsBySorting().apply(this);

    //    FrequentWordsWithMismatches(Text, k, d)
    //        FrequentPatterns ← an empty set
    //        Neighborhoods ← an empty list
    //        for i ← 0 to |Text| − k
    //            add Neighbors(Text(i, k), d) to Neighborhoods
    //        form an array NeighborhoodArray holding all strings in Neighborhoods
    //        for i ← 0 to |Neighborhoods| − 1
    //            Pattern ← NeighborhoodArray(i)
    //            Index(i) ← PatternToNumber(Pattern)
    //            Count(i) ← 1
    //        SortedIndex ← Sort(Index)
    //        for i ← 0 to |Neighborhoods| − 1
    //            if SortedIndex(i) = SortedIndex(i + 1)
    //                Count(i + 1) ← Count(i) + 1
    //        maxCount ← maximum value in array Count
    //        for i ← 0 to |Neighborhoods| − 1
    //            if Count(i) = maxCount
    //                Pattern ← NumberToPattern(SortedIndex(i), k)
    //                add Pattern to FrequentPatterns
    //        return FrequentPatterns
    public static final Function<Genome, Function<Integer, Function<Integer, Set<Genome>>>> frequentWordsWithMismatches =
            g -> k -> d -> {
                List<Genome> neighborhoods = g.kmerStream(k).
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
                int maxCount = IntStream.of(count).max().orElse(Integer.MIN_VALUE);
                return IntStream.range(0, neighborhoods.size()).
                        filter(i -> count[i] == maxCount).
                        mapToObj(i -> Genome.numberToPattern(index[i], k)).
                        map(Genome::new).
                        collect(Collectors.toSet());
            };

    public static final Function<Genome, Function<Integer, Function<Integer, Set<Genome>>>> frequentWordsWithMismatchesAndReverse =
            g -> k -> d -> {
                List<Genome> neighborhoods = g.kmerStream(k).
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
                int maxCount = IntStream.of(count).max().orElse(Integer.MIN_VALUE);
                return IntStream.range(0, neighborhoods.size()).
                        filter(i -> count[i] == maxCount).
                        mapToObj(i -> Genome.numberToPattern(index[i], k)).
                        map(Genome::new).
                        collect(Collectors.toSet());
            };

    //    Neighbors(Pattern, d)
    //        if d = 0
    //            return {Pattern}
    //        if |Pattern| = 1
    //            return {A, C, G, T}
    //        Neighborhood ← an empty set
    //        SuffixNeighbors ← Neighbors(Suffix(Pattern), d)
    //        for each string Text from SuffixNeighbors
    //            if HammingDistance(Suffix(Pattern), Text) < d
    //                for each nucleotide x
    //                    add x • Text to Neighborhood
    //            else
    //                add FirstSymbol(Pattern) • Text to Neighborhood
    //        return Neighborhood
    public static final Function<Genome, Function<Integer, Set<Genome>>> neighbors =
            g -> d -> {
                if (d == 0) {
                    return Collections.singleton(g);
                }
                if (g.size() == 1) {
                    return Stream.of(Nucleotide.values()).
                            map(Nucleotide::name).
                            map(Genome::new).
                            collect(Collectors.toSet());
                }
                return g.suffix().neighbors(d).stream().
                        flatMap(n -> g.suffix().hammingDistance(n) < d ?
                                Stream.of(Nucleotide.values()).map(n::addFirst) :
                                Stream.of(n.addFirst(g.first()))).
                        collect(Collectors.toSet());
            };


    public Stream<Nucleotide> stream() {
        return s.chars().mapToObj(Nucleotide::byName);
    }

    public Stream<Genome> kmerStream(int k) {
        return IntStream.range(0, s.length() - k + 1).
                mapToObj(i -> chunk(i, k));
    }

    public static Stream<Genome> allKmerStream(int k) {
        return IntStream.range(0, (int) Math.pow(4, k)).
                mapToObj(i -> numberToPattern(i, k)).
                map(Genome::new);
    }

    public int size() {
        return s.length();
    }

    public Nucleotide at(int i) {
        return Nucleotide.byName(s.charAt(i));
    }

    public Nucleotide first() {
        return at(0);
    }

    public Nucleotide last() {
        return at(size() - 1);
    }

    public Genome chunk(int i, int len) {
        return new Genome(s.substring(i, i + len));
    }

    public Genome suffix() {
        return chunk(1, size() - 1);
    }

    public Genome addFirst(Nucleotide s) {
        return new Genome(s.name() + this.s);
    }

    public int[] skew() {
        return skew.apply(this);
    }

    public int[] minimumSkew() {
        return minimumSkew.apply(this);
    }

    public int hammingDistance(Genome g) {
        return hammingDistance.apply(this).apply(g);
    }

    public int[] patternMatchingApx(Genome pattern, int d) {
        return patternMatchingApx.apply(this).apply(pattern).apply(d);
    }

    public int patternCount(Genome pattern) {
        return patternCount.apply(this).apply(pattern);
    }

    public int patternCount(String pattern) {
        return patternCount(new Genome(pattern));
    }

    public int patternCountApx(Genome pattern, int d) {
        return patternCountApx.apply(this).apply(pattern).apply(d);
    }

    public int patternCountApx(String pattern, int d) {
        return patternCountApx(new Genome(pattern), d);
    }

    public Set<Genome> neighbors(int d) {
        return neighbors.apply(this).apply(d);
    }

    public Set<Genome> frequentWords(int k) {
        return frequentWordsBySorting.apply(k);
    }

    public Set<Genome> frequentWordsWithMismatches(int k, int d) {
        return frequentWordsWithMismatches.apply(this).apply(k).apply(d);
    }

    public Set<Genome> frequentWordsWithMismatchesAndReverse(int k, int d) {
        return frequentWordsWithMismatchesAndReverse.apply(this).apply(k).apply(d);
    }

    //    ComputingFrequencies(Text , k)
    //        for i ← 0 to 4k − 1
    //            FrequencyArray(i) ← 0
    //        for i ← 0 to |Text| − k
    //            Pattern ← Text(i, k)
    //            j ← PatternToNumber(Pattern)
    //            FrequencyArray(j) ← FrequencyArray(j) + 1
    //        return FrequencyArray
    public int[] computingFrequencies(int k) {
        int[] frequencies = new int[(int) Math.pow(4, k)];
        kmerStream(k).
                map(Genome::toNumber).
                forEach(j -> frequencies[j.intValue()]++);
        return frequencies;
    }

    public Map<Long, Integer> computingFrequenciesMap(int k) {
        Map<Long, Integer> frequencies = new HashMap<>(s.length() - k + 1);
        kmerStream(k).
                map(Genome::toNumber).
                forEach(number -> {
                    Integer count = frequencies.get(number);
                    frequencies.put(number, count == null ? 1 : (count + 1));
                });
        return frequencies;
    }

    public Genome complementary() {
        return new Genome(IntStream.range(0, s.length()).
                mapToObj(i -> s.charAt(s.length() - i - 1)).
                map(Nucleotide::byName).
                map(Nucleotide::complementary).
                map(Nucleotide::name).
                collect(Collectors.joining()));
    }

    public int[] patternMatching(String pattern) {
        return IntStream.range(0, s.length() - pattern.length() + 1).
                map(i -> s.substring(i, i + pattern.length()).equals(pattern) ? i : -1).
                filter(i -> i >= 0).
                toArray();
    }

    public Set<String> clumps(int k, int l, int t) {
        return IntStream.range(0, s.length() - l + 1).parallel().
                mapToObj(i -> new Genome(s.substring(i, i + l))).
                map(dna -> dna.computingFrequencies(k)).
                map(frequencies -> IntStream.range(0, frequencies.length).
                        filter(i -> frequencies[i] >= t).
                        mapToObj(i -> numberToPattern(i, k)).
                        collect(Collectors.toSet())
                ).
                reduce(new HashSet<>(), (res, fromWindow) -> { res.addAll(fromWindow); return res; });
    }

    public Set<String> fasterClumpsOnMaps(int k, int l, int t) {
        Map<Long, Integer> computingFrequencies = new HashMap<>();
        return IntStream.range(0, s.length() - l + 1).// now we can't parallel this
                mapToObj(i -> {
            Genome genome = new Genome(s.substring(i, i + l));
            if (computingFrequencies.isEmpty()) {
                computingFrequencies.putAll(genome.computingFrequenciesMap(k));
            } else {
                long removed = chunk(i - 1, k).toNumber();
                int removedCount = computingFrequencies.get(removed);
                if (removedCount == 1) {
                    computingFrequencies.remove(removed);
                } else {
                    computingFrequencies.put(removed, removedCount - 1);
                }
                long added = chunk(i + l - k, k).toNumber();
                Integer addedCount = computingFrequencies.get(added);
                computingFrequencies.put(added, addedCount == null ? 1 : (addedCount + 1));
            }
            return computingFrequencies;
        }).
                map(frequencies -> frequencies.keySet().stream().
                        filter(number -> frequencies.get(number) >= t).
                        map(number -> numberToPattern(number, k)).
                        collect(Collectors.toSet())
                ).
                reduce(new HashSet<>(), (res, fromWindow) -> { res.addAll(fromWindow); return res; });
    }

    public Set<Genome> clumpsWithMismatchesAndReverse(int k, int l, int t, int d) {
        return new ClumpsWithMismatchesAndReverse().apply(this).apply(k).apply(l).apply(t).apply(d);
    }

    //    PatternToNumber(Pattern)
    //        if Pattern contains no symbols
    //            return 0
    //        symbol ← LastSymbol(Pattern)
    //        Prefix ← Prefix(Pattern)
    //        return 4 · PatternToNumber(Prefix) + SymbolToNumber(symbol)
    public long toNumber() {
        switch (size()) {
            case 0:
                return 0;
            case 1:
                return last().value;
            default:
                return last().value + 4 * chunk(0, size() - 1).toNumber();
        }
    }

    public static String numberToPattern(long number, int k) {
        if (k == 1) {
            return Nucleotide.byValue((int) number).name();
        }
        long prefixNumber = number / 4;
        int reminder = (int) number % 4;
        Nucleotide nucleotide = Nucleotide.byValue(reminder);
        return numberToPattern(prefixNumber, k - 1) + nucleotide.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genome genome = (Genome) o;

        return s.equals(genome.s);

    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public String toString() {
        return s;
    }

}
