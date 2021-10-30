package mrthomas20121.tinkers_leveling.util;

import mrthomas20121.tinkers_leveling.TinkersLeveling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public interface LevelInterface {

    void updateStats(ItemStack stack, Level level);

    default void addXP(EntityPlayer player, ItemStack stack, int amount) {
        NBTTagCompound base = stack.getTagCompound();
        LevelingNBT levelingNBT = new LevelingNBT(base);
        Level level = Level.getLevelFrom(levelingNBT.level);
        Level nextLevel = level.getNextLevel();
        int max_xp = level.getMaxXP();

        // make sure we don't repeat the max level
        if(levelingNBT.exp >= Level.master.getMaxXP()) {
            if(!levelingNBT.checkExp) levelingNBT.checkExp = true;
        }

        // check if the exp is below maxXP
        if(levelingNBT.exp<Level.master.getMaxXP() && !levelingNBT.checkExp) {
            levelingNBT.exp+=amount*level.getLevel();

            if(levelingNBT.exp > max_xp) {
                levelingNBT.exp = max_xp;
            }
            // check if we reach or get higher than the max XP for that level
            if(levelingNBT.exp == max_xp) {
                updateStats(stack, level);
                TinkersLeveling.proxy.sendLevelUpMessage(nextLevel, stack, player);
                TinkersLeveling.proxy.playSound(player);
                // levelup
                levelingNBT.level++;
                // reset exp when levelup
                levelingNBT.exp = 0;
            }

            levelingNBT.writeNBT(stack);
        }
    }

    default NBTTagCompound getTag(String tagName, ItemStack stack) {
        return getTag(tagName, Objects.requireNonNull(stack.getTagCompound()));
    }

    default NBTTagCompound getTag(String tagName, NBTTagCompound root) {
        return root.getCompoundTag(tagName);
    }
}
