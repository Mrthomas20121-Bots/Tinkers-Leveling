package mrthomas20121.tinkers_leveling.modifier;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import mrthomas20121.tinkers_leveling.util.ArmorLevelData;
import mrthomas20121.tinkers_leveling.util.LevelInterface;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LevelingModConarm extends ArmorModifierTrait {

    public static LevelingModConarm INSTANCE = new LevelingModConarm();

    public LevelInterface levelData = new ArmorLevelData();

    private LevelingModConarm() {
        super("leveling_mod_conarm", 0x0);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return true;
    }

    @Override
    public void onPlayerHurt(ItemStack tool, EntityPlayer player, EntityLivingBase attacker, LivingHurtEvent event) {
        levelData.addXP(player, tool, 20);
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        levelData.addXP(player, armor, 20);
        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        // add xp if the source is not fire
        if(!source.isFireDamage()) {
            levelData.addXP(player, armor, (int) damage);
        }
        ArmorHelper.damageArmor(armor, source, (int) damage, player);
        return newDamage;
    }

    @Override
    public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
        levelData.addXP(player, tool, 10);
        super.onBlock(tool, player, event);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if(damage > 0 && !source.isUnblockable()) {
            levelData.addXP(player, armor, 10);
        }
        return super.onHurt(armor, player, source, damage, newDamage, evt);
    }
}
