package mrthomas20121.tinkers_leveling;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public class ModNBT extends ModifierNBT {

    public int exp;

    public ModNBT() {
        super();
    }

    public ModNBT(NBTTagCompound tag) {
        super(tag);
        exp = tag.getInteger("exp");
    }

    public static ModNBT readTag(NBTTagCompound tag) {
        ModNBT data = new ModNBT();
        if(tag != null) {
            data.read(tag);
        }

        return data;
    }

    @Override
    public void read(NBTTagCompound tag) {
        super.read(tag);
        this.exp = tag.getInteger("exp");
    }

    @Override
    public void write(NBTTagCompound tag) {
        super.write(tag);
        tag.setInteger("exp", this.exp);
    }
}
