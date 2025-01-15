package artifact;
import data.Data;
import data.Data.Stats;
import data.Data.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class Artifact {
    
    private static final Random random = new Random();

    private Type artifactType;
    private Map<Stats, Double> mainStat;
    public Artifact() {
        initializeType();
        initializeMainStat();
    }


    ////// ARTIFACT TYPE METHODS
    

    private static final Supplier<Type> randomTypeSetter = () -> {
        // Generates a random artifact type.
        Type[] typeArray = Type.values();
        int randomType = random.nextInt(typeArray.length);
        return typeArray[randomType];
    };

    private void initializeType() {
        this.artifactType = randomTypeSetter.get();
    }


    ////// MAIN STAT METHODS


    // RNG for unequal chances.
    private static float mainStatRNG() {
        // Generates a number from 0 to 1 including decimal points.
        // Perfect for determining chances.
        return random.nextFloat();
    }

    // Main Setter method
    private void initializeMainStatMap(Stats mainStatName) {
        this.mainStat = new HashMap<>();
        mainStat.put(mainStatName, Data.MAIN_STATS.get(mainStatName)[0]);
    }

    private void initializeMainStatFlower() {
        // Flower of Life
        initializeMainStatMap(Data.Stats.HP); // 100% -> HP
    }
    
    private void initializeMainStatPlume() {
        // Plume of Death
        initializeMainStatMap(Stats.ATK); // 100% -> ATK
    }
    
    private void initializeMainStatSands() {
        // Sands of Eon
        float rng = mainStatRNG();
        if (rng <= .2668) { // 26.68% -> HP%
            initializeMainStatMap(Stats.HP_PERCENT);
        } else if (rng <= .5334) { // 26.66% -> ATK%
            initializeMainStatMap(Stats.ATK_PERCENT);
        } else if (rng <= .8) { // 26.66% -> DEF%
            initializeMainStatMap(Stats.DEF_PERCENT);
        } else if (rng <= .9) { // 10.00% -> Energy Recharge%
            initializeMainStatMap(Stats.ENERGY_RECHARGE);
        } else { // 10.00% -> Elemental Mastery
            initializeMainStatMap(Stats.ELEMENTAL_MASTERY);
        }
    }
    
    private void initializeMainStatGoblet() {
        // Goblet of Eonothem
        float rng = mainStatRNG();
        if (rng <= .1925) { // 19.25% -> HP%
            initializeMainStatMap(Stats.HP_PERCENT);
        } else if (rng <= .385) { // 19.25% -> ATK%
            initializeMainStatMap(Stats.ATK_PERCENT);
        } else if (rng <= .575) { // 19.00% -> DEF%
            initializeMainStatMap(Stats.DEF_PERCENT);
        } else if (rng <= .625) { // 5.00% -> Pyro DMG Bonus%
            initializeMainStatMap(Stats.PYRO_DMG_BONUS);
        } else if (rng <= .675) { // 5.00% -> Electro DMG Bonus%
            initializeMainStatMap(Stats.ELECTRO_DMG_BONUS);
        } else if (rng <= .725) { // 5.00% -> Cryo DMG Bonus%
            initializeMainStatMap(Stats.CRYO_DMG_BONUS);
        } else if (rng <= .775) { // 5.00% -> Hydro DMG Bonus%
            initializeMainStatMap(Stats.HYDRO_DMG_BONUS);
        } else if (rng <= .825) { // 5.00% -> Dendro DMG Bonus%
            initializeMainStatMap(Stats.DENDRO_DMG_BONUS);
        } else if (rng <= .875) { // 5.00% -> Anemo DMG Bonus%
            initializeMainStatMap(Stats.ANEMO_DMG_BONUS);
        } else if (rng <= .925) { // 5.00% -> Geo DMG Bonus%
            initializeMainStatMap(Stats.GEO_DMG_BONUS);
        } else if (rng <= .975) { // 5.00% -> Physical DMG Bonus%
            initializeMainStatMap(Stats.PHYSICAL_DMG_BONUS);
        } else { // 2.50% -> Elemental Mastery
            initializeMainStatMap(Stats.ELEMENTAL_MASTERY);
        }
    }
    
    private void initializeMainStatCirclet() {
        // Circlet of Logos
        float rng = mainStatRNG();
        if (rng <= .22) { // 22.00% -> HP%
            initializeMainStatMap(Stats.HP_PERCENT);
        } else if (rng <= .44) { // 22.00% -> ATK%
            initializeMainStatMap(Stats.ATK_PERCENT);
        } else if (rng <= .66) { // 22.00% -> DEF%
            initializeMainStatMap(Stats.DEF_PERCENT);
        } else if (rng <= .76) { // 10.00% -> CRIT Rate%
            initializeMainStatMap(Stats.CRIT_RATE);
        } else if (rng <= .86) { // 10.00% -> CRIT DMG%
            initializeMainStatMap(Stats.CRIT_DMG);
        } else if (rng <= .96) { // 10.00% -> Healing Bonus%
            initializeMainStatMap(Stats.HEALING_BONUS);
        } else { // 4.00% -> Elemental Mastery
            initializeMainStatMap(Stats.ELEMENTAL_MASTERY);
        }
    }
    
    private void initializeMainStat() {
        switch (artifactType) {
            case FLOWER -> initializeMainStatFlower();
            case PLUME -> initializeMainStatPlume();
            case SANDS -> initializeMainStatSands();
            case GOBLET -> initializeMainStatGoblet();
            case CIRCLET -> initializeMainStatCirclet();
        }
    }
 
    public void printSampleOutput() {
        StringBuilder sampleOutput = new StringBuilder();
        sampleOutput.append(String.format("%s\n\n", artifactType.getType().toUpperCase()));
        for (Map.Entry<Stats, Double> mainStatEntry: mainStat.entrySet()) {
            String mainStatName = mainStatEntry.getKey().getStat();
            double mainStatValue = mainStatEntry.getValue();
            sampleOutput.append("MAIN STAT\n");
            sampleOutput.append(String.format("%-15s %.2f\n\n", mainStatName, mainStatValue));
        }
        System.out.print(sampleOutput);
    }

}