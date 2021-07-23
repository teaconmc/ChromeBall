package org.teacon.chromeball;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;

@Mod(ChromeBall.MOD_ID)
@Mod.EventBusSubscriber
public class ChromeBall {
    public static final String MOD_ID = "chromeball";
    public static ObjectiveCriteria O;

    public static Config config;

    public ChromeBall() {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        config = new Config(configBuilder);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, configBuilder.build(), MOD_ID + ".toml");
    }

    @SubscribeEvent
    public static void onFMLServerStartingEvent(FMLServerStartingEvent event) {
//        O = event.getServer().getScoreboard().getObjective("chrome");
//        if (O == null)
//            O = event.getServer().getScoreboard().addObjective("chrome", CHROME, new TranslatableComponent("chromeball.tab.1"), ObjectiveCriteria.RenderType.INTEGER);
//
//        event.getServer().getScoreboard().setObjectiveInDisplaySlot(0, O);
    }


}
