package artifact;
import data.Data;
import data.Data.Stats;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.Utility;

public class Rolls extends Artifact {

    private final StringBuilder artifactStats;
    private final boolean[] changedSubStats;
    private int gainedSubStatUpgrades;
    public Rolls() {
        super();
        this.artifactStats = new StringBuilder();
        this.changedSubStats = new boolean[subStats.size()];
        this.gainedSubStatUpgrades = 0;
    }

    private enum Change {
        UPDATE,
        MERGE
    }


    ////// GENERAL HELPER METHODS
    

    // Stat value formatter
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

    // Change.UPDATE Helper method
    private void updateStatValue(String statName, double statValue) {
        String formattedStatValue = formatStatValue(statName, statValue);
        int statValueIndex = artifactStats.indexOf(String.format("%-20s", statName));
        artifactStats.insert(statValueIndex  + 26, String.format(" >>> %-5s", formattedStatValue));
    }

    // Change.MERGE Helper method
    private void mergeStatValue(String statName, double statValue) {
        String formattedStatValue = formatStatValue(statName, statValue);
        int statValueIndex = artifactStats.indexOf(String.format("%-20s", statName));
        artifactStats.replace(statValueIndex + 21, statValueIndex + 36, String.format("%5s", formattedStatValue));
    }

    // changeSubStatsEntry Helper method
    private void upgradeRandomSubStat(int gainSubStatUpgrades) {
        List<Stats> artifactSubStats = new ArrayList<>(subStats.keySet());
        for (int i = 0; i < gainSubStatUpgrades; i++) {
            // Selects a substat randomly and upgrades it every 4 artifact levels.
            int randomSubStatIndex = random.nextInt(artifactSubStats.size());
            Stats randomSubStatName = artifactSubStats.get(randomSubStatIndex);
            double[] randomSubStatValues = Data.SUB_STATS.get(randomSubStatName);
            double randomSubStatValue = randomSubStatValues[random.nextInt(randomSubStatValues.length)];
            subStats.put(randomSubStatName, subStats.get(randomSubStatName) + randomSubStatValue);
            changedSubStats[randomSubStatIndex] = true;
        }
    }

    // changeSubStatsEntry Helper method
    private boolean checkSubStatChanges() {
        for (boolean hasTrue : changedSubStats) {
            if (hasTrue) {
                return true;
            }
        }
        return false;
    }

    // changeSubStats Helper method
    private void changeSubStatsEntry(Change changeWhat) {
        switch (changeWhat) {
            case UPDATE -> {
                int gainSubStatUpgrades = artifactLevel / 4 - gainedSubStatUpgrades;
                if (gainSubStatUpgrades == 0) {
                    return;
                }
                upgradeRandomSubStat(gainSubStatUpgrades);
                gainedSubStatUpgrades += gainSubStatUpgrades;
            } case MERGE -> {
                // Terminates method if no changes.
                if (!checkSubStatChanges()) {
                    return;
                }
            }
        }
    }


    ////// CHANGE METHODS


    private void changeMainStat(Change changeWhat) {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            // Inserts a >>> newStatValue after each upgrade.
            Stats mainStatEnum = mainStatEntry.getKey();
            String mainStatName = mainStatEnum.getStat();
            double mainStatValue = Data.MAIN_STATS.get(mainStatEnum)[artifactLevel];
            // Refactored variables
            switch (changeWhat) {
                case UPDATE -> {
                    mainStat.put(mainStatEnum, mainStatValue);
                    updateStatValue(mainStatName, mainStatValue);
                } case MERGE -> {
                    mergeStatValue(mainStatName, mainStatValue);
                }
            }
        }
    }

    private void changeSubStats(Change changeWhat) {
        changeSubStatsEntry(changeWhat);
        int i = 0;
        for (Map.Entry<Stats,Double> subStatsEntry : subStats.entrySet()) {
            if (changedSubStats[i]) {
                String subStatName = subStatsEntry.getKey().getStat();
                double subStatValue = subStatsEntry.getValue();
                switch (changeWhat) {
                    case UPDATE -> {
                        updateStatValue(subStatName, subStatValue);
                    } case MERGE -> {
                        mergeStatValue(subStatName, subStatValue);
                        changedSubStats[i] = false;
                    }
                }
            }
            i++;
        }
    }


    ////// OUTPUT METHOD


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
            changeMainStat(Change.UPDATE);
            changeSubStats(Change.UPDATE);
            System.out.print(artifactStats);
            System.out.print("\n");
            changeMainStat(Change.MERGE);
            changeSubStats(Change.MERGE);
        }
        Utility.inputBuffer();
        System.out.print(artifactStats);
        System.out.print("\n");
    }

}