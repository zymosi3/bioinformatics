package com.zymosy3.bioinf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
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

    public final Function<Genome, Integer> patternCount = new PatternCount().apply(this);

    public final Function<Genome, Function<Integer, Integer>> patternCountApx = new PatternCountApx().apply(this);

    public final Supplier<int[]> skew = () -> new Skew().apply(this);

    public final Supplier<int[]> minimumSkew = () -> new MinimumSkew().apply(this);

    public final Function<Genome, Integer> hammingDistance = new HammingDistance().apply(this);

    public final Function<Genome, Function<Integer, int[]>> patternMatchingApx =
            new PatternMatchingApx().apply(this);

    public final Function<Integer, Set<Genome>> frequentWordsBySorting = new FrequentWordsBySorting().apply(this);

    public final Function<Integer, Set<Genome>> neighbors = new Neighbors().apply(this);

    public Stream<Symbol> stream() {
        return s.chars().mapToObj(Symbol::byName);
    }

    public int size() {
        return s.length();
    }

    public Symbol at(int i) {
        return Symbol.byName(s.charAt(i));
    }

    public Symbol first() {
        return at(0);
    }

    public Symbol last() {
        return at(size() - 1);
    }

    public Genome chunk(int i, int len) {
        return new Genome(s.substring(i, i + len));
    }

    public Genome suffix() {
        return chunk(1, size() - 1);
    }

    public Genome addFirst(Symbol s) {
        return new Genome(s.name() + this.s);
    }

    public int hammingDistance(Genome g) {
        return hammingDistance.apply(g);
    }

    public int[] patternMatchingApx(Genome pattern, int d) {
        return patternMatchingApx.apply(pattern).apply(d);
    }

    public int patternCount(Genome pattern) {
        return patternCount.apply(pattern);
    }

    public int patternCount(String pattern) {
        return patternCount(new Genome(pattern));
    }

    public int patternCountApx(Genome pattern, int d) {
        return patternCountApx.apply(pattern).apply(d);
    }

    public int patternCountApx(String pattern, int d) {
        return patternCountApx(new Genome(pattern), d);
    }

    public Set<Genome> neighbors(int d) {
        return neighbors.apply(d);
    }

    public Set<Genome> frequentWords(int k) {
        return frequentWordsBySorting.apply(k);
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
        IntStream.range(0, s.length() - k + 1).
                mapToObj(i -> chunk(i, k)).
                map(Genome::toNumber).
                forEach(j -> frequencies[j.intValue()]++);
        return frequencies;
    }

    public Map<Long, Integer> computingFrequenciesMap(int k) {
        Map<Long, Integer> frequencies = new HashMap<>(s.length() - k + 1);
        IntStream.range(0, s.length() - k + 1).
                mapToObj(i -> chunk(i, k)).
                map(Genome::toNumber).
                forEach(number -> {
                    Integer count = frequencies.get(number);
                    frequencies.put(number, count == null ? 1 : (count + 1));
                });
        return frequencies;
    }

    public int[] count(int k) {
        return IntStream.range(0, s.length() - k + 1).
                mapToObj(i -> s.substring(i, i + k)).
                map(Genome::new).
                map(patternCount).
                mapToInt(i -> i).
                toArray();
    }

    public String complementary() {
        return IntStream.range(0, s.length()).
                mapToObj(i -> s.charAt(s.length() - i - 1)).
                map(Symbol::byName).
                map(Symbol::complementary).
                map(Symbol::name).
                collect(Collectors.joining());
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
            return Symbol.byValue((int) number).name();
        }
        long prefixNumber = number / 4;
        int reminder = (int) number % 4;
        Symbol symbol = Symbol.byValue(reminder);
        return numberToPattern(prefixNumber, k - 1) + symbol.name();
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
