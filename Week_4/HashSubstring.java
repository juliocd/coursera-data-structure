import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    private static int p = 1000000007;
    private static int x = 236;

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

    private static List<Integer> getOccurrences(Data input) {
        String P = input.pattern, T = input.text;
        List<Integer> result = new ArrayList<Integer>();
        int tLength = T.length(), pLength = P.length();

        // Get poly hashes
        long pHash = PolyHash(P);

        // Precompute hashes
        Long[] H = PrecomputeHashes(T, pLength);

        // Comapare hashes
        for(int i = 0; i <= (tLength - pLength); i++){
            if(pHash != H[i]){
                continue;
            }else{
                if(AreEqual(T.substring(i, i + pLength), P)){
                    result.add(i);
                }
            }
        }

        return result;
    }

    private static Boolean AreEqual(String S1, String S2){
        return S1.equals(S2);
    }

    private static long PolyHash(String P){
        long hash = 0;
        Integer pLength = P.length();
        for (Integer i = pLength - 1; i >= 0; i--){
            hash = (hash * x + P.charAt(i)) % p;
        }

        return hash;
    }

    private static Long[] PrecomputeHashes(String T, Integer pLength){
        int tLength = T.length();
        Long[] H = new Long[tLength - pLength + 1];
        String S = T.substring(tLength - pLength, tLength);
        H[tLength - pLength] = PolyHash(S);
        long y = 1;
        for(int i = 1; i <= pLength; i++){
            y = (y * x) % p;
        }
        for(int i = (tLength - pLength - 1); i >= 0; i--){
            long hashValue = x*H[i + 1] + T.charAt(i) - y*T.charAt(i + pLength);
            H[i] = hashValue > 0 ? (hashValue % p) : ((hashValue % p) + p) % p;
        }

        return H;
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

