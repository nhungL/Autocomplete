import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of
// string and weights, using Term and BinarySearchDeluxe.
public class Autocomplete {
    private final Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new NullPointerException();

        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++)
            this.terms[i] = terms[i];
        Arrays.sort(this.terms);
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();

        Term[] matches;                 //matching terms
        int length = numberOfMatches(prefix);
        matches = new Term[length];

        Term t = new Term(prefix);      //key
        int first = BinarySearchDeluxe.firstIndexOf(terms, t, Term.byPrefixOrder(prefix.length()));
        for (int i = 0; i < matches.length; i++)
            matches[i] = terms[first + i];

        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();

        Term t = new Term(prefix);      //key
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());

        int first = BinarySearchDeluxe.firstIndexOf(terms, t, prefixOrder);
        int last = BinarySearchDeluxe.lastIndexOf(terms, t, prefixOrder);

        return last - first + 1;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
