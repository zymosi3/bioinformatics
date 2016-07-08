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
                new Dna(toList("TTC ATC TTC ATC TTC")),
                new Dna(toList("GGCGTTCAGGCA AAGAATCAGTCA CAAGGAGTTCGC CACGTCAATCAC CAATAATATTCG")).greedyMotifSearch(3)
        );

        assertEquals(
                new Dna(toList("AGGCG ATCCG AAGCG AGTCG AACCG AGGCG AGGCG AGGCG")),
                new Dna(toList("AGGCGGCACATCATTATCGATAACGATTCGCCGCATTGCC " +
                        "ATCCGTCATCGAATAACTGACACCTGCTCTGGCACCGCTC " +
                        "AAGCGTCGGCGGTATAGCCAGATAGTGCCAATAATTTCCT " +
                        "AGTCGGTGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG " +
                        "AACCGGACGGCAACTACGGTTACAACGCAGCAAGAATATT " +
                        "AGGCGTCTGTTGTTGCTAACACCGTTAAGCGACGGCAACT " +
                        "AAGCGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG " +
                        "AATTGAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA")).greedyMotifSearch(5)
        );

        assertEquals(
                new Dna(toList("AGGCG TGGCA AAGCG AGGCA CGGCA AGGCG AGGCG AGGCG")),
                new Dna(toList("GCACATCATTAAACGATTCGCCGCATTGCCTCGATAGGCG " +
                        "TCATAACTGACACCTGCTCTGGCACCGCTCATCCGTCGAA " +
                        "AAGCGGGTATAGCCAGATAGTGCCAATAATTTCCTTCGGC " +
                        "AGTCGGTGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG " +
                        "AACCGGACGGCAACTACGGTTACAACGCAGCAAGAATATT " +
                        "AGGCGTCTGTTGTTGCTAACACCGTTAAGCGACGGCAACT " +
                        "AAGCTTCCAACATCGTCTTGGCATCTCGGTGTGTGAGGCG " +
                        "AATTGAACATCTTACTCTTTTCGCTTTCAAAAAAAAGGCG")).greedyMotifSearch(5)
        );

        assertEquals(
                new Dna(toList("GGCGG GGCTC GGCGG GGCAG GACGG GACGG GGCGC GGCGC")),
                new Dna(toList("GCACATCATTATCGATAACGATTCATTGCCAGGCGGCCGC " +
                        "TCATCGAATAACTGACACCTGCTCTGGCTCATCCGACCGC " +
                        "TCGGCGGTATAGCCAGATAGTGCCAATAATTTCCTAAGCG " +
                        "GTGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTGAGTCG " +
                        "GACGGCAACTACGGTTACAACGCAGCAAGAATATTAACCG " +
                        "TCTGTTGTTGCTAACACCGTTAAGCGACGGCAACTAGGCG " +
                        "GCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTGAAGCG " +
                        "AAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAAAATTG")).greedyMotifSearch(5)
        );
    }


}
