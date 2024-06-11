import java.util.ArrayList;
import java.util.HashMap;

public class Preprocessor {
    private ArrayList<Token> tokens;
    private ArrayList<Token> newTokens;
    private boolean containsError;

    public Preprocessor(ArrayList<Token> tokens) {
        this.tokens = tokens;
        newTokens = new ArrayList<>();
        containsError = false;
    }

    public void parseTokens() {
        define();
        elseIf();
    }

    public ArrayList<Token> getNewTokens() {
        return newTokens;
    }

    public boolean getContainsError() {
        return containsError;
    }

    public void define() {
        HashMap<Object, Object> matchAndReplace = new HashMap<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            Object currentValue = currentToken.getValue();
            if (currentToken.getType() == TokenType.ERROR) {
                containsError = true;
            }
            if (currentToken.getType() == TokenType.PREPROCESSOR && i + 3 < tokens.size() && tokens.get(i + 1).getType() == TokenType.DEFINE) {
                Object match = tokens.get(i + 2).getValue();
                Object replace = tokens.get(i + 3).getValue();
                matchAndReplace.put(match, replace);
            }
            if (matchAndReplace.containsKey(currentValue) && tokens.get(i - 1).getType() != TokenType.DEFINE) {
                currentToken.setValue(matchAndReplace.get(currentValue));
            }
        }
    }

    public void elseIf() {
        for (int i = 0; i < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (currentToken.getType() == TokenType.ELSE && i + 1 < tokens.size() && tokens.get(i + 1).getType() == TokenType.IF) {
                Token elseIfToken = new Token(TokenType.ELSE_IF, TokenType.ELSE_IF.getRepresentation(), currentToken.getStartLineNum(), currentToken.getStartColumn());
                newTokens.add(elseIfToken);
                i++;
            } else {
                newTokens.add(currentToken);
            }
            if (i + 1 < tokens.size() && currentToken.getType() != TokenType.EOL && currentToken.getType() != TokenType.D_QUOTE && currentToken.getType() != TokenType.S_QUOTE && tokens.get(i + 1).getType() != TokenType.L_PAREN && tokens.get(i + 1).getType() != TokenType.D_QUOTE && tokens.get(i + 1).getType() != TokenType.S_QUOTE) {
                newTokens.add(new Token(TokenType.SPACE, TokenType.SPACE.getRepresentation(), currentToken.getStartLineNum(), currentToken.getStartColumn()));
            }
        }
    }
}