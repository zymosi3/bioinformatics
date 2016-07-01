package com.zymosy3.bioinf;

import java.util.ArrayList;
import java.util.function.Function;

/**
 *
 */
public class Skew implements Function<Genome, int[]> {

    @Override
    public int[] apply(Genome g) {
        return g.stream().
                map(s -> {
                    switch (s) {
                        case G:
                            return 1;
                        case C:
                            return -1;
                        default:
                            return 0;
                    }
                }).
                reduce(
                        new ArrayList<Integer>(g.s.length()),
                        (l, i) -> { if (l.isEmpty()) l.add(i); else l.add(l.get(l.size() - 1) + i); return l; },
                        (l1, l2) -> l1
                ).
                stream().
                mapToInt(i -> i).
                toArray();
    }
}
