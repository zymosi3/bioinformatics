
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Main {

    public static class Genome {

        public final String s;

        public Genome(String s) {
            this.s = Objects.requireNonNull(s);
        }

        private static final Function<Genome, Function<Genome, Integer>> hammingDistance = g1 -> g2 ->
                IntStream.range(0, g1.s.length()).
                        map(i -> g1.s.charAt(i) == g2.s.charAt(i) ? 0 : 1).
                        sum();

        public static final Function<Genome, Function<Integer, Set<Genome>>> neighbors =
                g -> d -> {
                    if (d == 0) {
                        return Collections.singleton(g);
                    }
                    if (g.size() == 1) {
                        return Stream.of(Nucleotide.values()).
                                map(Nucleotide::name).
                                map(Genome::new).
                                collect(Collectors.toSet());
                    }
                    return g.suffix().neighbors(d).stream().
                            flatMap(n -> g.suffix().hammingDistance(n) < d ?
                                    Stream.of(Nucleotide.values()).map(n::addFirst) :
                                    Stream.of(n.addFirst(g.first()))).
                            collect(Collectors.toSet());
                };

        public int size() {
            return s.length();
        }

        public Genome chunk(int i, int len) {
            return new Genome(s.substring(i, i + len));
        }

        public Genome suffix() {
            return chunk(1, size() - 1);
        }

        public Nucleotide at(int i) {
            return Nucleotide.byName(s.charAt(i));
        }

        public Nucleotide first() {
            return at(0);
        }

        public Genome addFirst(Nucleotide s) {
            return new Genome(s.name() + this.s);
        }

        public int hammingDistance(Genome g) {
            return hammingDistance.apply(this).apply(g);
        }

        public Set<Genome> neighbors(int d) {
            return neighbors.apply(this).apply(d);
        }

        @Override
        public String toString() {
            return s;
        }
    }

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
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Genome g = new Genome(scanner.next());
        int d = scanner.nextInt();
        System.out.println(g.neighbors(d).stream().map(Genome::toString).collect(Collectors.joining("\n")));
    }
}