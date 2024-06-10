import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Debug {
    public static void debugStartMessage() {
        System.out.println("\n[DBG] Debug mode is turned on.\n[DBG]");
    }

    public static void debugUserArguments(String[] args) {
        System.out.println("[DBG] ---------------< Transpiler.java >---------------");
        System.out.println("[DBG] User Arguments");
        System.out.println("[DBG] ---------------< Transpiler.java >---------------");

        for (int i = 0; i < args.length; i++) {
            String formattedNum = String.format("%02d", i + 1);
            System.out.println("[DBG] " + formattedNum + ". " + args[i]);
        }

        System.out.println("[DBG] -------------------------------------------------\n[DBG]");
    }

    public static void debugFlags(TranspilerFlags flags) {
        System.out.println("[DBG] -------------< TranspilerFlags.java >------------");
        System.out.println("[DBG] Flags");
        System.out.println("[DBG] -------------< TranspilerFlags.java >------------");

        System.out.println("[DBG] <NAME>               <DEFAULT>     <VALUE>");
        System.out.println("[DBG] Input File Path:     null          " + flags.getInputFilePath());
        System.out.println("[DBG] Output File:         null          " + flags.getOutputFileName());
        System.out.println("[DBG] Warnings Enabled:    false         " + flags.getWarningsAll());
        System.out.println("[DBG] Debug Mode:          false         " + flags.getDebug());
        System.out.println("[DBG] Seen Help/Version:   false         " + flags.getSeenHelpOrVersion());
        System.out.println("[DBG] Valid Command:       true          " + flags.getValidCommand());
        System.out.println("[DBG] Input File Name:     n/a           " + flags.getInputFileName());
        System.out.println("[DBG] -------------------------------------------------\n[DBG]");
    }

    public static void debugCaCode(ArrayList<String> caCode) {
        System.out.println("[DBG] -----------------< ReadFile.java >---------------");
        System.out.println("[DBG] .ca Code");
        System.out.println("[DBG] -----------------< ReadFile.java >---------------");

        int maxDigits = String.valueOf(caCode.size()).length();
        for (int i = 0; i < caCode.size(); i++) {
            String lineNumber = String.format("%" + maxDigits + "d", i + 1);
            System.out.println("[DBG] " + lineNumber + "  " + caCode.get(i));
        }

        System.out.println("[DBG] -------------------------------------------------\n[DBG]");
    }
    
    public static void debugReservedKeywords(HashMap<String, TokenType> reservedKeywords) {
        System.out.println("[DBG] ----------------< TokenType.java >---------------");
        System.out.println("[DBG] Reserved Keywords");
        System.out.println("[DBG] ----------------< TokenType.java >---------------");
        
        for (Map.Entry<String, TokenType> entry : reservedKeywords.entrySet()) {
            String representation = entry.getKey();
            TokenType tokenType = entry.getValue();
            int spacing = 12 - representation.length();
            System.out.print("[DBG] Representation: " + representation);
            for (int i = 0; i < spacing; i++) {
                System.out.print(" ");
            }
            System.out.println("Token Type: " + tokenType);
        }

        System.out.println("[DBG] -------------------------------------------------\n[DBG]");
    }

    public static void debugTokens(ArrayList<Token> tokens) {
        System.out.println("[DBG] ----------------< Tokenizer.java >---------------");
        System.out.println("[DBG] Tokens");
        System.out.println("[DBG] ----------------< Tokenizer.java >---------------");

        System.out.println("[DBG] Amount of tokens: " + tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("[DBG] " + tokens.get(i));
        }

        System.out.println("[DBG] -------------------------------------------------\n[DBG]");
    }

    public static void debugPreprocessedTokens(ArrayList<Token> preprocessedTokens) {
        System.out.println("[DBG] --------------< Preprocessor.java >--------------");
        System.out.println("[DBG] Preprocessed Tokens");
        System.out.println("[DBG] --------------< Preprocessor.java >--------------");

        System.out.println("[DBG] Amount of tokens: " + preprocessedTokens.size());
        for (int i = 0; i < preprocessedTokens.size(); i++) {
            System.out.println("[DBG] " + preprocessedTokens.get(i));
        }

        System.out.println("[DBG] -------------------------------------------------\n[DBG]");
    }
}