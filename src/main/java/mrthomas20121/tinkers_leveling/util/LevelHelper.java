package mrthomas20121.tinkers_leveling.util;

import mrthomas20121.tinkers_leveling.ConfigurationFile;
import mrthomas20121.tinkers_leveling.ModNBT;
import mrthomas20121.tinkers_leveling.TinkersLeveling;
import mrthomas20121.tinkers_leveling.modifier.LevelingMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class LevelHelper {

    public enum Level {
        beginner(1, 2, 0, 1000),
        rookie(2, 3, 1000, 5000),
        adept(3, 4, 5000, 10000),
        expert(4, 5, 10000, 15000),
        pro(5, 6, 15000, 20000),
        master(6, 6, 20000, 50000);

        private final String name;
        private final int nextLevel;
        private final int level;
        private final int minXP;
        private final int maxXP;

        Level(int level, int nextLevel, int minXP, int maxXP) {
            this.level = level;
            this.nextLevel = nextLevel;
            this.minXP = minXP;
            this.maxXP = maxXP;
            this.name = "tooltip.level."+level;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getMinXP() {
            return minXP;
        }

        public int getMaxXP() {
            return maxXP;
        }

        public Level getNextLevel() {
            switch (nextLevel) {
                case 1:
                    return beginner;
                case 2:
                    return rookie;
                case 3:
                    return adept;
                case 4:
                    return expert;
                case 5:
                    return pro;
                case 6:
                    return master;
                default:
                    return beginner;
            }
        }

        public int getLevelExp() {
            return level;
        }

        public double getLevelMultplier() {
            if(level == 5) {
                return ConfigurationFile.ConfigFile.toolStatsMultiplier+1;
            }
            return ConfigurationFile.ConfigFile.toolStatsMultiplier;
        }

        public double getToolBonus(double base_stat) {
            return base_stat * (level * getLevelMultplier());
        }

        public int getBonusExp() {
            if(level > 2) {
                return 2;
            }
            return 1;
        }
    }

    public static Level getLevelFrom(int level) {
        for(Level l: Level.values()) {
            if(l.getLevel() == level) {
                return l;
            }
        }
        return Level.beginner;
    }

    public static void addXP(EntityPlayer player, ItemStack tool, int amount) {
        NBTTagList tagList = TagUtil.getModifiersTagList(tool);
        NBTTagCompound modifiertag = getTag(tagList);
        ModNBT modNBT = getModData(modifiertag);
        Level level = getLevelFrom(modNBT.level);
        Level nextLevel = level.getNextLevel();
        int max_xp = level.getMaxXP();

        int bonus = 0;

        modNBT.exp+=amount*level.getBonusExp();

        if(modNBT.exp > max_xp) {
            bonus = modNBT.exp-max_xp;
            modNBT.exp = max_xp;
        }

        // check if we reach or get higher than the max XP for that level
        if(modNBT.exp == max_xp) {
            updateToolStats(tool, level);
            TinkersLeveling.proxy.sendLevelUpMessage(nextLevel, tool, player);
            TinkersLeveling.proxy.playSound(player);
            // check if we reached the last level
            if(nextLevel != level) {
                modNBT.level++;
            }
        }
        modNBT.exp+=bonus;

        modNBT.write(modifiertag);
        TagUtil.setModifiersTagList(tool, tagList);
    }

    private static void updateToolStats(ItemStack stack, Level level) {
        ToolNBT tag = TagUtil.getToolStats(stack);
        tag.attackSpeedMultiplier+= level.getToolBonus(tag.attackSpeedMultiplier);
        tag.attack+= level.getToolBonus(tag.attack);
        tag.harvestLevel++;
        tag.speed+= level.getToolBonus(tag.speed);
        tag.durability+= level.getToolBonus(tag.durability);
        TagUtil.setToolTag(stack, tag.get());

        // update the modifier slots
        NBTTagCompound tooltag = TagUtil.getToolTag(stack);
        int modifiers = tooltag.getInteger(Tags.FREE_MODIFIERS)+1;
        tooltag.setInteger(Tags.FREE_MODIFIERS, Math.max(0, modifiers));
    }

    public static NBTTagCompound getTag(NBTTagList tagList) {
        int index = TinkerUtil.getIndexInCompoundList(tagList, LevelingMod.INSTANCE.identifier);
        return tagList.getCompoundTagAt(index);
    }

    public static ModNBT getModData(NBTTagCompound tag) {
        return new ModNBT(tag);
    }
}
