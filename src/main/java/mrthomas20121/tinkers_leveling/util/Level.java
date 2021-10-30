package mrthomas20121.tinkers_leveling.util;

import mrthomas20121.tinkers_leveling.ConfigurationFile;

public enum Level {
    beginner(1, 2, 1000),
    rookie(2, 3, 5000),
    adept(3, 4, 10000),
    expert(4, 5, 15000),
    pro(5, 6, 20000),
    master(6, 6, 50000);

    private final String translationKey;
    private final int nextLevel;
    private final int level;
    private final int maxXP;

    Level(int level, int nextLevel, int maxXP) {
        this.level = level;
        this.nextLevel = nextLevel;
        this.maxXP = maxXP;
        this.translationKey = "tooltip.level." + level;
    }

    public static Level getLevelFrom(int level) {
        switch (level) {
            case 2:
                return Level.rookie;
            case 3:
                return Level.adept;
            case 4:
                return Level.expert;
            case 5:
                return Level.pro;
            case 6:
                return Level.master;
            default:
                return Level.beginner;
        }
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxXP() {
        return maxXP;
    }

    public Level getNextLevel() {
        return getLevelFrom(this.nextLevel);
    }

    public double getLevelMultplier() {
        return ConfigurationFile.ConfigFile.toolStatsMultiplier;
    }
}
