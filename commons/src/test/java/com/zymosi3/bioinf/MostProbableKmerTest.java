package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.toProfile;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class MostProbableKmerTest {

    @Test
    public void testMostProbableKmer() {
        assertEquals(
                new Genome("CCGAG"),
                new Genome("ACCTGTTTATTGCCTAAGTTCCGAACAAACCCAATATAGCCCGAGGGCCT").
                        mostProbableKmer(toProfile("0.2 0.2 0.3 0.2 0.3\n" +
                                "0.4 0.3 0.1 0.5 0.1\n" +
                                "0.3 0.3 0.5 0.2 0.4\n" +
                                "0.1 0.2 0.1 0.1 0.2"), 5)
        );

        assertEquals(
                new Genome("AGCAGCTT"),
                new Genome("AGCAGCTTTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATCTGAACTGGTTACCTGCCGTGAGTAAAT").
                        mostProbableKmer(toProfile("0.7 0.2 0.1 0.5 0.4 0.3 0.2 0.1\n" +
                                "0.2 0.2 0.5 0.4 0.2 0.3 0.1 0.6\n" +
                                "0.1 0.3 0.2 0.1 0.2 0.1 0.4 0.2\n" +
                                "0.0 0.3 0.2 0.0 0.2 0.3 0.3 0.1"), 8)
        );

        assertEquals(
                new Genome("AAGCAGAGTTTA"),
                new Genome("TTACCATGGGACCGCTGACTGATTTCTGGCGTCAGCGTGATGCTGGTGTGGATGACATTCCGGTGCGCTTTGTAAGCAGAGTTTA").
                        mostProbableKmer(toProfile("0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.1 0.2 0.3 0.4 0.5\n" +
                                "0.3 0.2 0.1 0.1 0.2 0.1 0.1 0.4 0.3 0.2 0.2 0.1\n" +
                                "0.2 0.1 0.4 0.3 0.1 0.1 0.1 0.3 0.1 0.1 0.2 0.1\n" +
                                "0.3 0.4 0.1 0.1 0.1 0.1 0.0 0.2 0.4 0.4 0.2 0.3"), 12)
        );

        assertEquals(
                new Genome("TGTCGC"),
                new Genome("TGCCCGAGCTATCTTATGCGCATCGCATGCGGACCCTTCCCTAGGCTTGTCGCAAGCCATTATCCTGGGCGCTAGTTGCGCGAGTATTG" +
                        "TCAGACCTGATGACGCTGTAAGCTAGCGTGTTCAGCGGCGCGCAATGAGCGGTTTAGATCACAGAATCCTTTGGCGTATTCCTATCCGTTACAT" +
                        "CACCTTCCTCACCCCTA").
                        mostProbableKmer(toProfile("0.364 0.333 0.303 0.212 0.121 0.242\n" +
                                "0.182 0.182 0.212 0.303 0.182 0.303\n" +
                                "0.121 0.303 0.182 0.273 0.333 0.303\n" +
                                "0.333 0.182 0.303 0.212 0.364 0.152"), 6)
        );
    }
}
