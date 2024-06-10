import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    private BufferedReader reader;
    private String filePath;
    private ArrayList<String> caCode;

    public ReadFile(String filePath) {
        try {
            this.filePath = filePath;
            this.caCode = new ArrayList<>();
            this.reader = new BufferedReader(new FileReader(filePath));
            readLinesFromFile();
        } catch (IOException err) {
            Exceptions.noSuchFileOrDirectory(filePath);
        }
    }

    public ArrayList<String> getCaCode() {
        return caCode;
    }

    private void readLinesFromFile() {
        try {
            String line = reader.readLine();
            while (line != null) {
                caCode.add(line + " ");
                line = reader.readLine();
            }
        } catch (IOException err) {
            Exceptions.noSuchFileOrDirectory(filePath);
        }
    }    
}