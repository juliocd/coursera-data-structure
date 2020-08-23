import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private LinkedList<String> elems;
    // for hash function
    private int bucketCount; //m
    private int prime = 1000000007; //p
    private int multiplier = 263; //x

    // Bookphone list
    LinkedList[] listHash;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void addRecord(Query query){
        // Retrieve chain
        int hashValue = hashFunc(query.s);
        LinkedList<String> chainRecords = listHash[hashValue];
        String newRecord = query.s;

        if(!chainRecords.contains(newRecord)){
            chainRecords.addFirst(newRecord);
            listHash[hashValue] = chainRecords;
        }
    }

    private boolean findRecord(Query query){
        // Retrieve chain
        int hashValue = hashFunc(query.s);
        LinkedList<String> chainRecords = listHash[hashValue];

        if(!chainRecords.isEmpty()){
            for(String item: chainRecords){
                if(item.equals(query.s)){
                    return true;
                }
            }
        }

        return false;
    }

    private void deleteRecord(Query query){
        // Retrieve chain
        int hashValue = hashFunc(query.s);
        LinkedList<String> chainRecords = listHash[hashValue];

        if(!chainRecords.isEmpty()){
            for(String item: chainRecords){  
                if(item.equals(query.s)){
                    chainRecords.remove(item);
                    listHash[hashValue] = chainRecords;
                    break;
                }
            }
        }
    }

    private LinkedList<String> checkRecord(int hashValue){
        // Retrieve chain
        LinkedList<String> chainRecords = listHash[hashValue];

        if(!chainRecords.isEmpty()){
            return chainRecords;
        }

        return new LinkedList<String>();
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                addRecord(query);
                break;
            case "del":
                deleteRecord(query);
                break;
            case "find":
                writeSearchResult(findRecord(query));
                break;
            case "check":
                elems = checkRecord(query.ind);
                for (String cur : elems)
                    if (hashFunc(cur) == query.ind)
                        out.print(cur + " ");
                out.println();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        
        listHash = new LinkedList[bucketCount];
        for(int i = 0; i < bucketCount; i++){
            listHash[i] = new LinkedList<String>();
        }

        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
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
