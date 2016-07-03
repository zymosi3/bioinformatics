package com.zymosi3.bioinf;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Util {

    public static Set<Genome> toSet(String s) {
        return Stream.of(s.split(" ")).map(Genome::new).collect(Collectors.toSet());
    }

    public static <T> String toString(Collection<T> objects) {
        return objects.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public static String toColumn(Collection<Genome> genomes) {
        return genomes.stream().map(Genome::toString).collect(Collectors.joining("\n"));
    }
}
