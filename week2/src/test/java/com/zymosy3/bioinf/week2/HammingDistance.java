package com.zymosy3.bioinf.week2;

import com.zymosy3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class HammingDistance {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome g1 = new Genome(scanner.next());
        Genome g2 = new Genome(scanner.next());
        System.out.println(g1.hammingDistance.apply(g2));
    }
}
