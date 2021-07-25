package org.teacon.chromeball;

import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import static org.teacon.chromeball.ChromeBallEntity.CHROME;

@Mod(ChromeBall.MOD_ID)
@Mod.EventBusSubscriber
public class ChromeBall {
    public static final String MOD_ID = "chromeball";
    public static ScoreObjective O;

    public static Config config;

    public ChromeBall() {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        config = new Config(configBuilder);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, configBuilder.build(), MOD_ID + ".toml");
    }

    @SubscribeEvent
    public static void onFMLServerStartingEvent(FMLServerStartingEvent event) {
        O = event.getServer().getScoreboard().getObjective("chrome");
        if (O == null)
            O = event.getServer().getScoreboard().addObjective("chrome", CHROME, new TranslationTextComponent("chromeball.tab.1"), ScoreCriteria.RenderType.INTEGER);
        event.getServer().getScoreboard().startTrackingObjective(O);

        event.getServer().getScoreboard().setDisplayObjective(0, O);
    }


}
