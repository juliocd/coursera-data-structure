import java.io.*;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    private int size;
    private long[] threads;
    private int[] workers;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];

        // Set up threads
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        threads = new long[numWorkers + 1];
        workers = new int[numWorkers + 1];

        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();

            int timeProcessing = 0;
            if(i < numWorkers){
                timeProcessing = jobs[i];
                assignedWorker[i] = i;
                startTime[i] = 0;
                threads[i + 1] = timeProcessing;
                workers[i + 1] = i + 1;
            }
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // Processing jobs
        if(jobs.length <= numWorkers){
            return;
        }

        size = numWorkers;
        int jobIndex = numWorkers;
        while (jobIndex < jobs.length){
            // Organize heap
            for(int j = (size/2); j > 0; j--){
                siftDown(j);
            }

            // Update thread
            assignedWorker[jobIndex] = workers[1] - 1;
            startTime[jobIndex] = threads[1];
            threads[1] += jobs[jobIndex];
            jobIndex++;
        }
    }

    boolean compareWorkers(int idx1, int idx2){
        if(threads[idx1] != threads[idx2]){
            return threads[idx1] < threads[idx2];
        }{
            return workers[idx1] < workers[idx2];
        }
    }

    private void siftDown(int i){
        int minIndex = i;
        int l = getLeftChild(i);
        int r = getRightChild(i);

        if (l <= size && compareWorkers(l, minIndex)){
            minIndex = l;
        }
        if (r <= size && compareWorkers(r, minIndex)){
            minIndex = r;
        }
  
        if(i != minIndex){

            // Update thread
            long tmp = threads[i];
            threads[i] = threads[minIndex];
            threads[minIndex] = tmp;

            // Update workers
            int tmp1 = workers[i];
            workers[i] = workers[minIndex];
            workers[minIndex] = tmp1;

            siftDown(minIndex);
        }
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
        assignJobs();
        writeResponse();
        out.close();
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
