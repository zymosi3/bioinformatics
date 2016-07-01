import com.zymosy3.bioinf.Genome;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class NeighborsTest {

    @Test
    public void testNeighbors() {
        assertEquals(
                new HashSet<Genome>(){{
                    add(new Genome("CCG"));
                    add(new Genome("TCG"));
                    add(new Genome("GCG"));
                    add(new Genome("AAG"));
                    add(new Genome("ATG"));
                    add(new Genome("AGG"));
                    add(new Genome("ACA"));
                    add(new Genome("ACC"));
                    add(new Genome("ACT"));
                    add(new Genome("ACG"));
                }},
                new Genome("ACG").neighbors(1)
        );
    }
}
