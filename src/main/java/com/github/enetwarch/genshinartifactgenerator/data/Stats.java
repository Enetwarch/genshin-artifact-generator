package com.github.enetwarch.genshinartifactgenerator.data;

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

    private final String statName;

    private Stats (String statName) {
        this.statName = statName;
    }

    public String getStat() {
        return statName;
    }

}