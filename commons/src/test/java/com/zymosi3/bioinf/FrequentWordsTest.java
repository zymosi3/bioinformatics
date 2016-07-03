package com.zymosi3.bioinf;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class FrequentWordsTest {

    @Test
    public void testFrequentWords() {
        assertEquals(
                new HashSet<Genome>(){{
                    add(new Genome("CATG"));
                    add(new Genome("GCAT"));
                }},
                new Genome("ACGTTGCATGTCGCATGATGCATGAGAGCT").frequentWords(4)
        );
        assertEquals(
                new HashSet<Genome>(){{
                    add(new Genome("TGG"));
                }},
                new Genome("TGGTAGCGACGTTGGTCCCGCCGCTTGAGAATCTGGATGAACATAAGCTCCCACTTGGCTTATT" +
                        "CAGAGAACTGGTCAACACTTGTCTCTCCCAGCCAGGTCTGACCACCGGGCAACTTTTAGAGCAC" +
                        "TATCGTGGTACAAATAATGCTGCCAC").frequentWords(3)
        );
        assertEquals(
                new HashSet<Genome>(){{
                    add(new Genome("TTTT"));
                }},
                new Genome("CAGTGGCAGATGACATTTTGCTGGTCGACTGGTTACAACAACGCCTGGGGCTTTTGAGCAACGA" +
                        "GACTTTTCAATGTTGCACCGTTTGCTGCATGATATTGAAAACAATATCACCAAATAAATAACGC" +
                        "CTTAGTAAGTAGCTTTT").frequentWords(4)
        );
        assertEquals(
                new HashSet<Genome>(){{
                    add(new Genome("AACAA"));
                }},
                new Genome("ATACAATTACAGTCTGGAACCGGATGAACTGGCCGCAGGTTAACAACAGAGTTGCCAGGCACTG" +
                        "CCGCTGACCAGCAACAACAACAATGACTTTGACGCGAAGGGGATGGCATGAGCGAACTGATCGT" +
                        "CAGCCGTCAGCAACGAGTATTGTTGCTGACCCTTAACAATCCCGCCGCACGTAATGCGCTAACT" +
                        "AATGCCCTGCTG").frequentWords(5)
        );
        assertEquals(
                new HashSet<Genome>(){{
                    add(new Genome("AAAAT"));
                    add(new Genome("GGGGT"));
                    add(new Genome("TTTTA"));
                }},
                new Genome("CCAGCGGGGGTTGATGCTCTGGGGGTCACAAGATTGCATTTTTATGGGGTTGCAAAAATGTTTT" +
                        "TTACGGCAGATTCATTTAAAATGCCCACTGGCTGGAGACATAGCCCGGATGCGCGTCTTTTACA" +
                        "ACGTATTGCGGGGTAAAATCGTAGATGTTTTAAAATAGGCGTAAC").frequentWords(5)
        );
    }
}
