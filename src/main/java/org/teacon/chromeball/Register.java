package org.teacon.chromeball;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.teacon.chromeball.net.Networking;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {
    public static final Item CHROME_BALL = new ChromeBallItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    public static final EntityType<ChromeBallEntity> CHROME_BALL_ENTITY_ENTITY_TYPE = EntityType.Builder.<ChromeBallEntity>of(ChromeBallEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("chrome");

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(
                CHROME_BALL.setRegistryName("chrome")
        );
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {

        event.getRegistry().registerAll(
                CHROME_BALL_ENTITY_ENTITY_TYPE.setRegistryName("chrome")
        );
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        Networking.registerMessage();
    }

}
