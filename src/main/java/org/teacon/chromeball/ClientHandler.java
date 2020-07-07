package org.teacon.chromeball;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static org.teacon.chromeball.Register.CHROME_BALL_ENTITY_ENTITY_TYPE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientHandler {

    @SubscribeEvent
    public static void entityRender(FMLClientSetupEvent event) {
        Minecraft.getInstance().getRenderManager().register(CHROME_BALL_ENTITY_ENTITY_TYPE, new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(),
                Minecraft.getInstance().getItemRenderer()));
    }
}
