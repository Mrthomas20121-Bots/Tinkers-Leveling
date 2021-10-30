package mrthomas20121.tinkers_leveling.util;

import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorNBT;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.utils.TagUtil;

public class ArmorLevelData implements LevelInterface {

    @Override
    public void updateStats(ItemStack stack, Level level) {
        ArmorNBT armorNBT = ArmorTagUtil.getOriginalArmorStats(stack);
        armorNBT.durability*=1.5f;
        armorNBT.defense+=(armorNBT.defense*level.getLevelMultplier());
        armorNBT.toughness+=1f;
        TagUtil.setToolTag(stack, armorNBT.get());
    }
}
