import java.util.ArrayList;
import java.util.HashMap;

public class Tokenizer {
	private ArrayList<String> caCode;
	private int linePosition;
	private int charPosition;
	private String currentLine;
	private String currentChar;
	private boolean stringMode;
	private boolean stringMode2;
	private String fileName;
	private boolean warningsEnabled;
	private ArrayList<Token> tokens;

	public Tokenizer(ArrayList<String> caCode, String fileName, boolean warningsEnabled) {
		this.caCode = caCode;
		linePosition = 0; 
		charPosition = 0; 
		currentLine = caCode.get(linePosition);
		currentChar = String.valueOf(currentLine.charAt(charPosition));
		stringMode = false;
		stringMode2 = false;
		this.fileName = fileName;
		this.warningsEnabled = warningsEnabled;
		tokens = new ArrayList<>();
	}
	
	public void advance() {
		if (charPosition == 0 && linePosition != 0) {
			tokens.add(new Token(TokenType.EOL, TokenType.EOL.getRepresentation(), linePosition, charPosition));
		}
		if (linePosition >= caCode.size()) {
			currentChar = null;
			return;
		}
		if (charPosition >= currentLine.length() - 1) {
			charPosition = 0;
			linePosition++;
			if (linePosition < caCode.size()) {
				currentLine = caCode.get(linePosition);
			}
			if (currentLine.isEmpty()) {
				advance();
			}
		} else {
			charPosition++;
		}
		if (linePosition < caCode.size()) {
			currentChar = String.valueOf(currentLine.charAt(charPosition));
		} else {
			currentChar = null;
		}
	}

	public String peek() {
		int peekPosition = charPosition + 1;
		if (peekPosition >= currentLine.length() || peekPosition < 0) {
			return "";
		}
		return String.valueOf(currentLine.charAt(peekPosition));
	}

	public void skipWhitespace() {
		while (currentChar != null && currentChar.equals(" ")) {
			advance();
		}
	}

	public void skipSingleLineComment() {
		int commentLinePosition = linePosition;
		while (commentLinePosition == linePosition) {
			advance();
		}
	}

	public void skipBlockComment() {
		while (!currentChar.equals("*") && !peek().equals("/")) {
			advance();
		}
		advance();
		advance();
	}

	public boolean doubleSymbol(String symbol) {
		return currentChar.equals(symbol.substring(0, 1)) && peek().equals(symbol.substring(1));	
	}

	public Token number() {
		String result = "";
		TokenType type = null;
		Object value = null;
		Token token = new Token(null, null, linePosition, charPosition);

		while (currentChar != null && currentChar.matches("-?\\d+(\\.\\d+)?")) {  // check if str is numerical
			result += currentChar;
			advance();
			if (currentChar.equals(".")) {
				result += currentChar;
				advance();
				while (currentChar != null && currentChar.matches("-?\\d+(\\.\\d+)?")) {
					result += currentChar;
					advance();
				}
				type = TokenType.FLOAT_LIT;
				value = Double.parseDouble(result);  // type casts to the largest data type
			} else {                                 // convert to a lower data type if necessary
				type = TokenType.INT_LIT;
				value = Long.parseLong(result);
			}
		}
		token.setType(type);
		token.setValue(value);
		return token;
	}

	public Token string() {
		String result = "";
		Token token = new Token(null, null, linePosition, charPosition);
		
		advance();
		while (currentChar != null && !currentChar.equals("\"")) {
			result += currentChar;
			advance();
		}
		stringMode = false;
		stringMode2 = true;
		token.setType(TokenType.STR_LIT);
		token.setValue(result);
		return token;
	}

	public Token identifier() {
		String result = "";
		TokenType type = null;
		Object value = null;
		HashMap<String, TokenType> reservedKeywords = TokenType.getReservedKeywords();
		Token token = new Token(null, null, linePosition, charPosition);

		while (currentChar != null && currentChar.matches("[a-zA-Z0-9]+")) {  // check if str is alphanumeric
			result += currentChar;
			advance();
		}
		type = reservedKeywords.get(result);
		if (type == null) {
			type = TokenType.IDENTIFIER;
			value = result;
		} else {
			value = result;
		}
		token.setType(type);
		token.setValue(value);
		return token;
	}

	public Token getNextToken() {  // I don't want to refactor this into multiple smaller methods. so much work :(
		while (currentChar != null) {
			if (stringMode) {
				return string();
			} if (stringMode2) {
				stringMode2 = false;
				Token token = new Token(TokenType.D_QUOTE, TokenType.D_QUOTE.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals(" ")) {
				skipWhitespace();
				continue;
			} if (doubleSymbol("//")) {
				skipSingleLineComment();
				continue;
			} if (doubleSymbol("/*")) {
				skipBlockComment();
				continue;
			} if (currentChar.equals("\"")) {
				stringMode = true;
				return new Token(TokenType.D_QUOTE, TokenType.D_QUOTE.getRepresentation(), linePosition, charPosition);
			} if (currentChar.chars().allMatch(Character::isLetter)) {  // check if alphabetic
				return identifier();
			} if (currentChar.matches("\\d+")) {  // check if number
				return number();
			} if (doubleSymbol("&&")) {
				Token token = new Token(TokenType.AND, TokenType.AND.getRepresentation(), linePosition, charPosition);
				return token;
			} if (doubleSymbol("||")) {
				Token token = new Token(TokenType.OR, TokenType.OR.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol(">=")) {
				Token token = new Token(TokenType.G_EQUALS, TokenType.G_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("<=")) {
				Token token = new Token(TokenType.L_EQUALS, TokenType.L_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("==")) {
				Token token = new Token(TokenType.C_EQUALS, TokenType.C_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("!=")) {
				Token token = new Token(TokenType.N_EQUALS, TokenType.N_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("++")) {
				Token token = new Token(TokenType.INCREMENT, TokenType.INCREMENT.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("--")) {
				Token token = new Token(TokenType.DECREMENT, TokenType.DECREMENT.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("+=")) {
				Token token = new Token(TokenType.PLUS_EQUALS, TokenType.PLUS_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("-=")) {
				Token token = new Token(TokenType.MINUS_EQUALS, TokenType.MINUS_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("*=")) {
				Token token = new Token(TokenType.MUL_EQUALS, TokenType.MUL_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("/=")) {
				Token token = new Token(TokenType.DIVIDE_EQUALS, TokenType.DIVIDE_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("%=")) {
				Token token = new Token(TokenType.MODULO_EQUALS, TokenType.MODULO_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (doubleSymbol("\\n")) {
				Token token = new Token(TokenType.N_LINE, TokenType.N_LINE.getRepresentation(), linePosition, charPosition);
				advance();
				advance();
				return token;
			} if (currentChar.equals("+")) {
				Token token = new Token(TokenType.PLUS, TokenType.PLUS.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("-")) {
				Token token = new Token(TokenType.MINUS, TokenType.MINUS.getRepresentation(), linePosition, charPosition); 
				advance();
				return token;
			} if (currentChar.equals("*")) {
				Token token = new Token(TokenType.MUL, TokenType.MUL.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("/")) {
				Token token = new Token(TokenType.F_SLASH, TokenType.F_SLASH.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("\\")) {
				Token token = new Token(TokenType.B_SLASH, TokenType.B_SLASH.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("%")) {
				Token token = new Token(TokenType.MODULO, TokenType.MODULO.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("=")) {
				Token token = new Token(TokenType.A_EQUALS, TokenType.A_EQUALS.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals(".")) {
				Token token = new Token(TokenType.DOT, TokenType.DOT.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals(",")) {
				Token token = new Token(TokenType.COMMA, TokenType.COMMA.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals(";")) {
				Token token = new Token(TokenType.SEMI, TokenType.SEMI.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("(")) {
				Token token = new Token(TokenType.L_PAREN, TokenType.L_PAREN.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals(")")) {
				Token token = new Token(TokenType.R_PAREN, TokenType.R_PAREN.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("[")) {
				Token token = new Token(TokenType.L_BRACKET, TokenType.L_BRACKET.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("]")) {
				Token token = new Token(TokenType.R_BRACKET, TokenType.R_BRACKET.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("{")) {
				Token token = new Token(TokenType.L_BRACE, TokenType.L_BRACE.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("}")) {
				Token token = new Token(TokenType.R_BRACE, TokenType.R_BRACE.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("'")) {
				Token token = new Token(TokenType.S_QUOTE, TokenType.S_QUOTE.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("!")) {
				Token token = new Token(TokenType.NOT, TokenType.NOT.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals(">")) {
				Token token = new Token(TokenType.G_THAN, TokenType.G_THAN.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("<")) {
				Token token = new Token(TokenType.L_THAN, TokenType.L_THAN.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			} if (currentChar.equals("#")) {
				Token token = new Token(TokenType.PREPROCESSOR, TokenType.PREPROCESSOR.getRepresentation(), linePosition, charPosition);
				advance();
				return token;
			}
			Token token = new Token(TokenType.ERROR, null, linePosition, charPosition);
			if (warningsEnabled) {
				Exceptions.straySymbol(fileName, currentChar, linePosition, charPosition);
			}
			advance();
			return token;
		}
		return new Token(TokenType.EOF, null, linePosition, charPosition);
	}

	public ArrayList<Token> convertCodeToTokens() {
		while (currentChar != null) {
			Token token = getNextToken();
			if (token.getType() == TokenType.PREPROCESSOR && token.getStartColumn() != 0) {
				if (warningsEnabled) {
					Exceptions.invalidDefinePlacement(fileName, "#define", linePosition, charPosition);
				}
			}
			tokens.add(token);
		}
		return tokens;
	}
}