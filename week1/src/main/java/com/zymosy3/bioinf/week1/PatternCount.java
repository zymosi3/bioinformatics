package com.zymosy3.bioinf.week1;

import com.zymosy3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class PatternCount {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        Genome genome = new Genome(s);
        String pattern = scanner.next();
        System.out.println(genome.count(pattern));
    }
}
