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
    private boolean[] changedSubStats;
    public Rolls() {
        super();
        this.artifactStats = new StringBuilder();
        this.previousSubStats = new LinkedHashMap<>(subStats);
        this.gainedSubStats = 0;
        this.changedSubStats = new boolean[subStats.size()];
    }


    ////// CALCULATE METHODS
    

    // updateStats Helper method
    private void updateStatValue(String statName, double statValue) {
        String formattedStatValue = formatStatValue(statName, statValue);
        int statValueIndex = artifactStats.indexOf(String.format("%-20s", statName));
        artifactStats.insert(statValueIndex  + 26, String.format(" >>> %-5s", formattedStatValue));
    }

    private void updateMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            // Inserts a >>> newStatValue after each upgrade.
            Stats mainStatEnum = mainStatEntry.getKey();
            String mainStatName = mainStatEnum.getStat();
            double mainStatValue = Data.MAIN_STATS.get(mainStatEnum)[artifactLevel];
            // Refactored variables
            updateStatValue(mainStatName, mainStatValue);
            mainStat.put(mainStatEnum, mainStatValue);
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
            changedSubStats[randomSubStatIndex] = true;
        }
        int i = 0;
        for (Map.Entry<Stats,Double> subStatsEntry : subStats.entrySet()) {
            if (changedSubStats[i]) {
                String subStatName = subStatsEntry.getKey().getStat();
                double subStatValue = subStatsEntry.getValue();
                updateStatValue(subStatName, subStatValue);
            }
            i++;
        }
        gainedSubStats += gainSubStats;
    }

    // mergeStats Helper method
    private void mergeStatValue(String statName, double statValue) {
        String formattedStatValue = formatStatValue(statName, statValue);
        int statValueIndex = artifactStats.indexOf(String.format("%-20s", statName));
        artifactStats.replace(statValueIndex + 21, statValueIndex + 36, String.format("%5s", formattedStatValue));
    }

    private void mergeMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            mergeStatValue(mainStatName, mainStatValue);
        }
    }

    private void mergeSubStats() {
        int i = 0;
        for (Map.Entry<Stats,Double> subStatsEntry : subStats.entrySet()) {
            if (changedSubStats[i]) {
                String subStatName = subStatsEntry.getKey().getStat();
                double subStatValue = subStatsEntry.getValue();
                String formattedSubStatValue = formatStatValue(subStatName, subStatValue);
                int subStatIndex = artifactStats.indexOf(String.format("%-20s", subStatName));
                artifactStats.replace(subStatIndex + 21, subStatIndex + 36, String.format("%5s", formattedSubStatValue));
            }
            i++;
        }
        changedSubStats = new boolean[subStats.size()];
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
        System.out.print("\n");
    }

}