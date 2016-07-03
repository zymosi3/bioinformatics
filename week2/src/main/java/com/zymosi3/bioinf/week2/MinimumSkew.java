package com.zymosi3.bioinf.week2;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class MinimumSkew {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) sb.append(scanner.next());
        Genome genome = new Genome(sb.toString());
        System.out.println(
                IntStream.of(genome.minimumSkew()).
                        mapToObj(String::valueOf).
                        collect(Collectors.joining(" "))
        );
    }
}
