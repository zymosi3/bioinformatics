package com.zymosi3.bioinf.week1;

import com.zymosi3.bioinf.Genome;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class Clumps {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        int k = scanner.nextInt();
        int l = scanner.nextInt();
        int t = scanner.nextInt();
        Set<String> clumps = genome.fasterClumpsOnMaps(k, l, t);
        System.out.println(clumps.stream().collect(Collectors.joining(" ")));
        System.out.println("Count: " + clumps.size());
    }
}
