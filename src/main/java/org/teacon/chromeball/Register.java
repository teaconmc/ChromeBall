package org.teacon.chromeball;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.teacon.chromeball.net.Networking;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {
    public static final Item CHROME_BALL = new ChromeBallItem(new Item.Properties().group(ItemGroup.MISC));
    public static final EntityType<ChromeBallEntity> CHROME_BALL_ENTITY_ENTITY_TYPE = EntityType.Builder.<ChromeBallEntity>create(ChromeBallEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build("chrome");

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
