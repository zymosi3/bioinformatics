import com.zymosy3.bioinf.Genome;
import org.junit.Test;

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
}
