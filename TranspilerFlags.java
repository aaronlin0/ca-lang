import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TranspilerFlags {
    private String[] userFlags;
    private HashMap<String, String> flags;
    private HashMap<String, Boolean> hasAdditionalParameters;
    
    private String outputFileName;
    private String inputFilePath;
    private boolean warningsAll;
    private boolean debug;

    private boolean skipNextFlag;
    private boolean seenHelpOrVersion;
    private boolean validCommand;
    
    public TranspilerFlags(String[] userFlags) {
        this.userFlags = userFlags;
        flags = new HashMap<>(Map.ofEntries(
            Map.entry("-debug", "debug"),
            Map.entry("--help", "help"),
            Map.entry("-o", "outputFileName"),
            Map.entry("--version", "version"),
            Map.entry("-Wall", "warningsAll")
        ));
        hasAdditionalParameters = new HashMap<>(Map.ofEntries(
            Map.entry("-debug", false),    
            Map.entry("--help", false),
            Map.entry("-o", true),
            Map.entry("--version", false),
            Map.entry("-Wall", false)
        ));
        validCommand = true;
        parseUserFlags();
        checkRequiredFlags();
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    
    public boolean getWarningsAll() {
        return warningsAll;
    }

    public boolean getDebug() {
        return debug;
    }

    public boolean getSeenHelpOrVersion() {
        return seenHelpOrVersion;
    }

    public boolean getValidCommand() {
        return validCommand;
    }

    public String getInputFileName() {
        int lastIndex = inputFilePath.lastIndexOf('/');
        if (lastIndex == -1) {
            lastIndex = inputFilePath.lastIndexOf('\\');
        }
        return (lastIndex != -1) ? inputFilePath.substring(lastIndex + 1) : inputFilePath;
    }
    
    private void parseUserFlags() {
        for (int i = 0; i < userFlags.length; i++) {
            String flag = userFlags[i];
            if (flag.endsWith(".ca") && !skipNextFlag) {
                callMethod("inputFilePathFlag", true, i);
            } else if (flags.containsKey(flag) && !skipNextFlag) {
                String methodName = flags.get(flag) + "Flag";
                boolean hasParameter = hasAdditionalParameters.get(flag);
                callMethod(methodName, hasParameter, i);
                continue;
            } 
            if (!skipNextFlag) {
                Exceptions.unknownArgument(flag);
            }
            skipNextFlag = false;
        }
    }

    private void callMethod(String methodName, boolean hasParameter, int index) {
        try {
            if (hasParameter) {
                skipNextFlag = true;
                Method method = TranspilerFlags.class.getDeclaredMethod(methodName, int.class);
                method.invoke(this, index);
            } else {
                Method method = TranspilerFlags.class.getDeclaredMethod(methodName);
                method.invoke(this);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException err) {
            Exceptions.invalidCommand();
        }
    }

    private void checkRequiredFlags() {
        if (!seenHelpOrVersion && inputFilePath == null) {
            Exceptions.noInputFiles();
            validCommand = false;
        } if (!seenHelpOrVersion && outputFileName == null) {
            Exceptions.missingFlagParameter("-o");
            validCommand = false;
        } if (!checkIfFileExists(inputFilePath)) {
            Exceptions.noSuchFileOrDirectory(inputFilePath);
            validCommand = false;
        }
    }

    private boolean checkIfFileExists(String path) {
        try {
            File file = new File(path);
            return file.exists();
        } catch (NullPointerException err) {
            return false;
        }
    }

    private void helpFlag() {
        if (!seenHelpOrVersion) {
            System.out.println("OVERVIEW C@ transpiler\n");
            System.out.println("USAGE: javac Transpiler.java; java Transpiler [options] file...\n");
            System.out.println("OPTIONS:");
            System.out.println("-debug                  Display debug information");
            System.out.println("--help                  Display available options");
            System.out.println("-o <file>               Write output to <file>");
            System.out.println("--version               Print version information");
            System.out.println("-Wall                   Show all warnings\n");
            seenHelpOrVersion = true;
        }
    }

    private void outputFileNameFlag(int index) {
        if (outputFileName == null) {
            outputFileName = userFlags[index + 1];
        }
    }

    private void inputFilePathFlag(int index) {
        if (inputFilePath == null) {
            inputFilePath = userFlags[index];
        }
    }

    private void warningsAllFlag() {
        warningsAll = true;
    }

    private void debugFlag() {
        debug = true;
    }

    private void versionFlag() {
        if (!seenHelpOrVersion) {
            System.out.println(System.getProperty("os.name") + " calang version 1.0.0");
            System.out.println("Target: " + System.getProperty("os.arch") + "-pc-" + System.getProperty("os.name").toLowerCase());
            System.out.println("InstalledDir: " + System.getProperty("user.dir") + "\n");
            seenHelpOrVersion = true;
        }
    }
}
