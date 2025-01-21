package artifact;
import data.Data;
import data.Data.Stats;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.Utility;

public class Rolls extends Artifact {

    private static final int TEXT_WIDTH = 20;
    private static final int NUMBER_WIDTH = 5;
    private static final String OUTLINE_SEPARATOR = "=".repeat(TEXT_WIDTH + NUMBER_WIDTH);
    private static final String INLINE_SEPARATOR = "-".repeat(TEXT_WIDTH + NUMBER_WIDTH);
    private static final String MODIFIER_ARROW = String.format(" %s ", ">".repeat(NUMBER_WIDTH - 2));

    private final StringBuilder artifactStats;
    private int gainedSubStatUpgrades;
    public Rolls() {
        super();
        this.artifactStats = new StringBuilder();
        this.gainedSubStatUpgrades = 0;
    }


    ////// CHANGE HELPER METHODS


    // Change.UPDATE Helper method
    private void updateStatValue(String statName, double statValue) {
        // Adds a >>> and new value to the artifact.
        String formattedStatValue = formatStatValue(statName, statValue);
        int statValueInsertIndex = artifactStats.indexOf(String.format("%-" + TEXT_WIDTH + "s", statName)) + TEXT_WIDTH + NUMBER_WIDTH;
        int statValueCheckForArrowStart = statValueInsertIndex;
        int statValueCheckForArrowFinal = statValueCheckForArrowStart + NUMBER_WIDTH;
        boolean alreadyUpdated = artifactStats.substring(statValueCheckForArrowStart, statValueCheckForArrowFinal).equals(MODIFIER_ARROW);
        if (alreadyUpdated) {
            artifactStats.replace(statValueCheckForArrowFinal, statValueCheckForArrowFinal + NUMBER_WIDTH, String.format("%-" + NUMBER_WIDTH + "s", formattedStatValue));
        } else {
            artifactStats.insert(statValueInsertIndex, String.format("%s%-" + NUMBER_WIDTH + "s", MODIFIER_ARROW, formattedStatValue));
        }
    }

    // Change.MERGE Helper method
    private void mergeValues() {
        while (true) {
            // Removes the old value and >>> from the artifact.
            int currentModifierArrowIndex = artifactStats.indexOf(MODIFIER_ARROW);
            if (currentModifierArrowIndex == -1) {
                return;
            }
            int statValueStartingIndex = currentModifierArrowIndex - NUMBER_WIDTH;
            int statValueFinalIndex = currentModifierArrowIndex + NUMBER_WIDTH * 2;
            String formattedStatValue = artifactStats.substring(statValueFinalIndex - NUMBER_WIDTH, statValueFinalIndex).trim();
            artifactStats.replace(statValueStartingIndex, statValueFinalIndex, String.format("%" + NUMBER_WIDTH + "s", formattedStatValue));
        }
    }


    ////// CHANGE METHODS


    private void changeArtifactLevel() {
        // Updates the + artifact level inline with the artifact type.
        String formattedArtifactLevel = String.format("%" + NUMBER_WIDTH + "s", String.format("+%d", artifactLevel));
        int artifactLevelStartingIndex = artifactStats.indexOf(String.format("%-" + TEXT_WIDTH + "s", artifactType.getType())) + TEXT_WIDTH;
        int artifactLevelFinalIndex = artifactLevelStartingIndex + NUMBER_WIDTH;
        artifactStats.replace(artifactLevelStartingIndex, artifactLevelFinalIndex, formattedArtifactLevel);
    }

    private void changeMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            Stats mainStatEnum = mainStatEntry.getKey();
            String mainStatName = mainStatEnum.getStat();
            double mainStatValue = Data.MAIN_STATS.get(mainStatEnum)[artifactLevel];
            mainStat.put(mainStatEnum, mainStatValue);
            updateStatValue(mainStatName, mainStatValue);
        }
    }

    private void upgradeRandomSubStat(int gainSubStatUpgrades) {
        List<Stats> artifactSubStats = new ArrayList<>(subStats.keySet());
        for (int i = 0; i < gainSubStatUpgrades; i++) {
            // Selects a substat randomly and upgrades it every 4 artifact levels.
            int randomSubStatIndex = random.nextInt(artifactSubStats.size());
            Stats randomSubStatEnum = artifactSubStats.get(randomSubStatIndex);
            double[] randomSubStatValues = Data.SUB_STATS.get(randomSubStatEnum);
            double randomSubStatValue = randomSubStatValues[random.nextInt(randomSubStatValues.length)];
            double newSubStatValue = subStats.get(randomSubStatEnum) + randomSubStatValue;
            subStats.put(randomSubStatEnum, newSubStatValue);
            String subStatName = randomSubStatEnum.getStat();
            updateStatValue(subStatName, newSubStatValue);
        }
    }

    private void changeSubStats() {
        // Decides initial requirements for changeSubStats.
        // Ever 4 levels gains one substat.
        int gainSubStatUpgrades = artifactLevel / 4 - gainedSubStatUpgrades;
        if (gainSubStatUpgrades == 0) {
            return;
        }
        upgradeRandomSubStat(gainSubStatUpgrades);
        gainedSubStatUpgrades += gainSubStatUpgrades;
    }


    ////// OUTPUT METHOD


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

    private void appendArtifactStats(String separator) {
        artifactStats.append(String.format("%s\n", separator));
    }

    private void appendArtifactStats(String text, String number) {
        artifactStats.append(String.format("%-" + TEXT_WIDTH + "s%" + NUMBER_WIDTH + "s\n", text, number));
    }

    private void buildArtifactStats() {
        // Printed artifact format.
        appendArtifactStats(OUTLINE_SEPARATOR);
        appendArtifactStats(artifactType.getType(), String.format("+%d", artifactLevel));
        appendArtifactStats(INLINE_SEPARATOR);
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            String formattedStatValue = formatStatValue(mainStatName, mainStatValue);
            appendArtifactStats(mainStatName, formattedStatValue);
        }
        appendArtifactStats(INLINE_SEPARATOR);
        for (Map.Entry<Stats, Double> subStatsEntry : subStats.entrySet()) {
            String subStatName = subStatsEntry.getKey().getStat();
            double subStatValue = subStatsEntry.getValue();
            String formattedStatValue = formatStatValue(subStatName, subStatValue);
            appendArtifactStats(subStatName, formattedStatValue);
        }
        appendArtifactStats(OUTLINE_SEPARATOR);
        System.out.print(artifactStats + "\n");
    }


    ////// FINAL METHOD


    public void calculateToConsole() {
        buildArtifactStats();
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
            changeArtifactLevel();
            changeMainStat();
            changeSubStats();
            System.out.print(artifactStats);
            System.out.print("\n");
            mergeValues();
        }
        Utility.inputBuffer();
        System.out.print(artifactStats);
        System.out.print("\n");
    }

}