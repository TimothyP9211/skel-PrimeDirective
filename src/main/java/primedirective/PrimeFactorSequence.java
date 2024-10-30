package primedirective;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimeFactorSequence {
    private List<Integer> primes;
    private int upperBound;

    /**
     * Create a PrimeFactorSequence object with a defined upperbound.
     *
     * @param _upperBound the upper bound for sequences and primes in this instance,
     * {@code upperBound > 0}.
     */
    public PrimeFactorSequence(int _upperBound) {
        upperBound = _upperBound;
        primes = Primes.getPrimes(upperBound);
    }

    /**
     * Obtain a sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     *
     * @return sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     */
    public List<Integer> primeFactorSequence() {
        List<Integer> seq = new ArrayList<>();
        List<Integer> primes = Primes.getPrimes(upperBound);
        for (int i = 0; i <= upperBound; i++) {
            int primeCount = 0;
            int number = i;
            for(Integer prime : primes) {
                if (i == 0) {
                    break;
                } else {
                    while (number % prime == 0) {
                        number = number / prime;
                        primeCount++;
                    }
                }
            }
            seq.add(primeCount);
        }
        return seq;
    }

    /**
     * Obtain a sequence L that is sorted in ascending order and where L[i] has exactly m
     * prime factors (including repeated factors) and L[i] <= upper bound
     *
     * @param m the number of prime factors that each element of the output sequence has
     * @return a sequence L that is sorted in ascending order and where L[i] has exactly
     * m prime factors (including repeated factors) and L[i] <= upper bound
     */
    public List<Integer> numbersWithMPrimeFactors(int m) {
        List<Integer> seq = new ArrayList<>();
        List<Integer> PFS = primeFactorSequence();
        for (int i = 0; i <= upperBound; i++) {
            if (PFS.get(i) == m) {
                seq.add(i);
            }
        }
        return seq;
    }

    /**
     * Obtain a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly m prime factors
     * (including repeated factors), and where |Sa - Sb| <= gap and where Sa and Sb are
     * adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     *
     * @param m   the number of prime factors that each element in the output sequence has
     * @param gap the maximum gap between two adjacent entries of interest in the output
     *            of {@code numbersWithMPrimeFactors(m)}
     * @return a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly
     * m prime factors (including repeated factors), and where |Sa - Sb| <= gap and where
     * Sa and Sb are adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     */
    public List<IntPair> numbersWithMPrimeFactorsAndSmallGap(int m, int gap) {
        List<IntPair> listOfPairs = new ArrayList<>();
        List<Integer> MPFS = numbersWithMPrimeFactors(m);
        for (int i = 0; i < MPFS.size() - 1; i++) {
            if (MPFS.get(i + 1) - MPFS.get(i) <= gap) {
                listOfPairs.add(new IntPair(MPFS.get(i), MPFS.get(i + 1)));
            }
        }
        return listOfPairs;
    }

    /**
     * Transform n to a larger prime (unless n is already prime) using the following steps:
     * <p>
     *     <ul>
     *         <li>A 0-step where we obtain 2 * n + 1</li>,
     *         <li>or a 1-step where we obtain n + 1</li>.
     *     </ul>
     *      We can apply these steps repeatedly, with more details in the problem statement.
     * </p>
     * @param n the number to transform
     * @return a string representation of the smallest transformation sequence
     */
    public String changeToPrime(int n) {
        Set<String> sequences = new HashSet<>();
        List<Integer> primes = Primes.getPrimes(upperBound);
        String s1 = makePrime(n, "", primes, 0, sequences);
        String s2 = makePrime(n, "", primes, 1, sequences);
        String shortest = "-";

        int minLen = Integer.MAX_VALUE;
        for (String s : sequences) {
            if (s.length() < minLen) {
                minLen = s.length();
                shortest = s;
            } else if (s.length() == minLen) {
                int n1 = StringToBinary(shortest);
                int n2 = StringToBinary(s);
                if (n2 < n1) {
                    shortest = s;
                }
            }
        }

        return shortest;
    }

    private String makePrime(int n, String sequence, List<Integer> primes, int seqType, Set<String> sequences) {
        if (n > upperBound) {
            return "-";
        }
        if (primes.contains(n)) {
           sequences.add(sequence);
           return sequence;
        }
        if (seqType == 0) {
            n = 2 * n + 1;
            sequence = sequence.concat("0");
        }
        if (seqType == 1) {
            n = n + 1;
            sequence = sequence.concat("1");
        }
        makePrime(n, sequence, primes, 0, sequences);
        makePrime(n , sequence, primes, 1, sequences);

        return sequence;
    }

    private int StringToBinary(String s) {
        char[] chars = s.toCharArray();
        int power = s.length() - 1;
        int sum = 0;
        for (char c : chars) {
            if (c == '1') {
                sum += (int) Math.pow(2, power);
            }
            power--;
        }
        return sum;
    }



}
