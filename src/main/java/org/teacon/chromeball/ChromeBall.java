package org.teacon.chromeball;

import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import static org.teacon.chromeball.ChromeBallEntity.CHROME;

@Mod(ChromeBall.MOD_ID)
@Mod.EventBusSubscriber
public class ChromeBall {
    public static final String MOD_ID = "chromeball";
    public static ScoreObjective O;
    @SubscribeEvent
    public static void onFMLServerStartingEvent(FMLServerStartingEvent event){
        O = event.getServer().getScoreboard().addObjective("chrome",CHROME,new TranslationTextComponent("chromeball.tab.1"), ScoreCriteria.RenderType.INTEGER);
        event.getServer().getScoreboard().addObjective(O);

        event.getServer().getScoreboard().setObjectiveInDisplaySlot(0,O);
    }


}
