package com.zymosi3.bioinf;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class FrequentWordsWithMismatchesAndReverseTest {

    @Test
    public void testFrequentWordsWithMismatchesAndReverse() {
        Assert.assertEquals(
                Util.toSet("ACAT ATGT"),
                new Genome("ACGTTGCATGTCGCATGATGCATGAGAGCT").frequentWordsWithMismatchesAndReverse(4, 1)
        );
        Assert.assertEquals(
                Util.toSet("AT TA"),
                new Genome("AAAAAAAAAA").frequentWordsWithMismatchesAndReverse(2, 1)
        );
        Assert.assertEquals(
                Util.toSet("AATT GGCC"),
                new Genome("AGTCAGTC").frequentWordsWithMismatchesAndReverse(4, 2)
        );
        Assert.assertEquals(
                Util.toSet("AATT"),
                new Genome("AATTAATTGGTAGGTAGGTA").frequentWordsWithMismatchesAndReverse(4, 0)
        );
        Assert.assertEquals(
                Util.toSet("AAA AAT ACA AGA ATA ATC ATG ATT CAT CTA GAT GTA TAA TAC TAG TAT TCT TGT TTA TTT"),
                new Genome("ATA").frequentWordsWithMismatchesAndReverse(3, 1)
        );
        Assert.assertEquals(
                Util.toSet("AAT ATT"),
                new Genome("AAT").frequentWordsWithMismatchesAndReverse(3, 0)
        );
        Assert.assertEquals(
                Util.toSet("CA CC GG TG"),
                new Genome("TAGCG").frequentWordsWithMismatchesAndReverse(2, 1)
        );
    }
}
