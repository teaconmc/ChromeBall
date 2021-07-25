package org.teacon.chromeball;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class ChromeBallItem extends SnowballItem {
    public ChromeBallItem(Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isClientSide) {
            ChromeBallEntity ballEntity = new ChromeBallEntity(worldIn, playerIn);
            ballEntity.setItem(itemstack);
            ballEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
            worldIn.addFreshEntity(ballEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.success(itemstack);
    }
}
