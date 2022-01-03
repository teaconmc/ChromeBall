package org.teacon.chromeball;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.teacon.chromeball.common.CBRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod(ChromeBall.MOD_ID)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeBall {
    public static final String MOD_ID = "chromeball";

    private static Config modConfig;

    public ChromeBall() {
        var builder = new ForgeConfigSpec.Builder();
        modConfig = new Config(builder);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, builder.build(), MOD_ID + ".toml");

        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CBRegistry.ITEMS.register(eventBus);
        CBRegistry.ENTITIES.register(eventBus);
    }

    public static Config getConfig() {
        return modConfig;
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public static class Config {
        public final ForgeConfigSpec.DoubleValue rate;

        private double rateValue = -1;

        public Config(ForgeConfigSpec.Builder builder) {
            this.rate = builder.defineInRange("recovery_rate", 0.3D, 0D, 1D);
        }

        public double getRateValue() {
            if (this.rateValue < 0) {
                this.rateValue = rate.get();
            }
            return this.rateValue;
        }
    }
}
