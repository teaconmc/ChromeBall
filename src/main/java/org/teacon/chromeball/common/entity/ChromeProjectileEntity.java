package org.teacon.chromeball.common.entity;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.teacon.chromeball.ChromeBall;
import org.teacon.chromeball.common.ChromeBallRegistry;
import org.teacon.chromeball.network.DingPack;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeProjectileEntity extends ThrowableItemProjectile {

    public ChromeProjectileEntity(EntityType<? extends ChromeProjectileEntity> type, Level world) {
        super(type, world);
    }

    public ChromeProjectileEntity(Level world, LivingEntity thrower) {
        super(ChromeBallRegistry.PROJECTILE_ENTITY_TYPE.get(), thrower, world);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            var world = this.level();
            var particle = new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
            for (var i = 0; i < 16; ++i) {
                world.addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            var entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof ServerPlayer player && entity.getServer() != null) {
                PacketDistributor.sendToPlayer(player, DingPack.INSTANCE);
                player.awardStat(ChromeBallRegistry.HITS_BY_STAT.get());
            }
        }

        var world = this.level();
        if (!world.isClientSide()) {
            world.broadcastEntityEvent(this, EntityEvent.DEATH);
            this.remove(RemovalReason.DISCARDED);
            var config = ChromeBall.CONFIG.getLeft();
            if (world.random.nextDouble() < config.rate().getAsDouble()) {
                var item = new ItemStack(ChromeBallRegistry.CHROME_BALL_ITEM.get());
                world.addFreshEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), item));
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ChromeBallRegistry.CHROME_BALL_ITEM.get();
    }
}
