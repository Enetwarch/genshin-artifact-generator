package artifact;
import data.Data;
import data.Data.Stats;
import data.Data.Type;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class Artifact {
    
    private static final Random random = new Random();

    private Type artifactType;
    private Map<Stats, Double> mainStat;
    private Map<Stats, Double> subStats;
    public Artifact() {
        this.artifactType = TYPE_SUPPLIER.get();
        this.mainStat = MAIN_STAT_SUPPLIER.get();
        this.subStats = SUBSTAT_SUPPLIER.get();
    }


    ////// ARTIFACT TYPE SUPPLIER
    

    private final Supplier<Type> TYPE_SUPPLIER = () -> {
        // Generates a random artifact type.
        Type[] typeArray = Type.values();
        int randomType = random.nextInt(typeArray.length);
        return typeArray[randomType];
    };


    ////// MAIN STAT SUPPLIERS


    private static final Supplier<Stats> FLOWER_MAIN_STAT_SUPPLIER = () -> {
        // Flower of Life
        return Stats.HP; // 100% -> HP
    };
    
    private static final Supplier<Stats> PLUME_MAIN_STAT_SUPPLIER = () -> {
        // Plume of Death
        return Stats.ATK; // 100% -> ATK
    };
    
    private static final Supplier<Stats> SANDS_MAIN_STAT_SUPPLIER = () -> {
        // Sands of Eon
        float rng = random.nextFloat();
        return rng <= .2668 ? Stats.HP_PERCENT : // 26.68% -> HP%
        rng <= .5334 ? Stats.ATK_PERCENT : // 26.66% -> ATK%
        rng <= .8 ? Stats.DEF_PERCENT : // 26.66% -> DEF%
        rng <= .9 ? Stats.ENERGY_RECHARGE : // 10.00% -> Energy Recharge%
        Stats.ELEMENTAL_MASTERY; // 10.00% -> Elemental Mastery
    };
    
    private static final Supplier<Stats> GOBLET_MAIN_STAT_SUPPLIER = () -> {
        // Goblet of Eonothem
        float rng = random.nextFloat();
        return rng <= .1925 ? Stats.HP_PERCENT : // 19.25% -> HP%
        rng <= .385 ? Stats.ATK_PERCENT : // 19.25% -> ATK%
        rng <= .575 ? Stats.DEF_PERCENT : // 19.00% -> DEF%
        rng <= .625 ? Stats.PYRO_DMG_BONUS : // 5.00% -> Pyro DMG Bonus%
        rng <= .675 ? Stats.ELECTRO_DMG_BONUS : // 5.00% -> Electro DMG Bonus%
        rng <= .725 ? Stats.CRYO_DMG_BONUS : // 5.00% -> Cryo DMG Bonus%
        rng <= .775 ? Stats.HYDRO_DMG_BONUS : // 5.00% -> Hydro DMG Bonus%
        rng <= .825 ? Stats.DENDRO_DMG_BONUS : // 5.00% -> Dendro DMG Bonus%
        rng <= .875 ? Stats.ANEMO_DMG_BONUS : // 5.00% -> Anemo DMG Bonus%
        rng <= .925 ? Stats.GEO_DMG_BONUS : // 5.00% -> Geo DMG Bonus%
        rng <= .975 ? Stats.PHYSICAL_DMG_BONUS : // 5.00% -> Physical DMG Bonus%
        Stats.ELEMENTAL_MASTERY; // 2.50% -> Elemental Mastery
    };
    
    private static final Supplier<Stats> CIRCLET_MAIN_STAT_SUPPLIER = () -> {
        // Circlet of Logos
        float rng = random.nextFloat();
        return rng <= .22 ? Stats.HP_PERCENT : // 22.00% -> HP%
        rng <= .44 ? Stats.ATK_PERCENT : // 22.00% -> ATK%
        rng <= .66 ? Stats.DEF_PERCENT : // 22.00% -> DEF%
        rng <= .76 ? Stats.CRIT_RATE : // 10.00% -> CRIT Rate%
        rng <= .86 ? Stats.CRIT_DMG : // 10.00% -> CRIT DMG%
        rng <= .96 ? Stats.HEALING_BONUS : // 10.00% -> Healing Bonus%
        Stats.ELEMENTAL_MASTERY; // 4.00% -> Elemental Mastery
    };

    private static final Map<Type, Supplier<Stats>> MAIN_STAT_MAP = new EnumMap<>(Type.class);
    static {
        MAIN_STAT_MAP.put(Type.FLOWER, FLOWER_MAIN_STAT_SUPPLIER);
        MAIN_STAT_MAP.put(Type.PLUME, PLUME_MAIN_STAT_SUPPLIER);
        MAIN_STAT_MAP.put(Type.SANDS, SANDS_MAIN_STAT_SUPPLIER);
        MAIN_STAT_MAP.put(Type.GOBLET, GOBLET_MAIN_STAT_SUPPLIER);
        MAIN_STAT_MAP.put(Type.CIRCLET, CIRCLET_MAIN_STAT_SUPPLIER);
    }

    private final Supplier<Map<Stats, Double>> MAIN_STAT_SUPPLIER = () -> {
        mainStat = new EnumMap<>(Stats.class);
        Stats mainStatName = MAIN_STAT_MAP.get(artifactType).get();
        Double mainStatValue = Data.MAIN_STATS.get(mainStatName)[0];
        mainStat.put(mainStatName, mainStatValue);
        return mainStat;
    };

    
    ////// SUBSTAT SUPPLIERS
    

    private final Supplier<Map<Stats, Double>> SUBSTAT_SUPPLIER = () -> {
        subStats = new LinkedHashMap<>();
        int startingSubStats = random.nextInt(2) + 3; // 3 or 4.
        List<Stats> subStatsPool = new ArrayList<>(Data.SUB_STATS.keySet());
        for (Map.Entry<Stats, Double> mainStatEntry : mainStat.entrySet()) {
            // Remove main stat from substat pool.
            subStatsPool.remove(mainStatEntry.getKey());
        }
        for (int i = 0; i < startingSubStats; i++) {
            // Generates 3-4 random substats of varying rolls.
            int randomSubStatIndex = random.nextInt(subStatsPool.size());
            Stats randomSubStatName = subStatsPool.get(randomSubStatIndex);
            double[] randomSubStatValues = Data.SUB_STATS.get(randomSubStatName);
            double randomSubStatValue = randomSubStatValues[random.nextInt(randomSubStatValues.length)];
            subStats.put(randomSubStatName, randomSubStatValue);
            subStatsPool.remove(randomSubStatName);
        }
        return subStats;
    };


    public void printSampleOutput() {
        StringBuilder sampleOutput = new StringBuilder();
        sampleOutput.append(String.format("%s\n", artifactType.getType().toUpperCase()));
        sampleOutput.append("MAIN STAT\n");
        for (Map.Entry<Stats, Double> mainStatEntry: mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            sampleOutput.append(String.format("%-20s %.2f\n", mainStatName, mainStatValue));
        }
        sampleOutput.append("SUBSTATS\n");
        for (Map.Entry<Stats, Double> subStatsEntry: subStats.entrySet()) {
            String subStatName = subStatsEntry.getKey().getStat();
            double subStatValue = subStatsEntry.getValue();
            sampleOutput.append(String.format("%-20s %.2f\n", subStatName, subStatValue));
        }
        sampleOutput.append("\n");
        System.out.print(sampleOutput);
    }

}