import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    private static long prime = 372036854775801l;
    private static long multiplier = 330;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static long polyHash(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return hash;
    }

    private static long[] precomputeHashes(String T, int Tl, int Pl){
        long[] H = new long[(Tl - Pl + 1)];
        String S = T.substring(0, Pl);
        System.out.println(S + " : " + polyHash(S));
        H[(Tl - Pl)] = polyHash(S);
        long y = 1;
        for(int i=0; Pl < i; i++){
            y = (y * multiplier) % prime;
        }
        int counter = Tl - Pl - 1;
        for(int i=counter; i >= 0; i--){
            H[i] = (multiplier * H[i + 1] + T.charAt(i) - y * T.charAt(i + Pl)) % prime;
        }

        return H;
    }

    private static boolean areEqual(String partialText, String P){
        for(int i=0; i < P.length(); i++){
            if(partialText.charAt(i) != P.charAt(i)){
                return false;
            }
        }

        return true;
    }

    private static ArrayList<Integer> RabinKarp(String P, String T){
        ArrayList<Integer> result = new ArrayList<Integer>();
        int Pl = P.length(), Tl = T.length();
        long pHash = polyHash(P);
        System.out.println("PaternHash > " + pHash);
        long[] H = precomputeHashes(T, Tl, Pl);        

        for(int i = 0; i < (Tl - Pl); i++){
            System.out.println(">>>" + H[i]);
            if(pHash != H[i]){
                continue;
            }
            if(areEqual(T.substring(i, Pl), P)){
                result.add(i);
            }
        }

        return result;
    }

    private static List<Integer> getOccurrences(Data input) {
        String P = input.pattern, T = input.text;
        List<Integer> occurrences = RabinKarp(P, T);

        return occurrences;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

