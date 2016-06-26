package com.zymosy3.bioinf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class Genome {

    public final String s;

    public Genome(String s) {
        this.s = s;
    }

    //    Genome(Text, Pattern)
//    count ← 0
//            for i ← 0 to |Text| − |Pattern|
//            if Text(i, |Pattern|) = Pattern
//    count ← count + 1
//            return count
    public int count(String pattern) {
        return IntStream.range(0, s.length() - pattern.length() + 1).
                map(i -> s.substring(i, i + pattern.length()).equals(pattern) ? 1 : 0).
                sum();
    }

    //    FrequentWords(Text, k)
//        FrequentPatterns ← an empty set
//        for i ← 0 to |Text| − k
//            Pattern ← the k-mer Text(i, k)
//            Count(i) ← Genome(Text, Pattern)
//        maxCount ← maximum value in array Count
//        for i ← 0 to |Text| − k
//            if Count(i) = maxCount
//                add Text(i, k) to FrequentPatterns
//        remove duplicates from FrequentPatterns
//        return FrequentPatterns
    public Set<String> frequentWords(int k) {
        int[] count = count(k);
        int max = IntStream.of(count).max().orElse(Integer.MIN_VALUE);
        return IntStream.range(0, count.length - k + 1).
                filter(i -> count[i] == max).
                mapToObj(i -> s.substring(i, i + k)).
                collect(Collectors.toSet());
    }

    //    FasterFrequentWords(Text , k)
//        FrequentPatterns ← an empty set
//        FrequencyArray ← ComputingFrequencies(Text, k)
//        maxCount ← maximal value in FrequencyArray
//        for i ←0 to 4k − 1
//            if FrequencyArray(i) = maxCount
//                Pattern ← NumberToPattern(i, k)
//                add Pattern to the set FrequentPatterns
//        return FrequentPatterns
    public Set<String> fasterFrequentWords(int k) {
        int[] frequencyArray = computingFrequencies(k);
        int max = IntStream.of(frequencyArray).max().orElse(Integer.MIN_VALUE);
        return IntStream.range(0, frequencyArray.length).
                filter(i -> frequencyArray[i] == max).
                mapToObj(i -> numberToPattern(i, k)).
                collect(Collectors.toSet());
    }

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
    public Collection<String> findingFrequentWordsBySorting(int k) {
        return null;
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
                mapToObj(i -> s.substring(i, i + k)).
                map(Genome::patternToNumber).
                forEach(j -> frequencies[j.intValue()]++);
        return frequencies;
    }

    public Map<Long, Integer> computingFrequenciesMap(int k) {
        Map<Long, Integer> frequencies = new HashMap<>(s.length() - k + 1);
        IntStream.range(0, s.length() - k + 1).
                mapToObj(i -> s.substring(i, i + k)).
                map(Genome::patternToNumber).
                forEach(number -> {
                    Integer count = frequencies.get(number);
                    frequencies.put(number, count == null ? 1 : (count + 1));
                });
        return frequencies;
    }

    public int[] count(int k) {
        int[] count = new int[s.length() - k];
        IntStream.range(0, count.length - k + 1).
                forEach(i -> count[i] = count(s.substring(i, i + k)));
        return count;
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

    public Set<String> fasterClumps(int k, int l, int t) {
        AtomicBoolean initFrequencies = new AtomicBoolean();
        AtomicReference<int[]> computingFrequencies = new AtomicReference<>();
        return IntStream.range(0, s.length() - l + 1).// now we can't parallel this
                mapToObj(i -> {
            Genome genome = new Genome(s.substring(i, i + l));
            if (!initFrequencies.get()) {
                computingFrequencies.set(genome.computingFrequencies(k));
                initFrequencies.set(true);
            } else {
                int removed = (int) patternToNumber(s.substring(i - 1, i + k - 1));
                computingFrequencies.get()[removed]--;
                int added = (int) patternToNumber(s.substring(i + l - k, i + l));
                computingFrequencies.get()[added]++;
            }
            return computingFrequencies.get();
        }).
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
                long removed = patternToNumber(s.substring(i - 1, i + k - 1));
                int removedCount = computingFrequencies.get(removed);
                if (removedCount == 1) {
                    computingFrequencies.remove(removed);
                } else {
                    computingFrequencies.put(removed, removedCount - 1);
                }
                long added = patternToNumber(s.substring(i + l - k, i + l));
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
    public static long patternToNumber(String pattern) {
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("Empty pattern");
        }

        Symbol lastSymbol = Symbol.byName(pattern.charAt(pattern.length() - 1));
        if (pattern.length() == 1) {
            return lastSymbol.value;
        }
        return 4 * patternToNumber(pattern.substring(0, pattern.length() - 1)) + lastSymbol.value;
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
    public String toString() {
        return s;
    }

}
