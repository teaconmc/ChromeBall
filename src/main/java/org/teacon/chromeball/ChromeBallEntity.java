package org.teacon.chromeball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

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

    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity instanceof ServerPlayerEntity && entity.getServer() != null) {
                ServerScoreboard board = entity.getServer().getScoreboard();

                board.forAllObjectives(CHROME, entity.getScoreboardName(), Score::incrementScore);
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }

    }
}
