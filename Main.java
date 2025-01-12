import artifact.Artifact;
import java.util.Map;

public class Main {
    
    public static void main(String[] args) {
        StringBuilder dataPrinter = new StringBuilder();
        dataPrinter.append("MAIN STATS\n");
        for (Map.Entry<Artifact.Stats, double[]> mapEntry : Artifact.MAIN_STATS.entrySet()) {
            String mainStat = mapEntry.getKey().getStat();
            double[] values = mapEntry.getValue();
            dataPrinter.append(String.format("%s: ", mainStat));
            for (double value : values) {
                dataPrinter.append(String.format("%.2f ", value));
            }
            dataPrinter.append("\n");
        }
        dataPrinter.append("\n");
        for (Map.Entry<Artifact.Stats, double[]> mapEntry : Artifact.SUB_STATS.entrySet()) {
            String subStat = mapEntry.getKey().getStat();
            double[] values = mapEntry.getValue();
            dataPrinter.append(String.format("%s: ", subStat));
            for (double value : values) {
                dataPrinter.append(String.format("%.2f ", value));
            }
            dataPrinter.append("\n");
        }
        System.out.print(dataPrinter);
    }

}