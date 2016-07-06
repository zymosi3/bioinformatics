package com.zymosi3.bioinf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class Dna {

    private final List<Genome> genomes;

    public Dna(Genome... genomes) {
        this.genomes = Stream.of(genomes).collect(Collectors.toList());
    }

    public Dna(Collection<Genome> genomes) {
        this.genomes = Collections.unmodifiableList(new ArrayList<>(genomes));
    }

    public Stream<Genome> stream() {
        return genomes.stream();
    }

    //    MotifEnumeration(Dna, k, d)
    //        Patterns ← an empty set
    //        for each k-mer Pattern in Dna
    //            for each k-mer Pattern’ differing from Pattern by at most d mismatches
    //                if Pattern' appears in each string from Dna with at most d mismatches
    //                    add Pattern' to Patterns
    //        remove duplicates from Patterns
    //        return Patterns
    public Set<Genome> motifEnumeration(int k, int d) {
        return genomes.stream().
                flatMap(g -> g.kmerStream(k)).
                flatMap(p -> p.neighbors(d).stream()).
                map(n -> {
                    for (Genome genome : genomes) {
                        if (genome.patternCountApx(n, d) == 0)
                            return null;
                    }
                    return n;
                }).
                filter(n -> n != null).
                collect(Collectors.toSet());
    }

    public int distance(Genome p) {
        return stream().
                map(g -> g.kmerStream(p.size()).map(kmer -> kmer.hammingDistance(p)).mapToInt(i -> i).min().orElse(p.size())).
                mapToInt(i -> i).
                sum();
    }

    //    MedianString(Dna, k)
    //        distance ← ∞
    //        for each k-mer Pattern from AA…AA to TT…TT
    //            if distance > d(Pattern, Dna)
    //                distance ← d(Pattern, Dna)
    //            Median ← Pattern
    //        return Median
    public Genome medianString(int k) {
        return Genome.allKmerStream(k).
                map(p -> new Object[] {p, distance(p)}).
                min((o1, o2) -> ((Integer) o1[1]) - ((Integer) o2[1])).
                map(o -> (Genome) o[0]).
                orElse(null);
    }

    public Map<Nucleotide, List<Double>> profile(int k) {
        return IntStream.range(0, k).
                mapToObj(i -> genomes.stream().map(g -> g.at(i))).
                map(col -> col.collect(Collectors.toMap(
                        n -> n,
                        n -> 1.0,
                        (d1, d2) -> d1 + d2,
                        () -> Nucleotide.stream().collect(Collectors.toMap(n -> n, n -> 0.0))
                ))).
                map(m -> Nucleotide.stream().collect(Collectors.toMap(n -> n, n -> m.get(n) / genomes.size()))).
                reduce(
                        Nucleotide.stream().collect(Collectors.toMap(n -> n, n -> new ArrayList<Double>())),
                        (r, m) -> {
                            Nucleotide.stream().forEach(n -> r.get(n).add(m.get(n)));
                            return r;
                        },
                        (m1, m2) -> m2
                );
    }
}
