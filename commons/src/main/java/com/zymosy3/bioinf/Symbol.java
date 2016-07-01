package com.zymosy3.bioinf;

import java.util.stream.Stream;

/**
 * Genome symbol
 */
public enum Symbol {

    A(0, 'A'),
    C(1, 'C'),
    G(2, 'G'),
    T(3, 'T');

    public final int value;
    public final char name;

    Symbol(int value, char name) {
        this.value = value;
        this.name = name;
    }

    public Symbol complementary() {
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

    public static Symbol byName(char name) {
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

    public static Symbol byName(int name) {
        return byName((char) name);
    }

    public static Symbol byValue(int value) {
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

    public static char complementary(char c) {
        return byName(c).complementary().name;
    }
}
