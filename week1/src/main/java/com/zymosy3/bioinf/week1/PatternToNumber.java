package com.zymosy3.bioinf.week1;

import com.zymosy3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class PatternToNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.next();
        System.out.println(Genome.patternToNumber(pattern));
    }
}
