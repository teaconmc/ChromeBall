package org.teacon.chromeball.common;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.teacon.chromeball.ChromeBall;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CBHitsByStat {
    public static final ResourceLocation INSTANCE = new ResourceLocation(ChromeBall.MOD_ID + ":hits_by");

    @SubscribeEvent
    public static void registerStat(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.CUSTOM_STAT, INSTANCE, INSTANCE);
            Stats.CUSTOM.get(INSTANCE);
        });
    }
}
