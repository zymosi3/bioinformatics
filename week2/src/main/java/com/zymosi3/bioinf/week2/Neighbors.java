package com.zymosi3.bioinf.week2;

import com.zymosi3.bioinf.Genome;
import com.zymosi3.bioinf.Util;

import java.util.Scanner;

/**
 *
 */
public class Neighbors {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome g = new Genome(scanner.next());
        int d = scanner.nextInt();
        System.out.println(Util.toColumn(g.neighbors(d)));
    }
}
