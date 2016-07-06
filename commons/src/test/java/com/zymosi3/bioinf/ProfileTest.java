package com.zymosi3.bioinf;

import org.junit.Test;

import static com.zymosi3.bioinf.Util.*;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ProfileTest {

    @Test
    public void testProfile() {
        assertEquals(
                toProfile(
                        "0.2 0.2 0.0 0.0 0.0 0.0 0.9 0.1 0.1 0.1 0.3 0.0\n" +
                        "0.1 0.6 0.0 0.0 0.0 0.0 0.0 0.4 0.1 0.2 0.4 0.6\n" +
                        "0.0 0.0 1.0 1.0 0.9 0.9 0.1 0.0 0.0 0.0 0.0 0.0\n" +
                        "0.7 0.2 0.0 0.0 0.1 0.1 0.0 0.5 0.8 0.7 0.3 0.4"),
                new Dna(toSet(
                        "TCGGGGgTTTtt " +
                        "cCGGtGAcTTaC " +
                        "aCGGGGATTTtC " +
                        "TtGGGGAcTTtt " +
                        "aaGGGGAcTTCC " +
                        "TtGGGGAcTTCC " +
                        "TCGGGGATTcat " +
                        "TCGGGGATTcCt " +
                        "TaGGGGAacTaC " +
                        "TCGGGtATaaCC ")).profile(12)
        );
    }
}
