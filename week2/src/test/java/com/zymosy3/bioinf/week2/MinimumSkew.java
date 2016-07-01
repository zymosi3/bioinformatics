package com.zymosy3.bioinf.week2;

import com.zymosy3.bioinf.Genome;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class MinimumSkew {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        System.out.println(
                IntStream.of(genome.minimumSkew.get()).
                        mapToObj(String::valueOf).
                        collect(Collectors.joining(" "))
        );
    }
}
