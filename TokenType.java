import java.util.HashMap;
import java.util.LinkedHashMap;

public enum TokenType {
    // Symbols
    PLUS          ("+"),
    MINUS         ("-"),
    MUL           ("*"),
    F_SLASH       ("/"),
    B_SLASH       ("\\"),
    MODULO        ("%"),
    A_EQUALS      ("="),
    DOT           ("."),
    COMMA         (","),
    SEMI          (";"),
    L_PAREN       ("("),
    R_PAREN       (")"),
    L_BRACKET     ("["),
    R_BRACKET     ("]"),
    L_BRACE       ("{"),
    R_BRACE       ("}"),
    COMMENT       ("//"),
    S_BLOCK       ("/*"),
    E_BLOCK       ("*/"),
    S_QUOTE       ("'"),
    D_QUOTE       ("\""),
    NOT           ("!"),
    AND           ("&&"),
    OR            ("||"),
    G_THAN        (">"),
    L_THAN        ("<"),
    G_EQUALS      (">="),
    L_EQUALS      ("<="),
    C_EQUALS      ("=="),
    N_EQUALS      ("!="),
    INCREMENT     ("++"),
    DECREMENT     ("--"),
    PLUS_EQUALS   ("+="),
    MINUS_EQUALS  ("-="),
    MUL_EQUALS    ("*="),
    DIVIDE_EQUALS ("/="),
    MODULO_EQUALS ("%="),
    PREPROCESSOR  ("#"),

    // Reserved words
    DEFINE        ("define"),
    INT           ("int"),
    LONG          ("long"),
    SHORT         ("short"),
    FLOAT         ("float"),
    DOUBLE        ("double"),
    CHAR          ("char"),
    BOOL          ("bool"),
    TRUE          ("true"),
    FALSE         ("false"),
    VOID          ("void"),
    CONST         ("const"),
    NULL          ("null"),
    IF            ("if"),
    ELSE_IF       ("else if"),
    ELSE          ("else"),
    FOR           ("for"),
    WHILE         ("while"),
    CONTINUE      ("continue"),
    BREAK         ("break"),
    RETURN        ("return"),
    STRUCT        ("struct"),

    // Miscellaneous
    IDENTIFIER    ("IDENTIFIER"),
    INT_LIT       ("INT_LIT"),
    FLOAT_LIT     ("FLOAT_LIT"),
    STR_LIT       ("STR_LIT"),
    ERROR         ("ERROR"),
    EOL           ("EOL"),
    EOF           ("EOF");

    private final String representation;

    TokenType(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    public static HashMap<String, TokenType> getReservedKeywords() {
        int startIndex = TokenType.DEFINE.ordinal();
        int endIndex = TokenType.STRUCT.ordinal();
        HashMap<String, TokenType> reservedKeywords = new LinkedHashMap<>();
        for (int i = startIndex; i <= endIndex; i++) {
            reservedKeywords.put(TokenType.values()[i].representation, TokenType.values()[i]);
        }
        return reservedKeywords; 
    }
}