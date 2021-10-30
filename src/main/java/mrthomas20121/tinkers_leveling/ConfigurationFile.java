package mrthomas20121.tinkers_leveling;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TinkersLeveling.MODID)
public class ConfigurationFile {

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(TinkersLeveling.MODID))
        {
            ConfigManager.sync(TinkersLeveling.MODID, Config.Type.INSTANCE);
        }
    }

    @Config(modid = TinkersLeveling.MODID)
    public static class ConfigFile {

        @Config.Comment("Tool Stats Multiplier")
        public static double toolStatsMultiplier = 0.1d;
    }
}
