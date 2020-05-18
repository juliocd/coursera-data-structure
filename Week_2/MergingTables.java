import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    private Table[] tables;
    private int[] parents;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    class Table {
        Table parent;
        int rank;
        int numberOfRows;
        int id;

        Table(int numberOfRows, int id) {
            this.id = id;
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }
        Table getParent() {
            Table p = this.parent;
            while (p != p.parent) {
                p = p.parent;
            }
            return p.parent;
        }
    } 

    int maximumNumberOfRows = -1;

    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        Table realSource = source.getParent();
        if (realDestination == realSource) {
            return;
        }
        union(realDestination, realSource);
    }

    void union(Table realDestination, Table realSource){
        if(realDestination.id == realSource.id){
            return;
        }

        int totalRows = 0;
        if(realDestination.rank > realSource.rank){
            realSource.parent = realDestination;

            // Update rows
            realDestination.numberOfRows += realSource.numberOfRows;
            realSource.numberOfRows = 0;
            totalRows = realDestination.numberOfRows;
        }else{
            realDestination.parent = realSource;

            // Update rows
            realSource.numberOfRows += realDestination.numberOfRows;
            realDestination.numberOfRows = 0;
            totalRows = realSource.numberOfRows;

            // Update rank
            if(realDestination.rank == realSource.rank){
                realSource.rank += 1;
            }
        }
        maximumNumberOfRows = Math.max(maximumNumberOfRows, totalRows);
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();
        tables = new Table[n];
        parents = new int[n+1];
        for (int i = 0; i < n; i++) {
            int id = i + 1;
            int numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows, id);
            parents[i+1] = id;
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }

        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(tables[destination], tables[source]);
            writer.printf("%d\n", maximumNumberOfRows);
        }
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
