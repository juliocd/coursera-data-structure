import java.util.*;
import java.io.*;

public class substring_equality {

	private static long p1 = 1000000007;
	private static long p2 = 1000000009;
	private static long x = 324;

	public class Solver {
		private String s;
		public Solver(String s) {
			this.s = s;
		}
		public boolean ask(int a, int b, int l) {
			int n = s.length();
			long[] h1 = new long[n + 1];
			long[] h2 = new long[n + 1];
			h1[0] = 0L;
			h2[0] = 0L;

			// Precompute hash for all string per each prime
			for(int i = 1; i <=n; i++){
				// For p1
				long hashValue = x * h1[i - 1] + s.charAt(i - 1);
				h1[i] = ((hashValue % p1) + p1) % p1;

				// For p2
				hashValue = x * h2[i - 1] + s.charAt(i - 1);
				h2[i] = ((hashValue % p2) + p2) % p2;
			}

			// Compute hashes per position
			long aHash1 = HashValue(h1, p1, a, l);
			long aHash2 = HashValue(h2, p2, a, l);
			long bHash1 = HashValue(h1, p1, b, l);
			long bHash2 = HashValue(h2, p2, b, l);

			return (aHash1 == bHash1) && (aHash2 == bHash2);
		}
	}

	public long HashValue(long[] H, long prime, int start, int n){
		long y = 1;
		for(int i = 1; i <= n; i++){
            y = (y * x) % prime;
		}
		long hValue = H[start + n] - y * H[start];

		return ((hValue % prime) + prime) % prime;
	}

	public void run() throws IOException {
		FastScanner in = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		String s = in.next();
		int q = in.nextInt();
		Solver solver = new Solver(s);
		for (int i = 0; i < q; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int l = in.nextInt();
			out.println(solver.ask(a, b, l) ? "Yes" : "No");
		}
		out.close();
	}

	static public void main(String[] args) throws IOException {
	    new substring_equality().run();
	}

	class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
