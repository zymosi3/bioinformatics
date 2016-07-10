package com.zymosi3.bioinf.week4;

import com.zymosi3.bioinf.Dna;
import com.zymosi3.bioinf.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.zymosi3.bioinf.Util.iterateToMin;

/**
 *
 */
public class RandomizedMotifSearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int t = scanner.nextInt();
        List<Genome> genomes = new ArrayList<>();
        while (scanner.hasNext()) genomes.add(new Genome(scanner.next()));
        Dna dna = new Dna(genomes);
        Random r = new Random();
        System.out.println(iterateToMin(1000, () -> dna.randomizedMotifSearch(r, k), d -> d.score(k)));
    }
}
