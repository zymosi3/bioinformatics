package com.zymosi3.bioinf;

import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 *
 */
public class UtilTest {

    @Test
    public void testRandom() {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int v = Util.random(r, 0.1, 0.2, 0.3);
            assertTrue(0 <= v && v < 6);
        }
        for (int i = 0; i < 1000; i++) {
            int v = Util.random(r, 0.1, 0.2, 0.03);
            assertTrue(0 <= v && v < 33);
        }
    }
}
