package lexer;

import java.util.Objects;

public class LineComment extends Token {

    private final String content;

    public LineComment(String content) {
        super(Tags.LINE_COMMENT);
        this.content = content;
    }

    @Override
    public String toString() {
        return "LineComment{" +
                "content='" + content + '\'' +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineComment that = (LineComment) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
