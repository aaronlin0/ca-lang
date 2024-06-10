public class Token {
    private TokenType type;
    private Object value;
    private int startLineNum;
    private int startColumn;

    public Token(TokenType type, Object value, int startLineNum, int startColumn) {
        this.type = type;
        this.value = value;
        this.startLineNum = startLineNum;
        this.startColumn = startColumn;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getStartLineNum() {
        return startLineNum;
    }

    public void setStartLineNum(int startLineNum) {
        this.startLineNum = startLineNum;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                ", startLineNum=" + startLineNum +
                ", startColumn=" + startColumn +
                '}';
    }
}
