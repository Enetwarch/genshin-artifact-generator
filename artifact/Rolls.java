package artifact;
import data.Data;
import data.Data.Stats;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.Utility;

public class Rolls extends Artifact {

    private final StringBuilder artifactStats;
    private final Map<Stats, Double> previousSubStats;
    private int gainedSubStats;
    private boolean[] toMergeSubStats;
    public Rolls() {
        super();
        this.artifactStats = new StringBuilder();
        this.previousSubStats = new LinkedHashMap<>(subStats);
        this.gainedSubStats = 0;
        this.toMergeSubStats = new boolean[4];
    }


    ////// CALCULATE METHODS
    

    private void updateMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            Stats mainStatEnum = mainStatEntry.getKey();
            String mainStatName = mainStatEnum.getStat();
            double previousMainStatValue = mainStatEntry.getValue();
            double currentMainStatValue = Data.MAIN_STATS.get(mainStatEnum)[artifactLevel];
            String formattedPreviousMainStatValue = formatStatValue(mainStatName, previousMainStatValue);
            String formattedCurrentMainStatValue = formatStatValue(mainStatName, currentMainStatValue);
            int mainStatValueIndex = artifactStats.indexOf(String.format("%-20s", mainStatName));
            artifactStats.replace(mainStatValueIndex + 21, mainStatValueIndex + 26, String.format("%5s >>> %-5s", formattedPreviousMainStatValue, formattedCurrentMainStatValue));
            mainStat.put(mainStatEnum, currentMainStatValue);
        }
    }

    private void updateSubStats() {
        int gainSubStats = artifactLevel / 4 - gainedSubStats;
        if (gainSubStats == 0) {
            return;
        }
        List<Stats> artifactSubStats = new ArrayList<>(subStats.keySet());
        for (int i = 0; i < gainSubStats; i++) {
            // Selects a substat randomly and upgrades it every 4 artifact levels.
            int randomSubStatIndex = random.nextInt(artifactSubStats.size());
            Stats randomSubStatName = artifactSubStats.get(randomSubStatIndex);
            double[] randomSubStatValues = Data.SUB_STATS.get(randomSubStatName);
            double randomSubStatValue = randomSubStatValues[random.nextInt(randomSubStatValues.length)];
            subStats.put(randomSubStatName, subStats.get(randomSubStatName) + randomSubStatValue);
        }
        List<Map.Entry<Stats,Double>> previousSubStatsList = new ArrayList<>(previousSubStats.entrySet());
        List<Map.Entry<Stats,Double>> currentSubStatsList = new ArrayList<>(subStats.entrySet());
        for (int i = 0; i < currentSubStatsList.size(); i++) {
            double previousSubStatValue = previousSubStatsList.get(i).getValue();
            double currentSubStatValue = currentSubStatsList.get(i).getValue();
            if (previousSubStatValue != currentSubStatValue) {
                String subStatName = currentSubStatsList.get(i).getKey().getStat();
                int subStatIndex = artifactStats.indexOf(String.format("%-20s", subStatName));
                String formattedPreviousSubStatValue = formatStatValue(subStatName, previousSubStatValue);
                String formattedCurrentSubStatValue = formatStatValue(subStatName, currentSubStatValue);
                artifactStats.replace(subStatIndex + 21, subStatIndex + 26, String.format("%5s >>> %-5s", formattedPreviousSubStatValue, formattedCurrentSubStatValue));
                toMergeSubStats[i] = true;
            }
        }
        gainedSubStats += gainSubStats;
    }

    private void mergeMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            String formattedMainStatValue = formatStatValue(mainStatName, mainStatValue);
            int mainStatValueIndex = artifactStats.indexOf(String.format("%-20s", mainStatName));
            artifactStats.replace(mainStatValueIndex + 21, mainStatValueIndex + 36, String.format("%5s", formattedMainStatValue));
        }
    }

    private void mergeSubStats() {
        List<Map.Entry<Stats,Double>> subStatsList = new ArrayList<>(subStats.entrySet());
        for (int i = 0; i < toMergeSubStats.length; i++) {
            if (toMergeSubStats[i]) {
                String subStatName = subStatsList.get(i).getKey().getStat();
                double subStatValue = subStatsList.get(i).getValue();
                String formattedSubStatValue = formatStatValue(subStatName, subStatValue);
                int subStatIndex = artifactStats.indexOf(String.format("%-20s", subStatName));
                artifactStats.replace(subStatIndex + 21, subStatIndex + 36, String.format("%5s", formattedSubStatValue));
            }
        }
        toMergeSubStats = new boolean[4];
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
        artifactStats.append(String.format("%-20s\n", artifactType.getType()));
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
        System.out.print(artifactStats + "\n");
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
            updateMainStat();
            updateSubStats();
            System.out.print(artifactStats);
            System.out.print("\n");
            mergeMainStat();
            mergeSubStats();
        }
        Utility.inputBuffer();
        System.out.print(artifactStats);
    }

}