package mrthomas20121.tinkers_leveling;

import mrthomas20121.tinkers_leveling.tooltip.Tooltips;
import mrthomas20121.tinkers_leveling.util.LevelHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.common.Sounds;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void playSound(EntityPlayer player) {
        Sounds.PlaySoundForPlayer(player, SoundEvent.REGISTRY.getObject(new ResourceLocation("minecraft:entity.experience_orb.pickup")), 1f, 1f);
    }

    public void sendLevelUpMessage(LevelHelper.Level level, ItemStack tool, EntityPlayer player) {
        ITextComponent textComponent = new TextComponentString(new TextComponentTranslation("message.levelup", tool.getDisplayName(), new TextComponentTranslation(level.getName()).getFormattedText()).getFormattedText());
        player.sendStatusMessage(textComponent, true);
    }
}
