package mrthomas20121.tinkers_leveling;

import mrthomas20121.tinkers_leveling.modifier.LevelingMod;
import mrthomas20121.tinkers_leveling.tooltip.Tooltips;
import mrthomas20121.tinkers_leveling.util.LevelingNBT;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.events.TinkerEvent;

@Mod.EventBusSubscriber(modid = TinkersLeveling.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void onToolBuild(TinkerEvent.OnItemBuilding event) {

        LevelingMod.INSTANCE.apply(event.tag);

        LevelingNBT levelingNBT = new LevelingNBT(event.tag);
        levelingNBT.writeNBT(event.tag);

        //TinkersLeveling.getLogger().info(event.tag);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent()
    public static void onTooltips(ItemTooltipEvent event) {
        Tooltips.addTooltip(event.getItemStack(), event.getToolTip());
    }
}
