package mrthomas20121.tinkers_leveling;

import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.tinkering.TinkersArmor;
import mrthomas20121.tinkers_leveling.modifier.LevelingMod;
import mrthomas20121.tinkers_leveling.modifier.LevelingModConarm;
import mrthomas20121.tinkers_leveling.tooltip.Tooltips;
import mrthomas20121.tinkers_leveling.util.LevelHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ConarmEventHandler {

    private ConarmEventHandler() {

    }

    public static ConarmEventHandler INSTANCE = new ConarmEventHandler();

    @SubscribeEvent
    public void onArmorBuild(ArmoryEvent.OnItemBuilding event) {
        LevelingModConarm.INSTANCE.apply(event.tag);

        NBTTagList tagList = TagUtil.getModifiersTagList(event.tag);
        NBTTagCompound tag = LevelingModConarm.getTag(tagList);
        tag.setInteger(LevelingMod.XP_TAG, 0);
        TagUtil.setModifiersTagList(event.tag, tagList);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public void onTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if(stack.getItem() instanceof TinkersArmor) {
            NBTTagCompound modifierTag = TinkerUtil.getModifierTag(stack, LevelingModConarm.INSTANCE.identifier);
            ModNBT modData = LevelHelper.getModData(modifierTag);
            LevelHelper.Level level = LevelHelper.getLevelFrom(modData.level);
            if(!Tooltips.isShiftDown() || !Tooltips.isCTRLDown()) event.getToolTip().add(1, new TextComponentTranslation("tooltip.xp", new TextComponentTranslation(level.getName()).getFormattedText(), modData.exp, level.getMaxXP()).getFormattedText());
        }
    }
}
