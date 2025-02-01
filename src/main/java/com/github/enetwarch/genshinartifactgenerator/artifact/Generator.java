package com.github.enetwarch.genshinartifactgenerator.artifact;
import com.github.enetwarch.genshinartifactgenerator.util.Output;
import com.github.enetwarch.genshinartifactgenerator.data.Stats;
import com.github.enetwarch.genshinartifactgenerator.data.Type;
import com.github.enetwarch.genshinartifactgenerator.data.Data;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class Generator {

    private Type artifactType;
    private Map<Stats, Double> mainStat;
    private Map<Stats, Double> subStats;

    public Generator() {
        this.artifactType = generateArtifactType();
        this.mainStat = generateMainStat(this.artifactType);
        this.subStats = generateSubStats(this.mainStat.keySet().iterator().next());
    }

    public Type getArtifactType() {
        return artifactType;
    }

    public Map<Stats, Double> getMainStat() {
        return mainStat;
    }

    public Map<Stats, Double> getSubStats() {
        return subStats;
    }

    private static Type generateArtifactType() {
        Type[] artifactTypes = Type.values();
        int randomType = Output.random.nextInt(artifactTypes.length);
        return artifactTypes[randomType];
    }

    private static EnumMap<Stats, Double> generateMainStat(Type artifactType) {
        Stats mainStatEnum;
        switch (artifactType) {
            case FLOWER -> mainStatEnum = generateFlowerMainStat();
            case PLUME -> mainStatEnum = generatePlumeMainStat();
            case SANDS -> mainStatEnum = generateSandsMainStat();
            case GOBLET -> mainStatEnum = generateGobletMainStat();
            case CIRCLET -> mainStatEnum = generateCircletMainStat();
            default -> throw new IllegalArgumentException(String.format("Unknown artifact type: %s%n", artifactType));
        }
        return mapMainStat(mainStatEnum);
    }

    private static Stats generateFlowerMainStat() {
        return Stats.HP;
    }

    private static Stats generatePlumeMainStat() {
        return Stats.ATK;
    }

    private static Stats generateSandsMainStat() {
        float rng = Output.random.nextFloat();
        return rng <= .2668 ? Stats.HP_PERCENT :
        rng <= .5334 ? Stats.ATK_PERCENT :
        rng <= .8 ? Stats.DEF_PERCENT :
        rng <= .9 ? Stats.ENERGY_RECHARGE :
        Stats.ELEMENTAL_MASTERY;
    }

    private static Stats generateGobletMainStat() {
        float rng = Output.random.nextFloat();
        return rng <= .1925 ? Stats.HP_PERCENT :
        rng <= .385 ? Stats.ATK_PERCENT :
        rng <= .575 ? Stats.DEF_PERCENT :
        rng <= .625 ? Stats.PYRO_DMG_BONUS :
        rng <= .675 ? Stats.ELECTRO_DMG_BONUS :
        rng <= .725 ? Stats.CRYO_DMG_BONUS :
        rng <= .775 ? Stats.HYDRO_DMG_BONUS :
        rng <= .825 ? Stats.DENDRO_DMG_BONUS :
        rng <= .875 ? Stats.ANEMO_DMG_BONUS :
        rng <= .925 ? Stats.GEO_DMG_BONUS :
        rng <= .975 ? Stats.PHYSICAL_DMG_BONUS :
        Stats.ELEMENTAL_MASTERY;
    }

    private static Stats generateCircletMainStat() {
        float rng = Output.random.nextFloat();
        return rng <= .22 ? Stats.HP_PERCENT :
        rng <= .44 ? Stats.ATK_PERCENT :
        rng <= .66 ? Stats.DEF_PERCENT :
        rng <= .76 ? Stats.CRIT_RATE :
        rng <= .86 ? Stats.CRIT_DMG :
        rng <= .96 ? Stats.HEALING_BONUS :
        Stats.ELEMENTAL_MASTERY;
    }

    private static EnumMap<Stats, Double> mapMainStat(Stats mainStatEnum) {
        EnumMap<Stats, Double> mainStat = new EnumMap<>(Stats.class);
        Double mainStatValue = Data.MAIN_STATS.get(mainStatEnum)[0];
        mainStat.put(mainStatEnum, mainStatValue);
        return mainStat;
    }

    private static LinkedHashMap<Stats, Double> generateSubStats(Stats mainStatEnum) {
        LinkedHashMap<Stats, Double> subStats = new LinkedHashMap<>();
        ArrayList<Stats> subStatsPool = new ArrayList<>(Data.SUB_STATS.keySet());
        subStatsPool.remove(mainStatEnum);
        final int startingSubStats = 4;
        for (int index = 0; index < startingSubStats; index++) {
            Stats randomSubStatEnum = generateRandomSubStatEnum(subStatsPool);
            double randomSubStatValue = generateRandomSubStatValue(randomSubStatEnum);
            subStats.put(randomSubStatEnum, randomSubStatValue);
            subStatsPool.remove(randomSubStatEnum);
        }
        return subStats;
    };

    private static Stats generateRandomSubStatEnum(ArrayList<Stats> subStatsPool) {
        int randomSubStatIndex = Output.random.nextInt(subStatsPool.size());
        return subStatsPool.get(randomSubStatIndex);
    }

    private static double generateRandomSubStatValue(Stats randomSubStatEnum) {
        double[] randomSubStatValues = Data.SUB_STATS.get(randomSubStatEnum);
        return randomSubStatValues[Output.random.nextInt(randomSubStatValues.length)];
    }

}