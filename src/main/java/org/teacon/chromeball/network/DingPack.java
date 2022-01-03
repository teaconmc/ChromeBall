package org.teacon.chromeball.network;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.teacon.chromeball.client.ClientRenderer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum DingPack {
    INSTANCE;

    public static DingPack fromBytes(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    public void toBytes(FriendlyByteBuf buf) {
        // do nothing here
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientRenderer::ding));
        ctx.get().setPacketHandled(true);
    }
}
