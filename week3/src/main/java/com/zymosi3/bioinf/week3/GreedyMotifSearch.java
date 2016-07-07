package com.zymosi3.bioinf.week3;

import com.zymosi3.bioinf.Dna;
import com.zymosi3.bioinf.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class GreedyMotifSearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        List<Genome> genomes = new ArrayList<>();
        while (scanner.hasNext()) genomes.add(new Genome(scanner.next()));
        Dna dna = new Dna(genomes);
        System.out.println(dna.greedyMotifSearch(k));
    }
}
