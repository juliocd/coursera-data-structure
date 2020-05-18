import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;
    private int size;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt() + 1;
        data = new int[n];
        data[0] = 0;
        for (int i = 1; i < n; i++) {
          data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
      swaps = new ArrayList<Swap>();      
      arrayToHeap(data);
    }

    private void arrayToHeap(int[] arr){
      size = arr.length - 1;
      for(int i = (size/2); i > 0; i--){
        siftDown(i);
      }
    }

    private void siftDown(int i){
      int minIndex = i;

      int l = getLeftChild(i);
      if (l <= size && (data[l] < data[minIndex])){
        minIndex = l;
      }

      int r = getRightChild(i);
      if((r <= size) && (data[r] < data[minIndex])){
        minIndex= r;
      }

      if(i != minIndex){
        swaps.add(new Swap(i - 1, minIndex - 1));
        int tmp = data[i];
        data[i] = data[minIndex];
        data[minIndex] = tmp;
        siftDown(minIndex);
      }
    }

    private void siftUp(int i){
      while (i > 1 && (data[getParent(i)] < data[i])){
        int j = getParent(i);
        swaps.add(new Swap(data[j], data[i]));
        int tmp = data[j];
        data[j] = data[i];
        data[i] = tmp;
        i = j;
      }
    }

    private int getParent(int i){
      return (i / 2);
    }

    private int getLeftChild(int i){
      return 2 * i;
    }

    private int getRightChild(int i){
      return (2 * i) + 1;
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
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
