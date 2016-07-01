import com.zymosy3.bioinf.Genome;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PatternCountApxTest {

    @Test
    public void testPatternCountApx() {
        assertEquals(4, new Genome("TTTAGAGCCTTCAGAGG").patternCountApx("GAGG", 2));
        assertEquals(2, new Genome("AAA").patternCountApx("AA", 0));
        assertEquals(1, new Genome("ATA").patternCountApx("ATA", 1));
    }
}
