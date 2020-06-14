package lexer;

import java.util.Objects;

public class BlockComment extends Token {

    private final String content;

    public BlockComment(String content) {
        super(Tags.BLOCK_COMMENT);
        this.content = content;
    }

    @Override
    public String toString() {
        return "BlockComment{" +
                "content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockComment that = (BlockComment) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
