package org.teacon.chromeball;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public final ForgeConfigSpec.DoubleValue rate;

    private double rateValue = -1;

    public Config(ForgeConfigSpec.Builder builder)
    {
        rate = builder
                .defineInRange("recovery_rate", 0.3D, 0D, 1D);
    }

    public double getRateValue() {
        if (rateValue<0)rateValue=rate.get();
        return rateValue;
    }
}
