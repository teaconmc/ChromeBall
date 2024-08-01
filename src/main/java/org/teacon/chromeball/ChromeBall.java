package org.teacon.chromeball;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.teacon.chromeball.client.ClientRenderer;
import org.teacon.chromeball.common.ChromeBallRegistry;
import org.teacon.chromeball.network.DingPack;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod(ChromeBall.MOD_ID)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeBall {
    public static final String MOD_ID = "chromeball";
    public static final Pair<Config, ModConfigSpec> CONFIG = new ModConfigSpec.Builder().configure(Config::new);

    public ChromeBall(IEventBus modBus, ModContainer container, Dist dist) {
        // register config
        container.registerConfig(ModConfig.Type.SERVER, CONFIG.getRight(), MOD_ID + ".toml");
        // register common event listeners
        ChromeBallRegistry.ITEMS.register(modBus);
        ChromeBallRegistry.ENTITIES.register(modBus);
        ChromeBallRegistry.CUSTOM_STATS.register(modBus);
        modBus.addListener(DingPack::registerMessage);
        modBus.addListener(ChromeBallRegistry::registerCreativeTabs);
        // register client listeners
        if (dist.isClient()) {
            modBus.addListener(ClientRenderer::registerRenderer);
        }
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public record Config(ModConfigSpec.DoubleValue rate) {
        public Config(ModConfigSpec.Builder builder) {
            this(builder.defineInRange("recovery_rate", 0.3D, 0D, 1D));
        }
    }
}
