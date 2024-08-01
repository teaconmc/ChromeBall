package org.teacon.chromeball.client;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.teacon.chromeball.common.ChromeBallRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ClientRenderer {
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ChromeBallRegistry.ENTITY_TYPE.get(), ThrownItemRenderer::new);
    }

    public static void ding() {
        var sound = SoundEvents.EXPERIENCE_ORB_PICKUP;
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0f));
    }
}
