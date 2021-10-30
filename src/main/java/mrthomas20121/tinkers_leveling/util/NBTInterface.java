package mrthomas20121.tinkers_leveling.util;


import net.minecraft.item.ItemStack;

public interface NBTInterface<S> {

    void readNBT(S tag);

    void writeNBT(ItemStack tool);

    void writeNBT(S tag);
}
