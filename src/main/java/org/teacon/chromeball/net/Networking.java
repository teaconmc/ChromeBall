package org.teacon.chromeball.net;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.teacon.chromeball.ChromeBall;

public class Networking {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(ChromeBall.MOD_ID + ":ding"),
                () -> "1.0",
                (clientVer) -> true,
                (serverVer) -> true);
        INSTANCE.registerMessage(
                nextID(),
                DingPack.class,
                DingPack::toBytes,
                (buffer) -> new DingPack(),
                DingPack::handler
        );
    }
}