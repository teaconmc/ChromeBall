package org.teacon.chromeball.common;

import com.mojang.logging.annotations.FieldsAreNonnullByDefault;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeItem extends SnowballItem {
    public ChromeItem(Properties builder) {
        super(builder);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        // sound
        var x = player.getX();
        var y = player.getY();
        var z = player.getZ();
        var item = player.getItemInHand(hand);
        var pitch = 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F);
        world.playSound(player, x, y, z, SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, pitch);

        // entity
        if (world instanceof ServerLevel serverWorld) {
            Projectile.spawnProjectileFromRotation(ChromeEntity::new, serverWorld, item, player, 0.0F, 1.5F, 1.0F);
        }

        // stat
        player.awardStat(Stats.ITEM_USED.get(this));

        // result
        item.consume(1, player);
        return InteractionResult.SUCCESS;
    }
}
