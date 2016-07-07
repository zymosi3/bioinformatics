package com.zymosi3.bioinf.week3;

import com.zymosi3.bioinf.Dna;
import com.zymosi3.bioinf.Genome;
import com.zymosi3.bioinf.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 */
public class MotifEnumeration {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int d = scanner.nextInt();
        List<Genome> genomes = new ArrayList<>();
        while (scanner.hasNext()) genomes.add(new Genome(scanner.next()));
        Dna dna = new Dna(genomes);
        System.out.println(Util.toString(dna.motifEnumeration(k, d)));
    }
}
