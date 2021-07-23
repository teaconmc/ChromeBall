package org.teacon.chromeball;


import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.teacon.chromeball.net.DingPack;
import org.teacon.chromeball.net.Networking;


public class ChromeBallEntity extends Snowball {
//    public static final ObjectiveCriteria CHROME = new ObjectiveCriteria("chrome");

    public ChromeBallEntity(EntityType<? extends Snowball> p_i50159_1_, Level p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public ChromeBallEntity(Level worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    public ChromeBallEntity(Level worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof ServerPlayer && entity.getServer() != null) {
                ServerScoreboard board = entity.getServer().getScoreboard();
//                board.forAllObjectives(CHROME, entity.getScoreboardName(), Score::incrementScore);
                Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(
                                () -> (ServerPlayer) entity
                        ),
                        new DingPack());
            }
        }

        if (!this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
            if (Math.random() < ChromeBall.config.getRateValue()) {
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Register.CHROME_BALL)));
            }
        }

    }
}
