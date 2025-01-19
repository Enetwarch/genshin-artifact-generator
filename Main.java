import artifact.Rolls;
import util.Utility;

public class Main {
    
    public static void main(String[] args) {
        while (true) {
            Rolls sampleArtifact = new Rolls();
            sampleArtifact.calculateToConsole();
            boolean proceedOrNot = Utility.getUserInputBoolean("Make another artifact");
            System.out.print("\n");
            if (!proceedOrNot) {
                break;
            }
        }
    }

}