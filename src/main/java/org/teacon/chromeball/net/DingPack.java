package org.teacon.chromeball.net;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DingPack {


    public void toBytes(PacketBuffer buf) {
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            run();
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public void run(){
        SoundEvent sound  = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft","entity.experience_orb.pickup"));
        if (sound!=null)
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(sound,1.0f));
    }
}
