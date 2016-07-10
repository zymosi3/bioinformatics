package com.zymosi3.bioinf.week2;

import com.zymosi3.bioinf.Genome;
import com.zymosi3.bioinf.Util;

import java.util.Scanner;

/**
 * By minimumSkew for Salmonella enterica we got 3764856 3764858
 * so explore area around 3764857 by frequentWordsWithMismatchesAndReverse
 */
public class SalmonellaEnterica {

    private static final int MINIMUM_SKEW = 3764857;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("start SalmonellaEnterica");
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) sb.append(scanner.next());
        Genome genome = new Genome(sb.toString());
        System.out.println("got genome length " + genome.size());
        int k = 9;
        int d = 1;
        System.out.println("d = 1");
        Genome windowAround = genome.kmer(MINIMUM_SKEW - 250, 500);
        System.out.println("around minimum skew: " + Util.toString(windowAround.frequentWordsWithMismatchesAndReverse(k, d)));
        Genome windowLeft = genome.kmer(MINIMUM_SKEW - 500, 500);
        System.out.println("left of minimum skew: " + Util.toString(windowLeft.frequentWordsWithMismatchesAndReverse(k, d)));
        Genome windowRight = genome.kmer(MINIMUM_SKEW, 500);
        System.out.println("right of minimum skew: " + Util.toString(windowRight.frequentWordsWithMismatchesAndReverse(k, d)));

        System.out.println("trying to find clumps around minimum skew");
        Genome toFindClumps = genome.kmer(MINIMUM_SKEW - 500, 1000);
        System.out.println("clumps: " + toFindClumps.clumpsWithMismatchesAndReverse(k, 500, 3, d));

        d = 2;
        System.out.println("d = 2");
        windowAround = genome.kmer(MINIMUM_SKEW - 250, 500);
        System.out.println("around minimum skew: " + Util.toString(windowAround.frequentWordsWithMismatchesAndReverse(k, d)));
        windowLeft = genome.kmer(MINIMUM_SKEW - 500, 500);
        System.out.println("left of minimum skew: " + Util.toString(windowLeft.frequentWordsWithMismatchesAndReverse(k, d)));
        windowRight = genome.kmer(MINIMUM_SKEW, 500);
        System.out.println("right of minimum skew: " + Util.toString(windowRight.frequentWordsWithMismatchesAndReverse(k, d)));

        System.out.println("trying to find clumps around minimum skew");
        toFindClumps = genome.kmer(MINIMUM_SKEW - 500, 1000);
        System.out.println("clumps: " + toFindClumps.clumpsWithMismatchesAndReverse(k, 500, 3, d));

        System.out.println("fin, time = " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
