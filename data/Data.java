package data;
import java.util.EnumMap;
import java.util.Map;

public class Data {


    ////// ENUMS


    public enum Type {
        FLOWER("Flower of Life"),
        PLUME("Plume of Death"),
        SANDS("Sands of Eon"),
        GOBLET("Goblet of Eonothem"),
        CIRCLET("Circlet of Logos");
        private final String artifactType;
        private Type(String artifactType) {
            this.artifactType = artifactType;
        }
        public String getType() {
            return artifactType;
        }
    }

    public enum Stats {
        HP("HP"),
        ATK("ATK"),
        DEF("DEF"),
        HP_PERCENT("HP%"),
        ATK_PERCENT("ATK%"),
        DEF_PERCENT("DEF%"),
        PHYSICAL_DMG_BONUS("Physical DMG Bonus%"),
        PYRO_DMG_BONUS("Pyro DMG Bonus%"),
        ELECTRO_DMG_BONUS("Electro DMG Bonus%"),
        CRYO_DMG_BONUS("Cryo DMG Bonus%"),
        HYDRO_DMG_BONUS("Hydro DMG Bonus%"),
        DENDRO_DMG_BONUS("Dendro DMG Bonus%"),
        ANEMO_DMG_BONUS("Anemo DMG Bonus%"),
        GEO_DMG_BONUS("Geo DMG Bonus%"),
        ELEMENTAL_MASTERY("Elemental Mastery"),
        ENERGY_RECHARGE("Energy Recharge%"),
        CRIT_RATE("Crit Rate%"),
        CRIT_DMG("Crit DMG%"),
        HEALING_BONUS("Healing Bonus&");
        // All stats you can get from artifacts.
        private final String statName;
        private Stats (String statName) {
            this.statName = statName;
        }
        public String getStat() {
            return statName;
        }
    }


    ////// HASHMAPS


    public static final Map<Stats, double[]> MAIN_STATS = new EnumMap<>(Stats.class);
    static {
        MAIN_STATS.put(Stats.HP, new double[] {717, 920, 1123, 1326, 1530, 1733, 1936, 2139, 2342, 2545, 2749, 2952, 3155, 3358, 3561, 3764, 3967, 4171, 4374, 4577, 4780});
        MAIN_STATS.put(Stats.ATK, new double[] {47, 60, 73, 86, 100, 113, 126, 139, 152, 166, 179, 192, 205, 219, 232, 245, 258, 272, 285, 298, 311});
        MAIN_STATS.put(Stats.HP_PERCENT, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.ATK_PERCENT, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.DEF_PERCENT, new double[] {8.7, 11.2, 13.7, 16.2, 18.6, 21.1, 23.6, 26.1, 28.6, 31.0, 33.5, 36.0, 38.5, 40.9, 43.4, 45.9, 48.4, 50.8, 53.3, 55.8, 58.3});
        MAIN_STATS.put(Stats.PHYSICAL_DMG_BONUS, new double[] {8.7, 11.2, 13.7, 16.2, 18.6, 21.1, 23.6, 26.1, 28.6, 31.0, 33.5, 36.0, 38.5, 40.9, 43.4, 45.9, 48.4, 50.8, 53.3, 55.8, 58.3});
        MAIN_STATS.put(Stats.PYRO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.ELECTRO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.CRYO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.HYDRO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.DENDRO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.ANEMO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.GEO_DMG_BONUS, new double[] {7.0, 9.0, 11.0, 12.9, 14.9, 16.9, 18.9, 20.9, 22.8, 24.8, 26.8, 28.8, 30.8, 32.8, 34.7, 36.7, 38.7, 40.7, 42.7, 44.6, 46.6});
        MAIN_STATS.put(Stats.ELEMENTAL_MASTERY, new double[] {28.0, 35.9, 43.8, 51.8, 59.7, 67.6, 75.5, 83.5, 91.4, 99.3, 107.2, 115.2, 123.1, 131.0, 138.9, 146.9, 154.8, 162.7, 170.6, 178.6, 186.5});
        MAIN_STATS.put(Stats.ENERGY_RECHARGE, new double[] {7.8, 10.0, 12.2, 14.4, 16.6, 18.8, 21.0, 23.2, 25.4, 27.6, 29.8, 32.0, 34.2, 36.4, 38.6, 40.8, 43.0, 45.2, 47.4, 49.6, 51.8});
        MAIN_STATS.put(Stats.CRIT_RATE, new double[] {4.7, 6.0, 7.3, 8.6, 9.9, 11.3, 12.6, 13.9, 15.2, 16.6, 17.9, 19.2, 20.5, 21.8, 23.2, 24.5, 25.8, 27.1, 28.4, 29.8, 31.1});
        MAIN_STATS.put(Stats.CRIT_DMG, new double[] {9.3, 12.0, 14.6, 17.3, 19.9, 22.5, 25.2, 27.8, 30.5, 33.1, 35.7, 38.4, 41.0, 43.7, 46.3, 49.0, 51.6, 54.2, 56.9, 59.5, 62.2});
        MAIN_STATS.put(Stats.HEALING_BONUS, new double[] {5.4, 6.9, 8.4, 10.0, 11.5, 13.0, 14.5, 16.1, 17.6, 19.1, 20.6, 22.1, 23.7, 25.2, 26.7, 28.2, 29.8, 31.3, 32.8, 34.3, 35.9});
    } // The double[] arrays refer to +0, +1, +2... +20 because there is no clear increment for each level gain. There are hidden decimals that HoYoverse hasn't disclosed.

    public static final Map<Stats, double[]> SUB_STATS = new EnumMap<>(Stats.class);
    static {
        SUB_STATS.put(Stats.HP, new double[] {209.13, 239.00, 268.88, 298.75});
        SUB_STATS.put(Stats.ATK, new double[] {13.62, 15.56, 17.51, 19.45});
        SUB_STATS.put(Stats.DEF, new double[] {16.20, 18.52, 20.83, 23.15});
        SUB_STATS.put(Stats.HP_PERCENT, new double[] {4.08, 4.66, 5.25, 5.83});
        SUB_STATS.put(Stats.ATK_PERCENT, new double[] {4.08, 4.66, 5.25, 5.83});
        SUB_STATS.put(Stats.DEF_PERCENT, new double[] {5.10, 5.83, 6.56, 7.29});
        SUB_STATS.put(Stats.ELEMENTAL_MASTERY, new double[] {16.32, 18.65, 20.98, 23.31});
        SUB_STATS.put(Stats.ENERGY_RECHARGE, new double[] {4.53, 5.18, 5.83, 6.48});
        SUB_STATS.put(Stats.CRIT_RATE, new double[] {2.72, 3.11, 3.50, 3.89});
        SUB_STATS.put(Stats.CRIT_DMG, new double[] {5.44, 6.22, 6.99, 7.77});
    } // The arrays refer to low and high rolls. 70%/80%/90%/100% of the max value.

}