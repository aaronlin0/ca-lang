import java.util.ArrayList;

public class Transpiler {
    public static void main(String[] args) {
        TranspilerFlags flags = new TranspilerFlags(args);
        ReadFile readFile = new ReadFile(flags.getInputFilePath());
        Tokenizer tokenizer = new Tokenizer(readFile.getCaCode(), flags.getInputFileName(), flags.getWarningsAll());
        ArrayList<Token> tokens = tokenizer.convertCodeToTokens();
        Preprocessor preprocessor = new Preprocessor(tokens);
        preprocessor.parseTokens();
        ArrayList<Token> preprocessedTokens = preprocessor.getNewTokens();
        boolean containsError = preprocessor.getContainsError();
        if (!containsError) {
            Translator translator = new Translator(preprocessedTokens, flags.getOutputFileName());
            translator.boilerplate();
            translator.translate();
        } else {
            Exceptions.errorInCaFile();
        }

        if (flags.getDebug()) {
            Debug.debugStartMessage();
            Debug.debugUserArguments(args);
            Debug.debugFlags(flags);
            Debug.debugCaCode(readFile.getCaCode());
            Debug.debugReservedKeywords(TokenType.getReservedKeywords());
            Debug.debugTokens(tokens);
            Debug.debugPreprocessedTokens(preprocessedTokens);
        }
    }
}