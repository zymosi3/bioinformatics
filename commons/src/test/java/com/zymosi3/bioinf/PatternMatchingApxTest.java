package com.zymosi3.bioinf;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


/**
 *
 */
public class PatternMatchingApxTest {

    @Test
    public void testPatternMatchingApx() {
        assertTrue(Arrays.equals(new int[] {6, 7, 26, 27},
                new Genome("CGCCCGAATCCAGAACGCATTCCCATATTTCGGGACCACTGGCCTCCACGGTACGGACGTCAATCAAAT").
                        patternMatchingApx(new Genome("ATTCTGGA"), 3)));
        assertTrue(Arrays.equals(new int[] {4, 5, 6, 7, 8, 11, 12, 13, 14, 15},
                new Genome("TTTTTTAAATTTTAAATTTTTT").
                        patternMatchingApx(new Genome("AAA"), 2)));
        assertTrue(Arrays.equals(new int[] {0, 30, 66},
                new Genome("GAGCGCTGGGTTAACTCGCTACTTCCCGACGAGCGCTGTGGCGCAAATTGGCGATGA" +
                        "AACTGCAGAGAGAACTGGTCATCCAACTGAATTCTCCCCGCTATCGCATTTTGATGC" +
                        "GCGCCGCGTCGATT").
                        patternMatchingApx(new Genome("GAGCGCTGG"), 2)));
        assertTrue(Arrays.equals(new int[] {3, 36, 74, 137},
                new Genome("CCAAATCCCCTCATGGCATGCATTCCCGCAGTATTTAATCCTTTCATTCTGCATATAA" +
                        "GTAGTGAAGGTATAGAAACCCGTTCAAGCCCGCAGCGGTAAAACCGAGAACCATGA" +
                        "TGAATGCACGGCGATTGCGCCATAATCCAAACA").
                        patternMatchingApx(new Genome("AATCCTTTCA"), 3)));
        assertTrue(Arrays.equals(new int[] {0, 7, 36, 44, 48, 72, 79, 112},
                new Genome("CCGTCATCCGTCATCCTCGCCACGTTGGCATGCATTCCGTCATCCCGTCAGGCATACT" +
                        "TCTGCATATAAGTACAAACATCCGTCATGTCAAAGGGAGCCCGCAGCGGTAAAACC" +
                        "GAGAACCATGATGAATGCACGGCGATTGC").
                        patternMatchingApx(new Genome("CCGTCATCC"), 3)));
        assertTrue(Arrays.equals(new int[] {0, 1, 2, 3},
                new Genome("AAAAAA").patternMatchingApx(new Genome("TTT"), 3)));
        assertTrue(Arrays.equals(new int[] {0},
                new Genome("CCACCT").patternMatchingApx(new Genome("CCA"), 0)));
    }
}
