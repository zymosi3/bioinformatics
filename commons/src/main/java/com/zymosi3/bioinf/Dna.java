package com.zymosi3.bioinf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class Dna {

    private final List<Genome> genomes;

    public Dna(Genome... genomes) {
        this(Stream.of(genomes).collect(Collectors.toList()));
    }

    public Dna(List<Genome> genomes) {
        this.genomes = Collections.unmodifiableList(new ArrayList<>(genomes));
    }

    public Stream<Genome> stream() {
        return genomes.stream();
    }

    public int size() {
        return genomes.size();
    }

    public Genome at(int i) {
        return genomes.get(i);
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
                min((o1, o2) -> Integer.compare((Integer) o1[1], (Integer) o2[1])).
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

    public Genome consensus(int k) {
        Map<Nucleotide, List<Double>> profile = profile(k);
        return new Genome(IntStream.range(0, k).
                mapToObj(i ->
                        Nucleotide.stream().
                                map(n -> new Object[]{n, profile.get(n).get(i)}).
                                max((o1, o2) -> Double.compare((double) o1[1], (double) o2[1])).
                                map(a -> (Nucleotide) a[0]).
                                orElse(null)
                ).
                map(Enum::name).
                collect(Collectors.joining()));
    }

    public double score(int k) {
        return distance(consensus(k));
    }

    //    GreedyMotifSearch(Dna, k, t)
    //        BestMotifs ← motif matrix formed by first k-mers in each string from Dna
    //        for each k-mer Motif in the first string from Dna
    //            Motif1 ← Motif
    //            for i = 2 to t
    //                form Profile from motifs Motif1, …, Motifi - 1
    //                Motifi ← Profile-most probable k-mer in the i-th string in Dna
    //            Motifs ← (Motif1, …, Motift)
    //            if Score(Motifs) < Score(BestMotifs)
    //                BestMotifs ← Motifs
    //        return BestMotifs
    public Dna greedyMotifSearch(int k) {
        AtomicReference<Dna> bestMotifs = new AtomicReference<>(new Dna(stream().map(g -> g.chunk(0, k)).collect(Collectors.toList())));
        AtomicReference<Double> bestScore = new AtomicReference<>(bestMotifs.get().score(k));
        genomes.get(0).kmerStream(k).
                map(motif1 -> {
                    List<Genome> genomes = new ArrayList<>();
                    genomes.add(motif1);
                    for (int i = 1; i < size(); i++) {
                        Genome next = at(i).mostProbableKmer(new Dna(genomes).profile(k), k);
                        genomes.add(next);
                    }
                    return new Dna(genomes);
                }).
                forEach(dna -> {
                    double score = dna.score(k);
                    if (score < bestScore.get()) {
                        bestMotifs.set(dna);
                        bestScore.set(score);
                    }
                });
        return bestMotifs.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dna dna = (Dna) o;

        return genomes != null ? genomes.equals(dna.genomes) : dna.genomes == null;

    }

    @Override
    public int hashCode() {
        return genomes != null ? genomes.hashCode() : 0;
    }

    @Override
    public String toString() {
        return stream().map(Genome::toString).collect(Collectors.joining("\n"));
    }
}
