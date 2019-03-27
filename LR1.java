import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class LR1 {
        
        //temp element to modify stack
        static Element temp;
        
        // queue to hold the expression
        static Queue<String> expression = new LinkedList<String>(); 
        
        //stack to hold the elements
        static Stack<Element> symbols = new Stack<Element>(); 

        /*
         * inner class to create objects to place into the stack
         */
        static class Element {
                int state;
                char symbol;
                int token;

                Element() {
                }

                Element(int state, char symbol, int token) {
                        this.state = state;
                        this.symbol = symbol;
                        this.token = token;
                }

                Element(int state, char symbol) {
                        this.state = state;
                        this.symbol = symbol;
                }
        }

        /**
         * main
         */
        public static void main(String args[]) {

                // tokenize the inputted expression and place it into the stack
                StringTokenizer st = new StringTokenizer(args[0], "+-*/()", true);

                // load the queue with the tokenized expression
                while (st.hasMoreTokens()) {
                        expression.add(st.nextToken());
                }
                expression.add("$");

                Element temp = new Element(0, '-');
                symbols.push(temp); // push state 0 into stack

                // loop while expression is valid
                boolean valid = true;
                while (valid) {

                        // switch case to execute actions dependent on state
                        switch (symbols.peek().state) {
                        case 0:
                                s0(valid);
                                break;

                        case 1:
                                s1(valid);
                                break;
                                
                        case 2:
                                s2(valid);
                                break;

                        case 3:
                                s3(valid);
                                break;

                        case 4:
                                s4(valid);
                                break;

                        case 5:
                                s5(valid);
                                break;

                        case 6:
                                s6(valid);
                                break;

                        case 7:
                                s7(valid);
                                break;
                                
                        case 8:
                                s8(valid);
                                break;

                        case 9:
                                s9(valid);
                                break;

                        case 10:
                                s10(valid);
                                break;

                        case 11:
                                s11(valid);
                                break;
                        }
                }
        }
        
        /*
         * state 0
         */
        public static void s0(boolean valid) {
                if (expression.peek().equals("(")) {
                        System.out.println("Are we here");
                        symbols.push(temp = new Element(4, '('));
                        expression.poll();
                        printStack();
                        //break;
                }

                // reduction
                else if (symbols.peek().symbol == 'E') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(1, 'E', temp.token));
                        printStack();
                        //break;
                }

                else if (symbols.peek().symbol == 'T') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(2, 'T', temp.token));
                        printStack();
                        //break;
                }

                else if (symbols.peek().symbol == 'F') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(3, 'F', temp.token));
                        printStack();
                        //break;
                }

                // if all else fails, expect an integer, else, invalid
                // expression
                else {
                        try {
                                Integer.parseInt(expression.peek());
                        }

                        catch (NumberFormatException e) {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }
                        symbols.push(temp = new Element(5, 'n',
                                        Integer.parseInt(expression.peek())));
                        expression.poll();
                        printStack();
                }
        }

        /*
         * state 1
         */
        public static void s1(boolean valid) {
                if (expression.peek().equals("-")
                                || expression.peek().equals("+")) {
                        symbols.push(
                                        temp = new Element(6, expression.peek().charAt(0)));
                        expression.poll();
                        printStack();
                }

                else if (expression.peek().equals("$")) {
                        System.out.print("Valid Expression, value = ");
                        valid = false;
                        System.out.println(symbols.pop().token);
                        System.exit(0);
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }

        }

        /*
         * state 2
         */
        public static void s2(boolean valid) {
                if ((expression.peek().equals("-")
                                || expression.peek().equals("+")
                                || expression.peek().equals(")")
                                || expression.peek().equals("$"))
                                && symbols.peek().symbol == 'T') {
                        temp = symbols.pop();
                        int tempState = symbols.peek().state;
                        symbols.push(
                                        temp = new Element(tempState, 'E', temp.token));
                        printStack();
                }

                else if (expression.peek().equals("*")
                                || expression.peek().equals("/")) {
                        symbols.push(
                                        temp = new Element(7, expression.peek().charAt(0)));
                        expression.poll();
                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * state 3
         */
        public static void s3(boolean valid) {
                if ((expression.peek().equals("-")
                                || expression.peek().equals("+")
                                || expression.peek().equals("*")
                                || expression.peek().equals("/")
                                || expression.peek().equals(")")
                                || expression.peek().equals("$"))
                                && symbols.peek().symbol == 'F') {
                        temp = symbols.pop();
                        int tempState = symbols.peek().state;
                        symbols.push(
                                        temp = new Element(tempState, 'T', temp.token));
                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * state 4
         */
        public static void s4(boolean valid) {
                if (expression.peek().equals("(")) {
                        symbols.push(temp = new Element(4, '('));
                        expression.poll();
                        printStack();
                }

                // reduction
                else if (symbols.peek().symbol == 'E') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(8, 'E', temp.token));
                        printStack();
                }

                else if (symbols.peek().symbol == 'T') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(2, 'T', temp.token));
                        printStack();
                }

                else if (symbols.peek().symbol == 'F') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(3, 'F', temp.token));
                        printStack();
                }

                else {
                        try {
                                Integer.parseInt(expression.peek());
                        }

                        catch (NumberFormatException e) {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }

                        symbols.push(temp = new Element(5, 'n',
                                        Integer.parseInt(expression.peek())));
                        expression.poll();
                        printStack();
                }
        }

        /*
         * state 5
         */
        public static void s5(boolean valid) {
                if ((expression.peek().equals("-")
                                || expression.peek().equals("+")
                                || expression.peek().equals("*")
                                || expression.peek().equals("/")
                                || expression.peek().equals(")")
                                || expression.peek().equals("$"))
                                && symbols.peek().symbol == 'n') {
                        temp = symbols.pop();
                        int tempState = symbols.peek().state;
                        symbols.push(
                                        temp = new Element(tempState, 'F', temp.token));
                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * state 6
         */
        public static void s6(boolean valid) {
                if (expression.peek().equals("(")) {
                        symbols.push(temp = new Element(4, '('));
                        expression.poll();
                        printStack();
                }

                // reduction
                else if (symbols.peek().symbol == 'T') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(9, 'T', temp.token));
                        printStack();
                }

                else if (symbols.peek().symbol == 'F') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(3, 'F', temp.token));
                        printStack();
                }

                else {
                        try {
                                Integer.parseInt(expression.peek());
                        }

                        catch (NumberFormatException e) {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }

                        symbols.push(temp = new Element(5, 'n',
                                        Integer.parseInt(expression.peek())));
                        expression.poll();
                        printStack();
                }
        }

        /*
         * state 7
         */
        public static void s7(boolean valid) {
                if (expression.peek().equals("(")) {
                        symbols.push(temp = new Element(4, '('));
                        expression.poll();
                        printStack();
                }

                // reduction
                else if (symbols.peek().symbol == 'F') {
                        temp = symbols.pop();
                        symbols.push(temp = new Element(10, 'F', temp.token));
                        printStack();
                }

                else {
                        try {
                                Integer.parseInt(expression.peek());
                        }

                        catch (NumberFormatException e) {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }
                        symbols.push(temp = new Element(5, 'n',
                                        Integer.parseInt(expression.peek())));
                        expression.poll();
                        printStack();
                }

        }

        /*
         * state 8
         */
        public static void s8(boolean valid) {
                if (expression.peek().equals("-")
                                || expression.peek().equals("+")) {
                        symbols.push(
                                        temp = new Element(6, expression.peek().charAt(0)));
                        expression.poll();
                        printStack();
                }

                else if (expression.peek().equals(")")) {
                        symbols.push(temp = new Element(11,
                                        expression.peek().charAt(0)));
                        expression.poll();
                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * state 9
         */
        public static void s9(boolean valid) {
                if ((expression.peek().equals("-")
                                || expression.peek().equals("+")
                                || expression.peek().equals(")")
                                || expression.peek().equals("$"))
                                && symbols.peek().symbol == 'T') {
                        temp = symbols.pop();

                        if (symbols.peek().symbol == '-') {
                                symbols.pop();
                                Element temp2 = symbols.pop();
                                int arith = temp2.token - temp.token;
                                symbols.push(temp = new Element(symbols.peek().state,
                                                'E', arith));
                                printStack();

                        }

                        else if (symbols.peek().symbol == '+') {
                                symbols.pop();
                                Element temp2 = symbols.pop();
                                int value = temp2.token + temp.token;
                                symbols.push(temp = new Element(symbols.peek().state,
                                                'E', value));
                                printStack();
                        }

                        else {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }
                }

                else if (expression.peek().equals("*")
                                || expression.peek().equals("/")) {
                        symbols.push(
                                        temp = new Element(7, expression.peek().charAt(0)));
                        expression.poll();
                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * state 10
         */
        public static void s10(boolean valid) {
                if ((expression.peek().equals("-")
                                || expression.peek().equals("+")
                                || expression.peek().equals(")")
                                || expression.peek().equals("$")
                                || expression.peek().equals("*")
                                || expression.peek().equals("/"))
                                && symbols.peek().symbol == 'F') {
                        temp = symbols.pop();

                        if (symbols.peek().symbol == '*') {
                                symbols.pop();
                                Element temp2 = symbols.pop();
                                int arith = temp2.token * temp.token;
                                symbols.push(temp = new Element(symbols.peek().state,
                                                'T', arith));
                        }

                        else if (symbols.peek().symbol == '/') {
                                symbols.pop();
                                Element temp2 = symbols.pop();
                                int arith = temp2.token / temp.token;
                                symbols.push(temp = new Element(symbols.peek().state,
                                                'T', arith));
                        }

                        else {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }

                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * state 11
         */
        public static void s11(boolean valid) {
                if ((expression.peek().equals("-")
                                || expression.peek().equals("+")
                                || expression.peek().equals(")")
                                || expression.peek().equals("$")
                                || expression.peek().equals("*")
                                || expression.peek().equals("/"))
                                && symbols.peek().symbol == ')') {
                        temp = symbols.pop();
                        if (symbols.peek().symbol == 'E') {
                                Element temp2 = symbols.pop();
                                symbols.pop();
                                symbols.push(temp = new Element(symbols.peek().state,
                                                'F', temp2.token));
                        }
                        else {
                                System.out.println("Invalid Expression");
                                System.exit(0);
                        }
                        printStack();
                }

                else {
                        System.out.println("Invalid Expression");
                        System.exit(0);
                }
        }

        /*
         * print the stack to the console
         */
        public static void printStack() {
                String output;

                for (int i = 0; i < symbols.size(); i++) {
                        output = "[" + String.valueOf(symbols.get(i).symbol) + ":"
                                        + String.valueOf(symbols.get(i).state) + "] ";
                        System.out.print(output);
                }

                for (int i = 0; i < expression.size(); i++) {
                        String temp = expression.poll();
                        System.out.print(temp + " ");
                        expression.offer(temp);
                }

                System.out.println();
        }
}