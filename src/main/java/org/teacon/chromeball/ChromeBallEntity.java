package org.teacon.chromeball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import org.teacon.chromeball.net.DingPack;
import org.teacon.chromeball.net.Networking;

public class ChromeBallEntity extends SnowballEntity {
    public static final ScoreCriteria CHROME = new ScoreCriteria("chrome");

    public ChromeBallEntity(EntityType<? extends SnowballEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public ChromeBallEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    public ChromeBallEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    protected void onHit(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity instanceof ServerPlayerEntity && entity.getServer() != null) {
                ServerScoreboard board = entity.getServer().getScoreboard();
                board.forAllObjectives(CHROME, entity.getScoreboardName(), Score::increment);
                Networking.INSTANCE.send(
                        PacketDistributor.PLAYER.with(
                                () -> (ServerPlayerEntity) entity
                        ),
                        new DingPack());
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
            if (Math.random() < ChromeBall.config.getRateValue()) {
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Register.CHROME_BALL)));
            }
        }

    }
}
