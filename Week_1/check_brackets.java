import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        if(text.length() == 1){
            System.out.println("1");
            return;
        }

        String result = "Success";
        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                opening_brackets_stack.push(new Bracket(next, position));
            } else if (next == ')' || next == ']' || next == '}') {
                if(opening_brackets_stack.isEmpty()){
                    result = ((Integer) (position + 1)).toString();
                    break;
                }

                Bracket top = opening_brackets_stack.peek();
                if(top.Match(next)){
                    opening_brackets_stack.pop();
                }else{
                    result = ((Integer) (position + 1)).toString();
                    break;
                }
            }
        }

        if(opening_brackets_stack.size() > 0 && result == "Success"){
            result = ((Integer) (opening_brackets_stack.get(opening_brackets_stack.size() - 1).position + 1)).toString();
        }

        System.out.println(result);
    }
}
