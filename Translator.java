import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Translator {
    private ArrayList<Token> tokens;
    private String fileName;
    private FileWriter javaFile;
    private boolean skip;

    public Translator(ArrayList<Token> tokens, String fileName) {
        this.tokens = tokens;
        this.fileName = fileName;
        skip = false;
        try {
            javaFile = new FileWriter(fileName + ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void boilerplate() {
        writeToJavaFile("public class " + fileName + " {\n");
    }

    public void translate() {
        HashMap<TokenType, String> replace = TokenType.getReplacedKeywords();
        for (Token token : tokens) {
            if (token.getType() == TokenType.EOL) {
                skip = false;
                writeToJavaFile("\n");
                continue;
            } if (skip) {
                continue;  
            } if (token.getType() == TokenType.PREPROCESSOR) {
                skip = true;
                continue;
            }
            if (replace.containsKey(token.getType())) {
                writeToJavaFile(replace.get(token.getType()));
            } else {
                Object value = token.getValue();
                if (value != null) {
                    if (value instanceof Double || value instanceof Long) {
                        writeToJavaFile(String.valueOf(value));
                    } else if (value instanceof String) {
                        writeToJavaFile((String) value);
                    }
                }
            }
        }
        writeToJavaFile("}");
        replaceMainMethod();
    }

    public void writeToJavaFile(String code) {
        if (code != null) {
            try {
                javaFile.write(code);
                javaFile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeFile() {
        try {
            if (javaFile != null) {
                javaFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceMainMethod() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName + ".java"));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains("void main(")) {
                    lines.set(i, "public static void main(String[] args) {");
                    break;
                }
            }
            Files.write(Paths.get(fileName + ".java"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
