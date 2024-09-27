package org.teacon.chromeball.common.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.teacon.chromeball.common.ChromeBallRegistry;
import org.teacon.chromeball.network.DingPack;

import java.util.List;

public class ChromeLivingEntity extends LivingEntity {
    private int clientClicked = 0;

    public ChromeLivingEntity(EntityType<? extends ChromeLivingEntity> entityType, Level level) {
        super(entityType, level);
        setInvulnerable(true);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return List.of();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot equipmentSlot, @NotNull ItemStack itemStack) {
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void tick() {
        super.tick();

        if (clientClicked > 0) {
            clientClicked -= 1;
        }
    }

    public int getClientClicked() {
        return clientClicked;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("chromeball.hint.merit_plus");
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        if (getClientClicked() == 0) {
            // Click cooldown for protecting 4z's head.
            clientClicked = 20;
            if (!level().isClientSide()) {
                player.awardStat(ChromeBallRegistry.MERIT_STAT.get());
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, DingPack.INSTANCE);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return super.interact(player, hand);
    }
}
