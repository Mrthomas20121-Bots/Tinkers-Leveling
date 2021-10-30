package mrthomas20121.tinkers_leveling.util;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;

public class ToolLevelData implements LevelInterface {

    @Override
    public void updateStats(ItemStack stack, Level level) {
        ToolNBT toolNBT = TagUtil.getOriginalToolStats(stack);
        toolNBT.attackSpeedMultiplier+=(toolNBT.attackSpeedMultiplier*level.getLevelMultplier());
        toolNBT.durability*=1.5f;
        toolNBT.speed+=(toolNBT.speed*level.getLevelMultplier());
        toolNBT.attack+=(toolNBT.attack*level.getLevelMultplier());
        TagUtil.setToolTag(stack, toolNBT.get());
    }
}
