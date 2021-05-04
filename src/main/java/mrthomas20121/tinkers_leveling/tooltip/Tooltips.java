package mrthomas20121.tinkers_leveling.tooltip;

import mrthomas20121.tinkers_leveling.ModNBT;
import mrthomas20121.tinkers_leveling.util.LevelHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.List;

public class Tooltips {

    private Tooltips() {

    }

    public static void addTooltip(ItemStack stack, List<String> tooltips) {
        if(stack.getItem() instanceof ToolCore && stack.hasTagCompound()) {
            NBTTagList tagList = TagUtil.getModifiersTagList(stack);
            NBTTagCompound tag = LevelHelper.getTag(tagList);
            ModNBT modData = LevelHelper.getModData(tag);
            LevelHelper.Level level = LevelHelper.getLevelFrom(modData.level);
            if(!isShiftDown() || !isCTRLDown()) tooltips.add(1, new TextComponentTranslation("tooltip.xp", new TextComponentTranslation(level.getName()).getFormattedText(), modData.exp, level.getMaxXP()).getFormattedText());
        }
    }

    public static boolean isShiftDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
    public static boolean isCTRLDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
    }
}
