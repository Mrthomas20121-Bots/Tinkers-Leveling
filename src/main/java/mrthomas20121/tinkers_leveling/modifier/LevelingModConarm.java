package mrthomas20121.tinkers_leveling.modifier;

import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import c4.conarm.lib.traits.AbstractArmorTrait;
import mrthomas20121.tinkers_leveling.ModNBT;
import mrthomas20121.tinkers_leveling.TinkersLeveling;
import mrthomas20121.tinkers_leveling.util.LevelHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class LevelingModConarm extends ArmorModifierTrait {

    private LevelingModConarm() {
        super("leveling_mod_conarm", 0x0);
    }

    public static LevelingModConarm INSTANCE = new LevelingModConarm();

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
        addXP(player, tool, 20);
    }

    @Override
    public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        addXP(player, armor, 20);
        return super.onArmorDamage(armor, source, damage, newDamage, player, slot);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
        //addXP(player, armor, 20);
        return super.onDamaged(armor, player, source, damage, newDamage, evt);
    }

    @Override
    public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
        addXP(player, tool, 10);
        super.onBlock(tool, player, event);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if(damage > 0 && !source.isUnblockable()) {
            addXP(player, armor, 10);
        }
        return super.onHurt(armor, player, source, damage, newDamage, evt);
    }

    private static void addXP(EntityPlayer player, ItemStack tool, int amount) {
        NBTTagList tagList = TagUtil.getModifiersTagList(tool);
        NBTTagCompound modifiertag = getTag(tagList);
        ModNBT modNBT = LevelHelper.getModData(modifiertag);
        LevelHelper.Level level = LevelHelper.getLevelFrom(modNBT.level);
        LevelHelper.Level nextLevel = level.getNextLevel();
        int max_xp = level.getMaxXP();

        int bonus = 0;

        modNBT.exp+=amount*level.getBonusExp();

        if(modNBT.exp > max_xp) {
            bonus = modNBT.exp-max_xp;
            modNBT.exp = max_xp;
        }

        // check if we reach or get higher than the max XP for that level
        if(modNBT.exp == max_xp) {
            updateToolStats(tool, level);
            TinkersLeveling.proxy.sendLevelUpMessage(nextLevel, tool, player);
            TinkersLeveling.proxy.playSound(player);
            // check if we reached the last level
            if(nextLevel != level) {
                modNBT.level++;
            }
        }
        modNBT.exp+=bonus;

        modNBT.write(modifiertag);
        TagUtil.setModifiersTagList(tool, tagList);
    }

    private static void updateToolStats(ItemStack stack, LevelHelper.Level level) {
        ArmorNBT tag = ArmorTagUtil.getArmorStats(stack);
        tag.toughness+= level.getToolBonus(tag.toughness);
        tag.defense+= level.getToolBonus(tag.defense);
        tag.durability+= level.getToolBonus(tag.durability);
        TagUtil.setToolTag(stack, tag.get());

        // update the modifier slots
        NBTTagCompound tooltag = TagUtil.getToolTag(stack);
        int modifiers = tooltag.getInteger(Tags.FREE_MODIFIERS)+1;
        tooltag.setInteger(Tags.FREE_MODIFIERS, Math.max(0, modifiers));
    }

    public static NBTTagCompound getTag(NBTTagList tagList) {
        int index = TinkerUtil.getIndexInCompoundList(tagList, LevelingModConarm.INSTANCE.identifier);
        return tagList.getCompoundTagAt(index);
    }
}
