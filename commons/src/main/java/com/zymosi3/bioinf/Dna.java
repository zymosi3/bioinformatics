package com.zymosi3.bioinf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
        this(Stream.of(genomes).collect(Collectors.toList()));
    }

    public Dna(List<Genome> genomes) {
        this.genomes = Collections.unmodifiableList(new ArrayList<>(genomes));
    }

    public Stream<Genome> stream() {
        return genomes.stream();
    }

    public Stream<Genome> randomMotifs(Random r, int k) {
        return stream().map(g -> g.randomKmer(r, k));
    }

    public int size() {
        return genomes.size();
    }

    public Genome at(int i) {
        return genomes.get(i);
    }

    public Dna add(Genome g) {
        return new Dna(Stream.concat(stream(), Stream.of(g)).collect(Collectors.toList()));
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

    public Map<Nucleotide, List<Float>> profile(int k) {
        return IntStream.range(0, k).
                mapToObj(i -> genomes.stream().map(g -> g.at(i))).
                map(col -> col.collect(Collectors.toMap(
                        n -> n,
                        n -> 1.0,
                        (d1, d2) -> d1 + d2,
                        () -> Nucleotide.stream().collect(Collectors.toMap(n -> n, n -> 1.0))
                ))).
                map(m -> Nucleotide.stream().collect(Collectors.toMap(n -> n, n -> m.get(n) / (genomes.size() + 4)))).
                reduce(
                        Nucleotide.stream().collect(Collectors.toMap(n -> n, n -> new ArrayList<Float>())),
                        (r, m) -> {
                            Nucleotide.stream().forEach(n -> r.get(n).add(m.get(n).floatValue()));
                            return r;
                        },
                        (m1, m2) -> m2
                );
    }

    public Genome consensus(int k) {
        Map<Nucleotide, List<Float>> profile = profile(k);
        return new Genome(IntStream.range(0, k).
                mapToObj(i ->
                        Nucleotide.stream().
                                map(n -> new Object[]{n, profile.get(n).get(i)}).
                                max((o1, o2) -> Float.compare((float) o1[1], (float) o2[1])).
                                map(a -> (Nucleotide) a[0]).
                                orElse(null)
                ).
                map(Enum::name).
                collect(Collectors.joining()));
    }

    public float score(int k) {
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
        return Stream.concat(
                Stream.of(new Dna(stream().map(g -> g.kmer(0, k)).collect(Collectors.toList()))),
                genomes.get(0).kmerStream(k).
                        map(motif1 ->
                                Stream.iterate(new Dna(motif1), dna -> dna.add(at(dna.size()).mostProbableKmer(dna.profile(k), k))).
                                        limit(size()).
                                        reduce((d1, d2) -> d2).
                                        orElse(null)
                        )
                ).
                map(dna -> new Object[]{dna, dna.score(k)}).
                reduce((o1, o2) -> (((float) o2[1]) < ((float) o1[1])) ? o2 : o1).
                map(a -> (Dna) a[0]).
                orElse(null);
    }

    //    RandomizedMotifSearch(Dna, k, t)
    //        randomly select k-mers Motifs = (Motif1, …, Motift) in each string from Dna
    //        BestMotifs ← Motifs
    //        while forever
    //            Profile ← Profile(Motifs)
    //            Motifs ← Motifs(Profile, Dna)
    //            if Score(Motifs) < Score(BestMotifs)
    //                BestMotifs ← Motifs
    //            else
    //                return BestMotifs
    public Dna randomizedMotifSearch(Random r, int k) {
        Dna bestMotifs = new Dna(randomMotifs(r, k).collect(Collectors.toList()));
        float bestScore = bestMotifs.score(k);
        Dna motifs = bestMotifs;
        while (true) {
            Map<Nucleotide, List<Float>> profile = motifs.profile(k);
            motifs = new Dna(stream().map(g -> g.mostProbableKmer(profile, k)).collect(Collectors.toList()));
            float score = motifs.score(k);
            if (score < bestScore) {
                bestMotifs = motifs;
                bestScore = score;
            } else {
                return bestMotifs;
            }
        }
    }

    //    GibbsSampler(Dna, k, t, N)
    //        randomly select k-mers Motifs = (Motif1, …, Motift) in each string from Dna
    //        BestMotifs ← Motifs
    //        for j ← 1 to N
    //            i ← Random(t)
    //            Profile ← profile matrix constructed from all strings in Motifs except for Motifi
    //            Motifi ← Profile-randomly generated k-mer in the i-th sequence
    //            if Score(Motifs) < Score(BestMotifs)
    //                BestMotifs ← Motifs
    //        return BestMotifs
    public Dna gibbsSampler(Random r, int k, int n) {
        List<Genome> motifs = randomMotifs(r, k).collect(Collectors.toList());
        List<Genome> bestMotifs = new ArrayList<>(motifs);
        float bestScore = new Dna(bestMotifs).score(k);
        for (int j = 0; j < n; j++) {
            int i = r.nextInt(size());
            motifs.remove(i);
            Map<Nucleotide, List<Float>> profile = new Dna(motifs).profile(k);
            double[] p = at(i).kmerStream(k).mapToDouble(kmer -> kmer.probability(profile)).toArray();
            motifs.add(i, at(i).kmer(Util.random(r, p), k));
            float score = new Dna(motifs).score(k);
            if (score < bestScore) {
                bestMotifs = new ArrayList<>(motifs);
                bestScore = score;
            }
        }
        return new Dna(bestMotifs);
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
