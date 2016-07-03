package com.zymosi3.bioinf.week1;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class PatternMatching {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.next();
        Genome genome = new Genome(scanner.next());
        System.out.println(IntStream.of(genome.patternMatching(pattern)).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
    }
}
