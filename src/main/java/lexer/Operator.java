package lexer;

import java.util.Objects;

public class Operator extends Token {

    private final String op;

    public Operator(String op) {
        super(Tags.OPERATOR);
        this.op = op;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "op='" + op + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(op, operator.op);
    }

    @Override
    public int hashCode() {
        return Objects.hash(op);
    }
}
