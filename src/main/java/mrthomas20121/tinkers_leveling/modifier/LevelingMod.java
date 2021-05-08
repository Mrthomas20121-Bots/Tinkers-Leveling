package mrthomas20121.tinkers_leveling.modifier;

import mrthomas20121.tinkers_leveling.util.LevelHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ProjectileModifierTrait;

public class LevelingMod extends ProjectileModifierTrait {

    public static LevelingMod INSTANCE = new LevelingMod();

    public static String XP_TAG = "exp";

    private LevelingMod() {
        super("leveling_mod", 0x0);
        this.aspects.add(new ModifierAspect.DataAspect(this));
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
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if(player instanceof EntityPlayer) {
            if(wasEffective) {
                LevelHelper.addXP((EntityPlayer)player, tool, 20);
            }
            else {
                LevelHelper.addXP((EntityPlayer)player, tool, 10);
            }
        }
    }

    @Override
    public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
        LevelHelper.addXP(world.getNearestAttackablePlayer(projectile.getPosition(), 100, 100), toolStack, 10);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if(player instanceof EntityPlayer) {
            if(wasHit) {
                if(wasCritical) {
                    LevelHelper.addXP((EntityPlayer)player, tool, 40);
                }
                else {
                    LevelHelper.addXP((EntityPlayer)player, tool, 20);
                }
            }
        }
    }

    @Override
    public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
        if(attacker instanceof EntityPlayer) {
            if(impactSpeed > 0) {
                LevelHelper.addXP((EntityPlayer)attacker, ammoStack, 10);
            }
        }
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if(player instanceof EntityPlayer) {
            if(isCritical) {
                LevelHelper.addXP((EntityPlayer)player, tool, 10);
            }
            if(target.isDead) {
                LevelHelper.addXP((EntityPlayer)player, tool, 10);
            }
        }
    }

    @Override
    public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
        LevelHelper.addXP(player, tool, 10);
    }
}
