import java.util.*;
import java.io.*;

public class StackWithMax {
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

    class MaxItem {
        int value_ = 0;
        int stack_index_ = -1;
    }

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        Stack<Integer> stack = new Stack<Integer>();
        List<Integer> maxRequests = new ArrayList<>();
        List<MaxItem> sortStack = new ArrayList<MaxItem>();
        int maxValue = -1;

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);
                if(value >= maxValue){
                    maxValue = value;
                }

                MaxItem item = new MaxItem();
                item.value_ = value;
                item.stack_index_ = stack.size() - 1;

                // Init
                if(sortStack.size() == 0){
                    sortStack.add(item);
                    continue;
                }

                // Lower limit
                if(item.value_ <= sortStack.get(0).value_){
                    sortStack.add(0, item);
                    continue;
                }

                // Unique value
                if(sortStack.size() == 1){
                    if(sortStack.get(0).value_ >= item.value_){
                        sortStack.add(0, item);
                    }else{
                        sortStack.add(item);
                    }
                    continue;
                }

                // Upper limit
                if(item.value_ >= sortStack.get(sortStack.size() - 1).value_){
                    sortStack.add(item);
                    continue;
                }

                // Interval values
                for(int k=sortStack.size(); k>1;k--){
                    if(sortStack.get(k-1).value_ <= value && sortStack.get(k-2).value_ >= value){
                        sortStack.add(k, item);
                        break;
                    }
                }
            } else if ("pop".equals(operation)) {
                if(stack.size() > 0){
                    int itemToRemove = stack.peek();
                    int itemToRemoveIndex = stack.size() - 1;
                    MaxItem maxItem = sortStack.get(sortStack.size() - 1);
                    stack.pop();

                    if(itemToRemove == maxItem.value_ && itemToRemoveIndex == maxItem.stack_index_){
                        sortStack.remove(maxItem);
                        maxValue = sortStack.get(sortStack.size() - 1).value_;
                    }
                }
            } else if ("max".equals(operation)) {
                maxRequests.add(maxValue);
            }
        }

        for(int i = 0; i < maxRequests.size(); i++){
            System.out.println(maxRequests.get(i));
        }
    }

    static public void main(String[] args) throws IOException {
        new StackWithMax().solve();
    }
}
