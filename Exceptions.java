public class Exceptions {
    public static void invalidCommand() {
        System.out.println("calang: error: invalid command");
    }
    
    public static void unknownArgument(String flag) {
        System.out.println("calang: error: unknown argument: '" + flag + "'");
    }

    public static void noInputFiles() {
        System.out.println("calang: error: no input files");
    }

    public static void missingFlagParameter(String flag) {
        System.out.println("calang: error: argument to '" + flag + "' is missing (expected 1 value)");
    }

    public static void noSuchFileOrDirectory(String path) {
        System.out.println("calang: error: no such file or directory: '" + path + "'");
    }

    public static void straySymbol(String fileName, String repr, int line, int col) {
        System.out.println(fileName + ":" + (line + 1) + ":" + (col + 1) + ": error: stray '" + repr  + "' in program");
    }

    public static void invalidDefinePlacement(String fileName, String repr, int line, int col) {
        System.out.println(fileName + ":" + (line + 1) + ":" + (col + 1) + ": error: invalid '" + repr + "' placement in program");
    }
}