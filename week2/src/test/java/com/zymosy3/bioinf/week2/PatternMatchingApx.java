package com.zymosy3.bioinf.week2;

import com.zymosy3.bioinf.Genome;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class PatternMatchingApx {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome pattern = new Genome(scanner.next());
        Genome genome = new Genome(scanner.next());
        int d = scanner.nextInt();
        System.out.println(
                IntStream.of(genome.patternMatchingApx(pattern, d)).
                        mapToObj(String::valueOf).
                        collect(Collectors.joining(" "))
        );
    }
}
