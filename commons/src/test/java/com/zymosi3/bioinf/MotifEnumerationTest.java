package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.toSet;
import static org.junit.Assert.*;

/**
 *
 */
public class MotifEnumerationTest {

    @Test
    public void testMotifEnumeration() {
        assertEquals(
                toSet("ATA ATT GTT TTT"),
                new Dna(toSet("ATTTGGC TGCCTTA CGGTATC GAAAATT")).motifEnumeration(3, 1)
        );
        assertEquals(
                toSet("ACG CGT"),
                new Dna(toSet("ACGT ACGT ACGT")).motifEnumeration(3, 0)
        );
        assertEquals(
                toSet("AAA AAC AAG AAT ACA AGA ATA CAA GAA TAA"),
                new Dna(toSet("AAAAA AAAAA AAAAA")).motifEnumeration(3, 1)
        );
        assertEquals(
                toSet("AAA AAC AAG AAT ACA ACC ACG ACT AGA AGC AGG AGT ATA ATC ATG ATT " +
                        "CAA CAC CAG CAT CCA CCC CCG CCT CGA CGC CGG CGT CTA CTC CTG CTT " +
                        "GAA GAC GAG GAT GCA GCC GCG GCT GGA GGC GGG GGT GTA GTC GTG GTT " +
                        "TAA TAC TAG TAT TCA TCC TCG TCT TGA TGC TGG TGT TTA TTC TTG TTT"),
                new Dna(toSet("AAAAA AAAAA AAAAA")).motifEnumeration(3, 3)
        );
        assertTrue(
                new Dna(toSet("AACAA AAAAA AAAAA")).motifEnumeration(3, 0).isEmpty()
        );
    }
}
