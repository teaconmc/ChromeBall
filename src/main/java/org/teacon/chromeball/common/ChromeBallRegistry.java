package org.teacon.chromeball.common;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.teacon.chromeball.ChromeBall;
import org.teacon.chromeball.common.entity.ChromeLivingEntity;
import org.teacon.chromeball.common.entity.ChromeProjectileEntity;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.function.Supplier;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@EventBusSubscriber(modid = ChromeBall.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ChromeBallRegistry {
    public static final DeferredRegister<Item> ITEMS;
    public static final DeferredRegister<EntityType<?>> ENTITIES;
    public static final DeferredRegister<ResourceLocation> CUSTOM_STATS;

    public static final DeferredHolder<Item, ChromeItem> CHROME_BALL_ITEM;
    public static final DeferredHolder<EntityType<?>, EntityType<ChromeProjectileEntity>> PROJECTILE_ENTITY_TYPE;
    public static final DeferredHolder<EntityType<?>, EntityType<ChromeLivingEntity>> DOOR_CHROME_ENTITY_TYPE;
    public static final DeferredHolder<ResourceLocation, ResourceLocation> HITS_BY_STAT;
    public static final DeferredHolder<ResourceLocation, ResourceLocation> MERIT_STAT;

    static {
        ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, ChromeBall.MOD_ID);
        ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ChromeBall.MOD_ID);
        CUSTOM_STATS = DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, ChromeBall.MOD_ID);

        CHROME_BALL_ITEM = ITEMS.register("chrome", () -> new ChromeItem(new Item.Properties().stacksTo(16)));

        PROJECTILE_ENTITY_TYPE = ENTITIES.register("chrome", () -> EntityType.Builder
                .<ChromeProjectileEntity>of(ChromeProjectileEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("chrome"));

        DOOR_CHROME_ENTITY_TYPE = ENTITIES.register("door_chrome", () -> EntityType.Builder
                .of(ChromeLivingEntity::new, MobCategory.MISC)
                .sized(1, 1.5F)
                .nameTagOffset(0)
                .build("door_chrome"));

        HITS_BY_STAT = CUSTOM_STATS.register("hits_by", () -> fromNamespaceAndPath(ChromeBall.MOD_ID, "hits_by"));

        MERIT_STAT = CUSTOM_STATS.register("merit", () -> fromNamespaceAndPath(ChromeBall.MOD_ID, "merit"));
    }

    public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (CreativeModeTabs.SEARCH.equals(event.getTabKey())) {
            event.accept(ChromeBallRegistry.CHROME_BALL_ITEM.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SubscribeEvent
    public static void onEntityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(DOOR_CHROME_ENTITY_TYPE.get(), LivingEntity.createLivingAttributes().build());
    }
}
