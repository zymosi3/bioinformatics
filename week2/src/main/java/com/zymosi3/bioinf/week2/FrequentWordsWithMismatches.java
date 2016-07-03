package com.zymosi3.bioinf.week2;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class FrequentWordsWithMismatches {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        int k = scanner.nextInt();
        int d = scanner.nextInt();
        System.out.println(genome.frequentWordsWithMismatches(k, d));
    }
}
