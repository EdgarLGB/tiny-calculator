package lexer;

import java.util.Objects;

public class Identifier extends Token {

    private final String lexeme;

    public Identifier(int tag, String lexeme) {
        super(tag);
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "lexeme='" + lexeme + '\'' +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(lexeme, that.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexeme);
    }
}
