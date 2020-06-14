import lexer.*;
import lexer.Number;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class LexerTest {

    private ByteArrayInputStream createInputStreamFromString(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    @Test
    public void parseIntegerTest() throws Exception {
        int number = 24;
        Lexer lexer = new Lexer(createInputStreamFromString(number + "\n"));
        Token res = lexer.scan();
        Assert.assertEquals(new Number(number), res);
    }

    @Test
    public void parseIdentifierTest() throws Exception {
        String id = "id1";
        Lexer lexer = new Lexer(createInputStreamFromString(id + "\n"));
        Token res = lexer.scan();
        Assert.assertEquals(new Identifier(Tags.ID, id), res);
    }

    @Test
    public void parseLineCommentTest() throws Exception {
        String lineComment = "// hello world";
        Lexer lexer = new Lexer(createInputStreamFromString(lineComment + "\n"));
        Token res = lexer.scan();
        Assert.assertEquals(new LineComment(lineComment.substring(2)), res);
    }

    @Test
    public void parseBlockCommentTest() throws Exception {
        String blockComment = "/* hello world *2 \n guobao */";
        Lexer lexer = new Lexer(createInputStreamFromString(blockComment + "\n"));
        Token res = lexer.scan();
        Assert.assertEquals(new BlockComment(blockComment.substring(2, blockComment.length() - 2)), res);
    }

    /**
     * Test the spaces separators
     * @throws Exception
     */
    @Test
    public void parseIdentifierAndBlockCommentTest() throws Exception {
        String id = " id10 ";
        String blockComment = "/* hello world \n guobao */";
        Lexer lexer = new Lexer(createInputStreamFromString(id + blockComment + "\n"));
        Token resId = lexer.scan();
        Assert.assertEquals(new Identifier(Tags.ID, id.trim()), resId);
        Token resComment = lexer.scan();
        Assert.assertEquals(new BlockComment(blockComment.substring(2, blockComment.length() - 2)), resComment);
    }

    @Test
    public void parseOperatorTest1() throws Exception {
        String content = "a<10";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Identifier(Tags.ID, "a"), lexer.scan());
        Assert.assertEquals(new Operator("<"), lexer.scan());
        Assert.assertEquals(new Number(10), lexer.scan());
    }

    @Test
    public void parseOperatorTest2() throws Exception {
        String content = "a<=10";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Identifier(Tags.ID, "a"), lexer.scan());
        Assert.assertEquals(new Operator("<="), lexer.scan());
        Assert.assertEquals(new Number(10), lexer.scan());
    }

    @Test
    public void parseOperatorTest3() throws Exception {
        String content = "a>10";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Identifier(Tags.ID, "a"), lexer.scan());
        Assert.assertEquals(new Operator(">"), lexer.scan());
        Assert.assertEquals(new Number(10), lexer.scan());
    }

    @Test
    public void parseOperatorTest4() throws Exception {
        String content = "a>=10";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Identifier(Tags.ID, "a"), lexer.scan());
        Assert.assertEquals(new Operator(">="), lexer.scan());
        Assert.assertEquals(new Number(10), lexer.scan());
    }

    @Test
    public void parseOperatorTest5() throws Exception {
        String content = "a!=10";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Identifier(Tags.ID, "a"), lexer.scan());
        Assert.assertEquals(new Operator("!="), lexer.scan());
        Assert.assertEquals(new Number(10), lexer.scan());
    }

    @Test
    public void parseOperatorTest6() throws Exception {
        String content = "a==10";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Identifier(Tags.ID, "a"), lexer.scan());
        Assert.assertEquals(new Operator("=="), lexer.scan());
        Assert.assertEquals(new Number(10), lexer.scan());
    }

    @Test
    public void parseFloatNumber1() throws Exception {
        String content = "10.123";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Number(10, 123), lexer.scan());
    }

    @Test
    public void parseFloatNumber2() throws Exception {
        String content = ".123";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Number(0, 123), lexer.scan());
    }

    @Test
    public void parseFloatNumber3() throws Exception {
        String content = "2.";
        Lexer lexer = new Lexer(createInputStreamFromString(content + "\n"));
        Assert.assertEquals(new Number(2, 0), lexer.scan());
    }
}
