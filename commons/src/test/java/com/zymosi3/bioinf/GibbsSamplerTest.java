package com.zymosi3.bioinf;

import org.junit.Test;

import java.util.Random;

import static com.zymosi3.bioinf.Util.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class GibbsSamplerTest {

    @Test
    public void testGibbsSampler() {
        Random r = new Random(2);
        assertEquals(
                new Dna(toList("TCTCGGGG CCAAGGTG TACAGGCG TTCAGGTG TCCACGTG")),
                iterateToMin(
                        20,
                        () -> new Dna(toList("CGCCCCTCTCGGGGGTGTTCAGTAAACGGCCA " +
                                "GGGCGAGGTATGTGTAAGTGCCAAGGTGCCAG " +
                                "TAGTACCGAGACCGAAAGAAGTATACAGGCGT " +
                                "TAGATCAAGTTTCAGGTGCACGTCGGTGAACC " +
                                "AATCCACCAGCTCCACGTGCAATGTTGGCCTA")).gibbsSampler(r, 8, 100),
                        dna -> dna.score(8)
                )
        );
    }
}
