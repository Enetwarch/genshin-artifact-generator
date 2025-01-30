package com.github.enetwarch.genshinartifactgenerator.artifact;
import com.github.enetwarch.genshinartifactgenerator.util.Output;
import com.github.enetwarch.genshinartifactgenerator.util.Input;
import com.github.enetwarch.genshinartifactgenerator.data.Stats;
import com.github.enetwarch.genshinartifactgenerator.data.Type;
import com.github.enetwarch.genshinartifactgenerator.data.Data;
import java.util.function.BiConsumer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class Artifact {

    private int artifactLevel;
    private final Type artifactType;
    private final Map<Stats, Double> mainStat;
    private final Map<Stats, Double> subStats;
    private final ArrayList<Stats> subStatsEnums;
    private int gainedSubStatUpgrades;
    private final StringBuilder artifactStats;

    public Artifact() {
        this.artifactLevel = 0;
        this.artifactType = Generator.generateArtifactType();
        this.mainStat = Generator.generateMainStat(this.artifactType);
        this.subStats = Generator.generateSubStats(this.mainStat.keySet().iterator().next());
        this.subStatsEnums = new ArrayList<>(subStats.keySet());
        this.gainedSubStatUpgrades = 0;
        this.artifactStats = new StringBuilder();
    }

    private static final int TEXT_WIDTH = 20;
    private static final int NUMBER_WIDTH = 5;
    private static final String OUTLINE_SEPARATOR = "=".repeat(TEXT_WIDTH + NUMBER_WIDTH);
    private static final String INLINE_SEPARATOR = "-".repeat(TEXT_WIDTH + NUMBER_WIDTH);
    private static final String MODIFIER_ARROW = String.format(" %s ", ">".repeat(NUMBER_WIDTH - 2));

    public void logToConsole() {
        appendArtifactStats();
        printArtifactStats();
        if (!proceedOrNot()) {
            return;
        }
        while (artifactLevel < 20) {
            int upgradeArtifactLevel = Input.getUserInputInt("Upgrade level", Integer.MIN_VALUE, Integer.MAX_VALUE);
            Output.printSpace();
            if (upgradeArtifactLevel <= 0) {
                return;
            }
            artifactLevel += upgradeArtifactLevel;
            if (upgradeArtifactLevel > 20 || artifactLevel > 20) {
                artifactLevel = 20;
            }
            upgradeArtifactLevel();
            upgradeMainStat();
            upgradeSubStats();
            printArtifactStats();
            mergeValues();
        }
        Input.inputBuffer();
        printArtifactStats();
    }

    private void printArtifactStats() {
        System.out.print(artifactStats);
        Output.printSpace();
    }

    private boolean proceedOrNot() {
        boolean proceedOrNot = Input.getUserInputBoolean("Upgrade artifact");
        Output.printSpace();
        return proceedOrNot;
    }

    public void appendArtifactStats() {
        appendSeparator(OUTLINE_SEPARATOR);
        appendEntry(artifactType, artifactLevel);
        appendSeparator(INLINE_SEPARATOR);
        iterateThroughStats(mainStat, this::appendEntry);
        appendSeparator(INLINE_SEPARATOR);
        iterateThroughStats(subStats, this::appendEntry);
        appendSeparator(OUTLINE_SEPARATOR);
    }

    private void appendSeparator(String separator) {
        artifactStats.append(String.format("%s%n", separator));
    }

    private void appendEntry(String text, String number) {
        artifactStats.append(String.format("%-" + TEXT_WIDTH + "s%" + NUMBER_WIDTH + "s%n", text, number));
    }

    private void appendEntry(Type artifactType, int artifactLevel) {
        final String formattedArtifactType = formatArtifactType(artifactType);
        final String formattedArtifactLevel = formatArtifactLevel(artifactLevel);
        appendEntry(formattedArtifactType, formattedArtifactLevel);
    }

    private void appendEntry(Stats statEnum, double statValue) {
        final String formattedStatName = formatStatName(statEnum);
        final String formattedStatValue = formatStatValue(statEnum, statValue);
        appendEntry(formattedStatName, formattedStatValue);
    }

    private static void iterateThroughStats(Map<Stats, Double> statsMap, BiConsumer<Stats, Double> method) {
        for (Map.Entry<Stats, Double> mainStatEntry : statsMap.entrySet()) {
            final Stats statEnum = mainStatEntry.getKey();
            final double statValue = mainStatEntry.getValue();
            method.accept(statEnum, statValue);
        }
    }

    private String formatArtifactType(Type artifactType) {
        return artifactType.getType();
    }

    private String formatArtifactLevel(int artifactLevel) {
        return String.format("+%d", artifactLevel);
    }

    private String formatStatName(Stats statEnum) {
        return statEnum.getStat();
    }

    private static String formatStatValue(Stats statEnum, double statValue) {
        DecimalFormat df;
        if (statEnum.getStat().contains("%")) {
            df = new DecimalFormat("#.0");
            return df.format(statValue) + "%";
        } else {
            df = new DecimalFormat("#,###");
            return df.format(statValue);
        }
    }

    private void updateStatValue(Stats statEnum, double statValue) {
        final String formattedStatName = formatStatName(statEnum).trim();
        final String formattedStatValue = formatStatValue(statEnum, statValue).trim();
        int statValueInsertIndex = artifactStats.indexOf(formattedStatName) + TEXT_WIDTH + NUMBER_WIDTH;
        int statValueReplaceIndex = statValueInsertIndex + NUMBER_WIDTH;
        final boolean alreadyUpdated = artifactStats.substring(statValueInsertIndex, statValueReplaceIndex).equals(MODIFIER_ARROW);
        if (alreadyUpdated) {
            statValueInsertIndex += NUMBER_WIDTH;
            statValueReplaceIndex += NUMBER_WIDTH;
            replaceStatValue(statValueInsertIndex, statValueReplaceIndex, formattedStatValue);
        } else {
            insertStatValue(statValueInsertIndex, formattedStatValue);
        }
    }

    private void replaceStatValue(int statValueInsertIndex, int statValueReplaceIndex, String formattedStatValue) {
        artifactStats.replace(statValueInsertIndex, statValueReplaceIndex, String.format("%-" + NUMBER_WIDTH + "s", formattedStatValue));
    }

    private void insertStatValue(int statValueInsertIndex, String formattedStatValue) {
        artifactStats.insert(statValueInsertIndex, String.format("%s%-" + NUMBER_WIDTH + "s", MODIFIER_ARROW, formattedStatValue));
    }

    private void mergeValues() {
        while (true) {
            final int currentModifierArrowIndex = artifactStats.indexOf(MODIFIER_ARROW);
            if (currentModifierArrowIndex == -1) {
                return;
            }
            replaceOldValues(currentModifierArrowIndex);
        }
    }

    private void replaceOldValues(int currentModifierArrowIndex) {
        int statValueStartingIndex = currentModifierArrowIndex + NUMBER_WIDTH;
        final int statValueFinalIndex = statValueStartingIndex + NUMBER_WIDTH;
        final String formattedStatValue = artifactStats.substring(statValueStartingIndex, statValueFinalIndex).trim();
        statValueStartingIndex = currentModifierArrowIndex - NUMBER_WIDTH;
        artifactStats.replace(statValueStartingIndex, statValueFinalIndex, String.format("%" + NUMBER_WIDTH + "s", formattedStatValue));
    }

    private void upgradeArtifactLevel() {
        final String formattedArtifactLevel = String.format("%" + NUMBER_WIDTH + "s", formatArtifactLevel(artifactLevel));
        final int artifactLevelStartingIndex = artifactStats.indexOf(formatArtifactType(artifactType)) + TEXT_WIDTH;
        final int artifactLevelFinalIndex = artifactLevelStartingIndex + NUMBER_WIDTH;
        artifactStats.replace(artifactLevelStartingIndex, artifactLevelFinalIndex, formattedArtifactLevel);
    }

    private void upgradeMainStat() {
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            final Stats mainStatEnum = mainStatEntry.getKey();
            final double mainStatValue = Data.MAIN_STATS.get(mainStatEnum)[artifactLevel];
            mainStat.put(mainStatEnum, mainStatValue);
            updateStatValue(mainStatEnum, mainStatValue);
        }
    }

    private void upgradeSubStats() {
        final int gainSubStatUpgrades = artifactLevel / 4 - gainedSubStatUpgrades;
        if (gainSubStatUpgrades == 0) {
            return;
        }
        upgradeRandomSubStat(gainSubStatUpgrades);
        gainedSubStatUpgrades += gainSubStatUpgrades;
    }

    private void upgradeRandomSubStat(int gainSubStatUpgrades) {
        for (int index = 0; index < gainSubStatUpgrades; index++) {
            final Stats randomSubStatEnum = getRandomSubStatEnum();
            final double newRandomSubStatValue = getNewRandomSubStatValue(randomSubStatEnum);
            subStats.put(randomSubStatEnum, newRandomSubStatValue);
            updateStatValue(randomSubStatEnum, newRandomSubStatValue);
        }
    }

    private Stats getRandomSubStatEnum() {
        final int randomSubStatIndex = Output.random.nextInt(subStatsEnums.size());
        return subStatsEnums.get(randomSubStatIndex);
    }

    private double getNewRandomSubStatValue(Stats randomSubStatEnum) {
        final double randomSubStatValue = getRandomSubStatValue(randomSubStatEnum);
        return subStats.get(randomSubStatEnum) + randomSubStatValue;
    }

    private double getRandomSubStatValue(Stats randomSubStatEnum) {
        final double[] randomSubStatValues = Data.SUB_STATS.get(randomSubStatEnum);
        return randomSubStatValues[Output.random.nextInt(randomSubStatValues.length)];
    }

}