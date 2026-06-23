package org.teacon.chromeball.common;

import com.mojang.logging.annotations.FieldsAreNonnullByDefault;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.teacon.chromeball.ChromeBall;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeEntity extends ThrowableItemProjectile {
    public ChromeEntity(EntityType<? extends ChromeEntity> type, Level world) {
        super(type, world);
    }

    public ChromeEntity(Level world, LivingEntity thrower, ItemStack itemStack) {
        super(ChromeBallRegistry.ENTITY_TYPE.get(), thrower, world, itemStack);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EntityEvent.DEATH) {
            // noinspection resource
            var world = this.level();
            var particle = new ItemParticleOption(ParticleTypes.ITEM, this.getDefaultItem());
            for (var i = 0; i < 16; ++i) {
                world.addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        var world = this.level();
        if (!world.isClientSide()) {
            world.broadcastEntityEvent(this, EntityEvent.DEATH);
            this.remove(RemovalReason.DISCARDED);
            var config = ChromeBall.CONFIG.getLeft();
            if (world.getRandom().nextDouble() < config.rate().getAsDouble()) {
                var item = ChromeBallRegistry.ITEM.toStack();
                world.addFreshEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), item));
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ChromeBallRegistry.ITEM.get();
    }
}
