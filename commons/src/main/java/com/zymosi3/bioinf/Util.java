package com.zymosi3.bioinf;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public static Map<Nucleotide, List<Float>> toProfile(String s) {
        String[] rows = s.split("\n");
        return IntStream.range(0, rows.length).
                mapToObj(Integer::valueOf).
                collect(Collectors.toMap(
                        Nucleotide::byValue,
                        i -> Stream.of(rows[i].split(" ")).map(Float::valueOf).collect(Collectors.toList())
                ));
    }

    public static <T> T iterateToMin(int n, Supplier<T> s, Function<T, Float> t) {
        return IntStream.range(0, n).
                mapToObj(i -> s.get()).
                map(o -> new Object[]{o, t.apply(o)}).
                reduce((a1, a2) -> (((float) a1[1]) < ((Float) a2[1])) ? a1 : a2).
                map(a -> (T) a[0]).
                orElse(null);
    }

    public static int random(Random r, double... probabilities) {
        int mult = 1000000000;
        int[] dist = DoubleStream.of(probabilities).mapToInt(p -> (int) (p * mult)).toArray();
        int sum = IntStream.of(dist).sum();

        int x = r.nextInt(sum);
        int acc = 0;
        for (int i = 0; i < dist.length ; i++) {
            if (acc <= x && x < acc + dist[i]) {
                return i;
            }
            acc += dist[i];
        }
        throw new RuntimeException();
    }
}
