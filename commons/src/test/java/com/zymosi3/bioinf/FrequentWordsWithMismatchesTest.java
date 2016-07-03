package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.toSet;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class FrequentWordsWithMismatchesTest {

    @Test
    public void testFrequentWordsWithMismatches() {
        assertEquals(
                toSet("ATGC ATGT GATG"),
                new Genome("ACGTTGCATGTCGCATGATGCATGAGAGCT").frequentWordsWithMismatches(4, 1)
        );
        assertEquals(
                toSet("AA AC AG CA AT GA TA"),
                new Genome("AAAAAAAAAA").frequentWordsWithMismatches(2, 1)
        );
        assertEquals(
                toSet("TCTC CGGC AAGC TGTG GGCC AGGT ATCC ACTG ACAC AGAG ATTA TGAC AATT " +
                        "CGTT GTTC GGTA AGCA CATC"),
                new Genome("AGTCAGTC").frequentWordsWithMismatches(4, 2)
        );
        assertEquals(
                toSet("GGTA"),
                new Genome("AATTAATTGGTAGGTAGGTA").frequentWordsWithMismatches(4, 0)
        );
        assertEquals(
                toSet("GTA ACA AAA ATC ATA AGA ATT CTA TTA ATG"),
                new Genome("ATA").frequentWordsWithMismatches(3, 1)
        );
        assertEquals(
                toSet("AAT"),
                new Genome("AAT").frequentWordsWithMismatches(3, 0)
        );
        assertEquals(
                toSet("GG TG"),
                new Genome("TAGCG").frequentWordsWithMismatches(2, 1)
        );
    }


}
