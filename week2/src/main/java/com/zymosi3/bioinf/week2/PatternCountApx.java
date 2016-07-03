package com.zymosi3.bioinf.week2;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class PatternCountApx {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome pattern = new Genome(scanner.next());
        Genome genome = new Genome(scanner.next());
        int d = scanner.nextInt();
        System.out.println(genome.patternCountApx(pattern, d));
    }
}
