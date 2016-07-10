package com.zymosi3.bioinf;

import org.junit.Test;

import java.util.Random;

import static com.zymosi3.bioinf.Util.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class RandomizedMotifSearchTest {

    @Test
    public void testRandomizedMotifSearchTest() {
        Random r = new Random(42);
        assertEquals(
                new Dna(toList("TCTCGGGG CCAAGGTG TACAGGCG TTCAGGTG TCCACGTG")),
                iterateToMin(
                        1000,
                        () -> new Dna(toList("CGCCCCTCTCGGGGGTGTTCAGTAAACGGCCA " +
                                "GGGCGAGGTATGTGTAAGTGCCAAGGTGCCAG " +
                                "TAGTACCGAGACCGAAAGAAGTATACAGGCGT " +
                                "TAGATCAAGTTTCAGGTGCACGTCGGTGAACC " +
                                "AATCCACCAGCTCCACGTGCAATGTTGGCCTA")).randomizedMotifSearch(r, 8),
                        dna -> dna.score(8)
                )
        );

        assertEquals(
                new Dna(toList("CGATAA " +
                        "GGTTAA " +
                        "GGTATA " +
                        "GGTTAA " +
                        "GGTTAC " +
                        "GGTTAA " +
                        "GGCCAA " +
                        "GGTTAA")),
                iterateToMin(
                        1000,
                        () -> new Dna(toList("AATTGGCACATCATTATCGATAACGATTCGCCGCATTGCC " +
                                "GGTTAACATCGAATAACTGACACCTGCTCTGGCACCGCTC " +
                                "AATTGGCGGCGGTATAGCCAGATAGTGCCAATAATTTCCT " +
                                "GGTTAATGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG " +
                                "AATTGGACGGCAACTACGGTTACAACGCAGCAAGAATATT " +
                                "GGTTAACTGTTGTTGCTAACACCGTTAAGCGACGGCAACT " +
                                "AATTGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG " +
                                "GGTTAAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA")).randomizedMotifSearch(r, 6),
                        dna -> dna.score(6)
                )
        );

        assertEquals(
                new Dna(toList("TTAACC " +
                        "ATAACT " +
                        "TTAACC " +
                        "TGAAGT " +
                        "TTAACC " +
                        "TTAAGC " +
                        "TTAACC " +
                        "TGAACA")),
                iterateToMin(
                        3000,
                        () -> new Dna(toList("GCACATCATTAAACGATTCGCCGCATTGCCTCGATTAACC " +
                                "TCATAACTGACACCTGCTCTGGCACCGCTCATCCAAGGCC " +
                                "AAGCGGGTATAGCCAGATAGTGCCAATAATTTCCTTAACC " +
                                "AGTCGGTGGTGAAGTGTGGGTTATGGGGAAAGGCAAGGCC " +
                                "AACCGGACGGCAACTACGGTTACAACGCAGCAAGTTAACC " +
                                "AGGCGTCTGTTGTTGCTAACACCGTTAAGCGACGAAGGCC " +
                                "AAGCTTCCAACATCGTCTTGGCATCTCGGTGTGTTTAACC " +
                                "AATTGAACATCTTACTCTTTTCGCTTTCAAAAAAAAGGCC")).randomizedMotifSearch(r, 6),
                        dna -> dna.score(6)
                )
        );
    }
}
