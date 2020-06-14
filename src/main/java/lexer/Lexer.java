package lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Lexer {

    private final InputStream inputStream;  // where we read characters
    private char current;  // the current char
    private char lookahead;  // used as a register
    private final Map<String, Identifier> identifierMap;  // the mapping of lexeme -> <tag, lexeme>

    public Lexer(InputStream input) throws IOException {
        inputStream = input;
        current = '\0';
        lookahead = '\0';
        identifierMap = new HashMap<>();
        addKeywords(identifierMap);
    }

    private void addKeywords(Map<String, Identifier> identifierMap) {
        identifierMap.put("true", new Identifier(Tags.TRUE, "true"));
        identifierMap.put("false", new Identifier(Tags.FALSE, "false"));
    }

    private char getChar() throws IOException {
        return (char) inputStream.read();
    }

    private boolean isDigit(char lookhead) {
        return '0' <= lookhead && lookhead <= '9';
    }

    private boolean isCharacter(char lookhead) {
        return ('a' <= lookhead && lookhead <= 'z') || ('A' <= lookhead && lookhead <= 'Z');
    }

    private boolean isSeparator(char lookhead) {
        return lookhead == Separators.EMPTY_SPACE || lookhead == Separators.NEW_LINE || lookhead == Separators.TAB;
    }

    private void error(String s) throws Exception {
        throw new Exception(String.format("error: expected %s", s));
    }

    public Token scan() throws Exception {
        do {
            if (lookahead != '\0') {  // if there is something in the register, use it firstly
                current = lookahead;
                lookahead = '\0';
            } else {
                current = getChar();
            }
        } while (isSeparator(current));  // skip the space-wise separators

        Token res;
        if (isDigit(current) || current == '.') {  // read number token
            res = scanNumber();
        } else if (isCharacter(current)) {  // read identifier token (variables, keywords)
            res = scanIdentifier();
        } else if (current == '/') {
            current = getChar();
            if (current == '/') {  // skip the line comment
                res = scanLineComment();
            } else if (current == '*') {  // skip the block comment
                res = scanBlockComment();
            } else {
                throw new Exception(String.format("can not parse %s", current));
            }
        } else if (current == '<' || current == '>' || current == '!' || current == '=') {
            lookahead = getChar();
            if (lookahead == '=') {  // read <=, >=, !=, ==
                res = new Operator("" + current + lookahead);
                lookahead = '\0';
            } else if (current == '<' || current == '>'){  // read <, >
                res = new Operator("" + current);
            } else {
                throw new Exception(String.format("can not parse %s", current));
            }
        } else {
            throw new Exception(String.format("can not parse %s", current));
        }
        return res;
    }

    private Token scanBlockComment() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            current = getChar();
            if (current == '*') {
                lookahead = getChar();
                if (lookahead == '/')
                    break;
            }
            sb.append(current);
            if (lookahead != '\0') {
                sb.append(lookahead);
                lookahead = '\0';
            }
        }
        return new BlockComment(sb.toString());
    }

    private Token scanLineComment() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            current = getChar();
            if (current == '\n') {
                break;
            }
            sb.append(current);
        }
        return new LineComment(sb.toString());
    }

    private Token scanNumber() throws Exception {
        int i = 0;
        int frac = -1;
        if (current != '.') {
            // read integer part
            i = getNumber();
        }
        if (current == '.') {
            // read fractional part
            current = getChar();
            frac = getNumber();
        }
        if (!isSeparator(current)) {
            throw new Exception("can not parse " + current);
        }
        return new Number(i, frac);
    }

    private int getNumber() throws IOException {
        int res = 0;
        while (isDigit(current)) {
            res = res * 10 + current - '0';
            current = getChar();
        }
        return res;
    }

    private Token scanIdentifier() throws Exception {
        StringBuilder sb = new StringBuilder();
        do {  // Currently only support the identifiers made of characters and digits (but always starts with character)
            sb.append(current);
            current = getChar();
        } while (isCharacter(current) || isDigit(current));
        lookahead = current;

        String lexeme = sb.toString();
        Identifier id = identifierMap.get(lexeme);
        if (id == null) {
            id = new Identifier(Tags.ID, lexeme);
            identifierMap.put(lexeme, id);
        }
        return id;
    }
}
