import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Translator {
    private ArrayList<Token> tokens;
    private FileWriter javaFile;
    private String boilerplateClass;
    private String boilerplateMainMethod;
    private String endCurly;

    public Translator(ArrayList<Token> tokens, String fileName) {
        this.tokens = tokens;
        boilerplateClass = "public class " + fileName + "{";
        boilerplateMainMethod = "public static void main(String[] args) {";
        endCurly = "}";
        try {
            javaFile = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void translate() {
        for (Token token : tokens) {

        }
    }
}
