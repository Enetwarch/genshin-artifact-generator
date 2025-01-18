package artifact;
import java.text.DecimalFormat;
import java.util.Map;
import data.Data.Stats;
import data.Data.Type;

public class Rolls extends Artifact {


    ////// OUTPUT METHODS


    private static String formatStatValue(String statName, double statValue) {
        // Number formatting conventions of genshin artifacts.
        DecimalFormat df;
        if (statName.contains("%")) {
            df = new DecimalFormat("#.0");
            return df.format(statValue) + "%";
        } else {
            df = new DecimalFormat("#,###");
            return df.format(statValue);
        }
    }

    public void printArtifactStats() {
        StringBuilder artifactStats = new StringBuilder();
        artifactStats.append(String.format("%s\n", artifactType.getType()));
        artifactStats.append("MAIN STAT\n");
        for (Map.Entry<Stats, Double> mainStatEntry: mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            String formattedStatValue = formatStatValue(mainStatName, mainStatValue);
            artifactStats.append(String.format("%-20s %5s\n", mainStatName, formattedStatValue));
        }
        artifactStats.append("SUBSTATS\n");
        for (Map.Entry<Stats, Double> subStatsEntry: subStats.entrySet()) {
            String subStatName = subStatsEntry.getKey().getStat();
            double subStatValue = subStatsEntry.getValue();
            String formattedStatValue = formatStatValue(subStatName, subStatValue);
            artifactStats.append(String.format("%-20s %5s\n", subStatName, formattedStatValue));
        }
        artifactStats.append("\n");
        System.out.print(artifactStats);
    }

}