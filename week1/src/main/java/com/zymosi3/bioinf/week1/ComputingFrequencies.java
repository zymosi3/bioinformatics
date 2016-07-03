package com.zymosi3.bioinf.week1;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class ComputingFrequencies {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        int k = scanner.nextInt();
        System.out.println(IntStream.of(genome.computingFrequencies(k)).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
    }
}
