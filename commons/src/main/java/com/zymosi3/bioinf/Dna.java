package com.zymosi3.bioinf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Dna {

    private final Collection<Genome> genomes;

    public Dna(Genome... genomes) {
        this.genomes = Stream.of(genomes).collect(Collectors.toList());
    }

    public Dna(Collection<Genome> genomes) {
        this.genomes = Collections.unmodifiableCollection(new ArrayList<>(genomes));
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
}
