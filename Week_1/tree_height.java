import java.util.*;
import java.io.*;

public class tree_height {
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

	public class Node {
		List<Integer> children = new ArrayList<Integer>();
		int level = 0;

		void addChildren(Integer child){
			children.add(child);
		}

		List<Integer> getChildren(){
			return children;
		}
	}

	public class TreeHeight {
		int n;
		int parent[];
		Node[] nodeTree;
		int height = 1;
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
			}
		}

		int computeHeight() {
			if(n == 1){
				return height;
			}

            nodeTree = new Node[n];
			int rootIndex = -1;
			for (int i = 0; i < parent.length; i++) {
				if(parent[i] == -1){
					rootIndex = i;
					nodeTree[rootIndex] = new Node();
				}else if(nodeTree[parent[i]] == null){
					nodeTree[parent[i]] = new Node();
					nodeTree[parent[i]].addChildren(i);
				}else{
					nodeTree[parent[i]].addChildren(i);
				}
			}

			Node root = nodeTree[rootIndex];
			root.level = 1;
			evalNode(root, 1);
			return height + 1;
		}

		int evalNode(Node node, int level) {
			List<Integer> children = node.getChildren();
			for(int i : children){
				Node child = nodeTree[i];
				if(child == null){
					continue;
				}
				child.level = node.level + 1;
				height = Math.max(height, child.level);
				evalNode(child, level);
			}
			return level;
		}
	}

	static public void main(String[] args) throws IOException {
		new Thread(null, new Runnable() {
				public void run() {
					try {
						new tree_height().run();
					} catch (IOException e) {
					}
				}
			}, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		System.out.println(tree.computeHeight());
	}
}
