package org.teacon.chromeball.network;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.teacon.chromeball.ChromeBall;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Networking {
    private static SimpleChannel instance;

    private static int id = 0;

    private static int nextID() {
        return id++;
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        instance = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(ChromeBall.MOD_ID + ":ding"),
                () -> "1.0", (clientVer) -> true, (serverVer) -> true);
        instance.registerMessage(
                nextID(),
                DingPack.class,
                DingPack::toBytes,
                DingPack::fromBytes,
                DingPack::handler
        );
    }

    public static void send(PacketDistributor.PacketTarget target, Object message) {
        instance.send(target, message);
    }
}
