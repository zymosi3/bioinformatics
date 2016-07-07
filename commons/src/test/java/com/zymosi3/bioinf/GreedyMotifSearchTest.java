package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.toList;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class GreedyMotifSearchTest {

    @Test
    public void testGreedyMotifSearch() {
        assertEquals(
                new Dna(toList("CAG CAG CAA CAA CAA")),
                new Dna(toList("GGCGTTCAGGCA AAGAATCAGTCA CAAGGAGTTCGC CACGTCAATCAC CAATAATATTCG")).greedyMotifSearch(3)
        );

        assertEquals(
                new Dna(toList("GCC GCC AAC TTC")),
                new Dna(toList("GCCCAA GGCCTG AACCTA TTCCTT")).greedyMotifSearch(3)
        );

        assertEquals(
                new Dna(toList("GAGGC TCATC TCGGC GAGTC GCAGC GCGGC GCGGC GCATC")),
                new Dna(toList("GAGGCGCACATCATTATCGATAACGATTCGCCGCATTGCC " +
                        "TCATCGAATCCGATAACTGACACCTGCTCTGGCACCGCTC " +
                        "TCGGCGGTATAGCCAGAAAGCGTAGTGCCAATAATTTCCT " +
                        "GAGTCGTGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG " +
                        "GACGGCAACTACGGTTACAACGCAGCAACCGAAGAATATT " +
                        "TCTGTTGTTGCTAACACCGTTAAAGGCGGCGACGGCAACT " +
                        "AAGCGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG " +
                        "AATTGAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA")).greedyMotifSearch(5)
        );

        assertEquals(
                new Dna(toList("GTGCGT GTGCGT GCGCCA GTGCCA GCGCCA")),
                new Dna(toList("GCAGGTTAATACCGCGGATCAGCTGAGAAACCGGAATGTGCGT " +
                        "CCTGCATGCCCGGTTTGAGGAACATCAGCGAAGAACTGTGCGT " +
                        "GCGCCAGTAACCCGTGCCAGTCAGGTTAATGGCAGTAACATTT " +
                        "AACCCGTGCCAGTCAGGTTAATGGCAGTAACATTTATGCCTTC " +
                        "ATGCCTTCCGCGCCAATTGTTCGTATCGTCGCCACTTCGAGTG")).greedyMotifSearch(6)
        );

        assertEquals(
                new Dna(toList("GCAGC TCATT GGAGT TCATC GCATC GCATC GGTAT GCAAC")),
                new Dna(toList("GACCTACGGTTACAACGCAGCAACCGAAGAATATTGGCAA " +
                        "TCATTATCGATAACGATTCGCCGGAGGCCATTGCCGCACA " +
                        "GGAGTCTGGTGAAGTGTGGGTTATGGGGCAGACTGGGAAA " +
                        "GAATCCGATAACTGACACCTGCTCTGGCACCGCTCTCATC " +
                        "AAGCGCGTAGGCGCGGCTTGGCATCTCGGTGTGTGGCCAA " +
                        "AATTGAAAGGCGCATCTTACTCTTTTCGCTTAAAATCAAA " +
                        "GGTATAGCCAGAAAGCGTAGTTAATTTCGGCTCCTGCCAA " +
                        "TCTGTTGTTGCTAACACCGTTAAAGGCGGCGACGGCAACT")).greedyMotifSearch(5)
        );
    }


}
