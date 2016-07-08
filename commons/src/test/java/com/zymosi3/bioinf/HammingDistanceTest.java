package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.toList;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class HammingDistanceTest {

    @Test
    public void testHammingDistance() {
        assertEquals(3, new Genome("GGGCCGTTGGT").hammingDistance(new Genome("GGACCGTTGAC")));
        assertEquals(4, new Genome("AAAA").hammingDistance(new Genome("TTTT")));
        assertEquals(8, new Genome("ACGTACGT").hammingDistance(new Genome("TACGTACG")));
        assertEquals(6, new Genome("ACGTACGT").hammingDistance(new Genome("CCCCCCCC")));
        assertEquals(8, new Genome("ACGTACGT").hammingDistance(new Genome("TGCATGCA")));
        assertEquals(15, new Genome("GATAGCAGCTTCTGAACTGGTTACCTGCCGTGAGTAAATTAAAATTTTATTGACTTAGGTCACTAAATACT").
                hammingDistance(new Genome("AATAGCAGCTTCTCAACTGGTTACCTCGTATGAGTAAATTAGGTCATTATTGACTCAGGTCACTAACGTCT")));
        assertEquals(28, new Genome("AGAAACAGACCGCTATGTTCAACGATTTGTTTTATCTCGTCACCGGGATATTGCGGCCACTCATCGGTCAGTTGATTACGCAGGGCGTAAATCGCCAGAATCAGGCTG").
                hammingDistance(new Genome("AGAAACCCACCGCTAAAAACAACGATTTGCGTAGTCAGGTCACCGGGATATTGCGGCCACTAAGGCCTTGGATGATTACGCAGAACGTATTGACCCAGAATCAGGCTC")));
    }

    @Test
    public void testDistance() {
        assertEquals(5, new Dna(toList("TTACCTTAAC GATATCTGTC ACGGCGTTCG CCCTAAAGAG CGTCAGAGGT")).distance(new Genome("AAA")));
        assertEquals(3, new Dna(toList("TTTATTT CCTACAC GGTAGAG")).distance(new Genome("TAA")));
        assertEquals(0, new Dna(toList("AAACT AAAC AAAG")).distance(new Genome("AAA")));
        assertEquals(0, new Dna(toList("TTTTAAA CCCCAAA GGGGAAA")).distance(new Genome("AAA")));
        assertEquals(0, new Dna(toList("AAATTTT AAACCCC AAAGGGG")).distance(new Genome("AAA")));
    }
}
