package com.zymosy3.bioinf.week1;

import com.zymosy3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class Complementary {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        Genome genome = new Genome(s);
        System.out.println(genome.complementary());
    }
}
