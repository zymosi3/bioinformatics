package com.zymosi3.bioinf.week1;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class PatternCount {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        Genome pattern = new Genome(scanner.next());
        System.out.println(genome.patternCount.apply(pattern));
    }
}
