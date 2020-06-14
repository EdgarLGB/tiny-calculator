package lexer;

import java.util.Objects;

public class Number extends Token {

    private final int integer;    // the integer part
    private final int fraction;     // the fraction part (-1 means it is an integer)

    public Number(int i) {
        super(Tags.NUMBER);
        this.integer = i;
        this.fraction = -1;
    }

    public Number(int i, int frac) {
        super(Tags.NUMBER);
        this.integer = i;
        this.fraction = frac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number = (Number) o;
        return integer == number.integer &&
                fraction == number.fraction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(integer, fraction);
    }

    @Override
    public String toString() {
        return "Number{" +
                "integer=" + integer +
                ", fraction=" + fraction +
                '}';
    }
}
