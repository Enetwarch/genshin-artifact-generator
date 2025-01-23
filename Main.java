import artifact.Rolls;
import util.Utility;

public class Main {
    
    private static void terminateProgram() {
        StringBuilder terminateProgram = new StringBuilder();
        terminateProgram.append("Genshin Artifact Generator\n");
        terminateProgram.append("Code by: Enetwarch\n");
        System.out.print(terminateProgram);
        Utility.scanner.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        while (true) {
            Rolls sampleArtifact = new Rolls();
            sampleArtifact.calculateToConsole();
            boolean proceedOrNot = Utility.getUserInputBoolean("Make another artifact");
            System.out.print("\n");
            if (!proceedOrNot) {
                terminateProgram();
            }
        }
    }

}