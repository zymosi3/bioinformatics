package com.zymosi3.bioinf.week1;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;

/**
 *
 */
public class FrequentWords {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        int len = scanner.nextInt();
        System.out.println(genome.frequentWords(len));
    }
}
