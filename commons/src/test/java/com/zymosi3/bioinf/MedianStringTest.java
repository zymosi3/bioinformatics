package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class MedianStringTest {

    @Test
    public void testMedianString() {
        assertEquals(
                new Genome("GAC"),
                new Dna(toList("AAATTGACGCAT GACGACCACGTT CGTCAGCGCCTG GCTGAGCACCGG AGTTCGGGACAG")).medianString(3)
        );

        assertEquals(
                new Genome("ACG"),
                new Dna(toList("ACGT ACGT ACGT")).medianString(3)
        );

        assertEquals(
                new Genome("AAA"),
                new Dna(toList("ATA ACA AGA AAT AAC")).medianString(3)
        );

        assertEquals(
                new Genome("AAG"),
                new Dna(toList("AAG AAT")).medianString(3)
        );

    }
}
