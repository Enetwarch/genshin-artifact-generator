import artifact.Artifact;
import util.Utility;

public class Main {
    
    public static void main(String[] args) {
        while (true) {
            Artifact sampleArtifact = new Artifact();
            sampleArtifact.printSampleOutput();
            boolean proceedOrNot = Utility.getUserInputBoolean("Make another sample artifact");
            System.out.print("\n");
            if (!proceedOrNot) {
                break;
            }
        }
    }

}