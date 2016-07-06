package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.toSet;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class MedianStringTest {

    @Test
    public void testMedianString() {
        assertEquals(
                new Genome("GAC"),
                new Dna(toSet("AAATTGACGCAT GACGACCACGTT CGTCAGCGCCTG GCTGAGCACCGG AGTTCGGGACAG")).medianString(3)
        );

        assertEquals(
                new Genome("ACG"),
                new Dna(toSet("ACGT ACGT ACGT")).medianString(3)
        );

        assertEquals(
                new Genome("AAA"),
                new Dna(toSet("ATA ACA AGA AAT AAC")).medianString(3)
        );

        assertEquals(
                new Genome("AAG"),
                new Dna(toSet("AAG AAT")).medianString(3)
        );

    }
}
