package com.zymosy3.bioinf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Neighbors implements Function<Genome, Function<Integer, Set<Genome>>> {

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
    @Override
    public Function<Integer, Set<Genome>> apply(Genome g) {
        return d -> {
            if (d == 0) {
                return Collections.singleton(g);
            }
            if (g.size() == 1) {
                return Stream.of(Symbol.values()).
                        map(Symbol::name).
                        map(Genome::new).
                        collect(Collectors.toSet());
            }
            return g.suffix().neighbors(d).
                    stream().
                    flatMap(n -> g.suffix().hammingDistance(n) < d ?
                            Stream.of(Symbol.values()).map(n::addFirst) :
                            Stream.of(n.addFirst(g.first()))).
                    collect(Collectors.toSet());
        };
    }
}
