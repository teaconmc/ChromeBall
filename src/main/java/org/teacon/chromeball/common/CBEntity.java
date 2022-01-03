package org.teacon.chromeball.common;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.PacketDistributor;
import org.teacon.chromeball.ChromeBall;
import org.teacon.chromeball.network.DingPack;
import org.teacon.chromeball.network.Networking;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CBEntity extends ThrowableItemProjectile {

    public CBEntity(EntityType<? extends CBEntity> type, Level world) {
        super(type, world);
    }

    public CBEntity(Level world, LivingEntity thrower) {
        super(CBRegistry.ENTITY_TYPE.get(), thrower, world);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            var particle = new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
            for (var i = 0; i < 16; ++i) {
                this.level.addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            var entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof ServerPlayer player && entity.getServer() != null) {
                Networking.send(PacketDistributor.PLAYER.with(() -> player), DingPack.INSTANCE);
                player.awardStat(CBHitsByStat.INSTANCE);
            }
        }

        if (!this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
            if (this.level.random.nextDouble() < ChromeBall.getConfig().getRateValue()) {
                var item = new ItemStack(CBRegistry.ITEM.get());
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), item));
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return CBRegistry.ITEM.get();
    }
}
