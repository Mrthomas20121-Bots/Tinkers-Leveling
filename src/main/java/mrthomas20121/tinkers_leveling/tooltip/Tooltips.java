package mrthomas20121.tinkers_leveling.tooltip;

import mrthomas20121.tinkers_leveling.util.Level;
import mrthomas20121.tinkers_leveling.util.LevelingNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.tools.ToolCore;

import java.util.List;

public class Tooltips {

    public static void addTooltip(ItemStack stack, List<String> tooltips) {
        if(stack.getItem() instanceof ToolCore && stack.hasTagCompound()) {
            NBTTagCompound base = stack.getTagCompound();
            LevelingNBT levelingNBT = new LevelingNBT(base);
            Level level = Level.getLevelFrom(levelingNBT.level);
            if (!isCTRLDown()) {
                tooltips.add(1, String.format("%s %s-%s %s/%s", Util.translate(level.getTranslationKey()), TextFormatting.WHITE, TextFormatting.GOLD, levelingNBT.exp, level.getMaxXP()));
            }
        }
    }

    public static boolean isShiftDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
    public static boolean isCTRLDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
    }
}
