package com.zymosi3.bioinf;

import java.util.stream.Stream;

/**
 * Genome symbol
 */
public enum Nucleotide {

    A(0, 'A'), // adenine
    C(1, 'C'), // cytosine
    G(2, 'G'), // guanine
    T(3, 'T'); // thymine

    public final int value;
    public final char name;

    Nucleotide(int value, char name) {
        this.value = value;
        this.name = name;
    }

    public Nucleotide complementary() {
        switch (this) {
            case A:
                return T;
            case C:
                return G;
            case G:
                return C;
            case T:
                return A;
        }
        throw new IllegalStateException();
    }

    public static Nucleotide byName(char name) {
        switch (name) {
            case 'a':
            case 'A':
                return A;
            case 'c':
            case 'C':
                return C;
            case 'g':
            case 'G':
                return G;
            case 't':
            case 'T':
                return T;
        }
        throw new IllegalArgumentException("Unknown name");
    }

    public static Nucleotide byName(int name) {
        return byName((char) name);
    }

    public static Nucleotide byValue(int value) {
        switch (value) {
            case 0:
                return A;
            case 1:
                return C;
            case 2:
                return G;
            case 3:
                return T;
        }
        throw new IllegalArgumentException("Unknown value");
    }

    public static Stream<Nucleotide> stream() {
        return Stream.of(values());
    }
}
