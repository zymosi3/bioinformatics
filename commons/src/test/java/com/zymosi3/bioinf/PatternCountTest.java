package com.zymosi3.bioinf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PatternCountTest {

    @Test
    public void patternCountTest() {
        assertEquals(2, new Genome("GCGCG").patternCount("GCG"));
        assertEquals(3, new Genome("ACGTACGTACGT").patternCount("CG"));
        assertEquals(4, new Genome("AAAGAGTGTCTGATAGCAGCTTCTGAACTGGTTACCTGCCGTGAGTAAATTAAATTTTATTGAC" +
                "TTAGGTCACTAAATACTTTAACCAATATAGGCATAGCGCACAGACAGATAATAATTACAGAGTA" +
                "CACAACATCCAT").patternCount("AAA"));
        assertEquals(4, new Genome("AGCGTGCCGAAATATGCCGCCAGACCTGCTGCGGTGGCCTCGCCGACTTCACGGATGCCAAGTG" +
                "CATAGAGGAAGCGAGCAAAGGTGGTTTCTTTCGCTTTATCCAGCGCGTTAACCACGTTCTGTGC" +
                "CGACTTT").patternCount("TTT"));
        assertEquals(2, new Genome("GGACTTACTGACGTACG").patternCount("ACT"));
        assertEquals(5, new Genome("ATCCGATCCCATGCCCATG").patternCount("CC"));
        assertEquals(9, new Genome("CTGTTTTTGATCCATGATATGTTATCTCTCCGTCATCAGAAGAACAGTGACGGATCGCCCTCTC" +
                "TCTTGGTCAGGCGACCGTTTGCCATAATGCCCATGCTTTCCAGCCAGCTCTCAAACTCCGGTGA" +
                "CTCGCGCAGGTTGAGTA").patternCount("CTC"));
    }
}
