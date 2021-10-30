package mrthomas20121.tinkers_leveling;

import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.tinkering.TinkersArmor;
import mrthomas20121.tinkers_leveling.modifier.LevelingModConarm;
import mrthomas20121.tinkers_leveling.tooltip.Tooltips;
import mrthomas20121.tinkers_leveling.util.Level;
import mrthomas20121.tinkers_leveling.util.LevelingNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.Util;

public class ConarmEventHandler {

    public static ConarmEventHandler INSTANCE = new ConarmEventHandler();

    @SubscribeEvent
    public void onArmorBuild(ArmoryEvent.OnItemBuilding event) {
        LevelingModConarm.INSTANCE.apply(event.tag);

        LevelingNBT levelingNBT = new LevelingNBT(event.tag);
        levelingNBT.writeNBT(event.tag);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public void onTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if(stack.getItem() instanceof TinkersArmor) {
            LevelingNBT levelingNBT = new LevelingNBT(stack.getTagCompound());
            Level level = Level.getLevelFrom(levelingNBT.level);
            if(!Tooltips.isCTRLDown()) {
                event.getToolTip().add(1, String.format("%s %s-%s %s/%s", Util.translate(level.getTranslationKey()), TextFormatting.WHITE, TextFormatting.GOLD, levelingNBT.exp, level.getMaxXP()));
            }
        }
    }
}
