package org.teacon.chromeball.network;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import org.teacon.chromeball.ChromeBall;
import org.teacon.chromeball.client.ClientRenderer;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum DingPack implements CustomPacketPayload, IPayloadHandler<DingPack> {
    INSTANCE;

    private static final String VERSION = "2.0";
    private static final Type<DingPack> TYPE = new Type<>(fromNamespaceAndPath(ChromeBall.MOD_ID, "ding"));

    public static void registerMessage(RegisterPayloadHandlersEvent event) {
        event.registrar(VERSION).playToClient(TYPE, StreamCodec.unit(INSTANCE), INSTANCE);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(DingPack dingPack, IPayloadContext context) {
        context.enqueueWork(ClientRenderer::ding);
    }
}
