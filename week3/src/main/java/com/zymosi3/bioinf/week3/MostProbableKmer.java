package com.zymosi3.bioinf.week3;

import com.zymosi3.bioinf.Genome;
import com.zymosi3.bioinf.Nucleotide;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.zymosi3.bioinf.Util.toProfile;

/**
 *
 */
public class MostProbableKmer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome genome = new Genome(scanner.next());
        int k = scanner.nextInt();
        String profileStr = IntStream.range(0, 4).
                mapToObj(i -> IntStream.range(0, k).mapToObj(j -> scanner.next()).collect(Collectors.joining(" "))).
                collect(Collectors.joining("\n"));
        Map<Nucleotide, List<Double>> profile = toProfile(profileStr);
        System.out.println(genome.mostProbableKmer(profile, k));
    }
}
