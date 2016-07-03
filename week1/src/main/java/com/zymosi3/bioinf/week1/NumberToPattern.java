package com.zymosi3.bioinf.week1;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class NumberToPattern {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long number = scanner.nextLong();
        int k = scanner.nextInt();
        System.out.println(Genome.numberToPattern(number, k));
    }
}
