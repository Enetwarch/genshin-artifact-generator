import artifact.Rolls;
import util.Utility;

public class Main {
    
    public static void main(String[] args) {
        while (true) {
            Rolls sampleArtifact = new Rolls();
            sampleArtifact.printArtifactStats();
            boolean proceedOrNot = Utility.getUserInputBoolean("Make another sample artifact");
            System.out.print("\n");
            if (!proceedOrNot) {
                break;
            }
        }
    }

}