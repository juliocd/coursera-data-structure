import java.util.*;
import java.io.*;

public class is_bst_hard {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;
            String side = "";

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }

            void setSide(String side){
                this.side = side;
            }

            String getSide(){
                return this.side;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            return tree.length == 0 ? true : LevelTraversal(tree[0]);
        }

        private boolean LevelTraversal(Node node){
            if(node == null){
                return false;
            }
            
            int rootKey = node.key;
            boolean isBTSTree = true;
            Queue<Node> q = new LinkedList<Node>();
            q.add(node);
    
            while(!q.isEmpty()){
                Node currentNode = q.poll();
                // System.out.println("Node:" + currentNode.key);

                if(currentNode.left != -1){
                    Node leftNode = tree[currentNode.left];
                    if((currentNode.key <= leftNode.key) || (currentNode.side.equals("right") && (rootKey >= leftNode.key))){
                        isBTSTree = false;
                        break;
                    }
                    // System.out.println("L:" + leftNode.key);

                    if(currentNode.side.equals("")){
                        leftNode.side = "left";
                    }else {
                        leftNode.side = currentNode.side;
                    }

                    q.add(leftNode);
                }
                if(currentNode.right != -1){
                    Node rightNode = tree[currentNode.right];
                    if((currentNode.key > rightNode.key) || (currentNode.side.equals("left") && (rootKey < rightNode.key))){
                        isBTSTree = false;
                        break;
                    }
                    // System.out.println("R:" + rightNode.key);

                    if(currentNode.side.equals("")){
                        rightNode.side = "right";
                    }else {
                        rightNode.side = currentNode.side;
                    }

                    q.add(rightNode);
                }
            }

            return isBTSTree;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
