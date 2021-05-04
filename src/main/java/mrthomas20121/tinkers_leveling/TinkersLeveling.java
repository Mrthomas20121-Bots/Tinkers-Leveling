package mrthomas20121.tinkers_leveling;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TinkersLeveling.MODID, name = TinkersLeveling.NAME, version = TinkersLeveling.VERSION, dependencies = "required-after:tconstruct")
public class TinkersLeveling {

    @Mod.Instance
    public static TinkersLeveling instance;
    public static final String MODID = "tinkers_leveling";
    public static final String NAME = "Tinkers' Leveling";
    public static final String VERSION = "1.0.0";

    @SidedProxy(serverSide = "mrthomas20121.tinkers_leveling.CommonProxy", clientSide = "mrthomas20121.tinkers_leveling.ClientProxy")
    public static CommonProxy proxy;

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        if(Loader.isModLoaded("conarm")) {
            MinecraftForge.EVENT_BUS.register(ConarmEventHandler.INSTANCE);
        }
    }
}
