package mrthomas20121.tinkers_leveling.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LevelingNBT implements NBTInterface<NBTTagCompound> {
    public int level;
    public int exp;
    public boolean checkExp;

    public LevelingNBT(NBTTagCompound base) {
        this.readNBT(base);
    }

    public static NBTTagCompound getTag(NBTTagCompound base_tag) {
        if(base_tag != null && base_tag.hasKey(LevelingTags.toolArmorTag)) return base_tag.getCompoundTag(LevelingTags.toolArmorTag);
        return new NBTTagCompound();
    }

    @Override
    public void readNBT(NBTTagCompound base) {
        NBTTagCompound tag = getTag(base);

        this.level = tag.getInteger("level");
        this.exp = tag.getInteger("exp");
        this.checkExp = tag.getBoolean("check");
    }

    @Override
    public void writeNBT(ItemStack tool) {
        if (tool.hasTagCompound() && tool.getTagCompound() != null) {
            NBTTagCompound base = tool.getTagCompound();
            writeNBT(base);
        }
    }

    @Override
    public void writeNBT(NBTTagCompound base) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("level", this.level);
        tag.setInteger("exp", this.exp);
        tag.setBoolean("check", this.checkExp);
        base.setTag(LevelingTags.toolArmorTag, tag);
    }
}
