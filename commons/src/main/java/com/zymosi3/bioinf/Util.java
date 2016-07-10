package com.zymosi3.bioinf;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.zymosi3.bioinf.Nucleotide.*;

/**
 *
 */
public class Util {

    public static Set<Genome> toSet(String s) {
        return Stream.of(s.split(" ")).map(Genome::new).collect(Collectors.toSet());
    }

    public static List<Genome> toList(String s) {
        return Stream.of(s.split(" ")).map(Genome::new).collect(Collectors.toList());
    }

    public static <T> String toString(Collection<T> objects) {
        return objects.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public static String toColumn(Collection<Genome> genomes) {
        return genomes.stream().map(Genome::toString).collect(Collectors.joining("\n"));
    }

    public static Map<Nucleotide, List<Double>> toProfile(String s) {
        String[] rows = s.split("\n");
        return IntStream.range(0, rows.length).
                mapToObj(Integer::valueOf).
                collect(Collectors.toMap(
                        Nucleotide::byValue,
                        i -> Stream.of(rows[i].split(" ")).map(Double::valueOf).collect(Collectors.toList())
                ));
    }

    public static <T> T iterateToMin(int n, Supplier<T> s, Function<T, Double> t) {
        return IntStream.range(0, n).
                mapToObj(i -> s.get()).
                map(o -> new Object[]{o, t.apply(o)}).
                reduce((a1, a2) -> (((double) a1[1]) < ((double) a2[1])) ? a1 : a2).
                map(a -> (T) a[0]).
                orElse(null);
    }
}
