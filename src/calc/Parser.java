package calc;

import java.io.IOException;

public class Parser {
    private char lookhead;

    public void parse() throws Exception {
        scan();
        expression();
    }

    private void scan() throws IOException {
        lookhead = (char) System.in.read();
    }

    private void print(String s) {
        System.out.println(s);
    }

    private void error(String s) throws Exception {
        throw new Exception(String.format("error: expected %s", s));
    }

    private void match(char c) throws Exception {
        if (c == lookhead) {
            print(String.format("match %s", c));
            scan();
        } else {
            error("" + c);
        }
    }

    private void factor() throws Exception {
        if (isDigit(lookhead)) {
            // match a digit
            match(lookhead);
        } else if (lookhead == '(') {
            match('(');
            expression();
            match(')');
        } else {
            error("digit or '('");
        }
    }

    private void term() throws Exception {
        factor();
        while (true) { // remove the left recursive
            if (lookhead == '*') {
                match('*');
                factor();
            } else if (lookhead == '/') {
                match('/');
                factor();
            } else {
                break;
            }
        }
    }

    private void expression() throws Exception {
        term();
        while (true) {
            if (lookhead == '+') {
                match('+');
                term();
            } else if (lookhead == '-') {
                match('-');
                term();
            } else {
                break;
            }
        }
    }

    private boolean isDigit(char lookhead) {
        return '0' <= lookhead && lookhead <= '9';
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        parser.parse();
    }
}
