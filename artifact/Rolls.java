package artifact;
import data.Data;
import data.Data.Stats;
import java.text.DecimalFormat;
import java.util.Map;
import util.Utility;

public class Rolls extends Artifact {

    private StringBuilder artifactStats;


    ////// CALCULATE METHODS
    

    private void calculateMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            Stats mainStatEnum = mainStatEntry.getKey();
            String mainStatName = mainStatEnum.getStat();
            double previousMainStatValue = mainStatEntry.getValue();
            double currentMainStatValue = Data.MAIN_STATS.get(mainStatEnum)[artifactLevel];
            String formattedPreviousMainStatValue = formatStatValue(mainStatName, previousMainStatValue);
            String formattedCurrentMainStatValue = formatStatValue(mainStatName, currentMainStatValue);
            int mainStatValueIndex = artifactStats.indexOf(mainStatName);
            artifactStats.replace(mainStatValueIndex + 21, mainStatValueIndex + 26, String.format("%5s >>> %-5s", formattedPreviousMainStatValue, formattedCurrentMainStatValue));
            mainStat.put(mainStatEnum, currentMainStatValue);
        }
    }

    private void mergeMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            int mainStatValueIndex = artifactStats.indexOf(mainStatName);
            artifactStats.delete(mainStatValueIndex + 21, mainStatValueIndex + 31);
        }
    }


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

    private void printArtifactStats() {
        artifactStats = new StringBuilder();
        artifactStats.append(String.format("%s\n", artifactType.getType()));
        artifactStats.append("MAIN STAT\n");
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            String formattedStatValue = formatStatValue(mainStatName, mainStatValue);
            artifactStats.append(String.format("%-20s %5s\n", mainStatName, formattedStatValue));
        }
        artifactStats.append("SUBSTATS\n");
        for (Map.Entry<Stats, Double> subStatsEntry : subStats.entrySet()) {
            String subStatName = subStatsEntry.getKey().getStat();
            double subStatValue = subStatsEntry.getValue();
            String formattedStatValue = formatStatValue(subStatName, subStatValue);
            artifactStats.append(String.format("%-20s %5s\n", subStatName, formattedStatValue));
        }
        artifactStats.append("\n");
        System.out.print(artifactStats);
    }


    ////// FINAL METHOD


    public void calculateToConsole() {
        printArtifactStats();
        boolean proceedOrNot = Utility.getUserInputBoolean("Upgrade artifact");
        System.out.print("\n");
        if (!proceedOrNot) {
            return;
        }
        while (artifactLevel < 20) {
            int artifactUpgrade = Utility.getUserInputInt("Upgrade level", Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.print("\n");
            if (artifactUpgrade <= 0) {
                return;
            }
            artifactLevel += artifactUpgrade;
            if (artifactUpgrade > 20 || artifactLevel > 20) {
                artifactLevel = 20;
            }
            calculateMainStat();
            System.out.print(artifactStats);
            mergeMainStat();
        }
        Utility.inputBuffer();
        System.out.print(artifactStats);
    }

}