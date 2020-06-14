package lexer;

public abstract class Token {
    final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();
}
