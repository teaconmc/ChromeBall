package org.teacon.chromeball.common;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.teacon.chromeball.ChromeBall;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeBallRegistry {
    public static final DeferredRegister<Item> ITEMS;
    public static final DeferredRegister<EntityType<?>> ENTITIES;
    public static final DeferredRegister<ResourceLocation> CUSTOM_STATS;

    public static final DeferredHolder<Item, ChromeItem> ITEM;
    public static final DeferredHolder<EntityType<?>, EntityType<ChromeEntity>> ENTITY_TYPE;
    public static final DeferredHolder<ResourceLocation, ResourceLocation> HITS_BY_STAT;

    static {
        ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, ChromeBall.MOD_ID);
        ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ChromeBall.MOD_ID);
        CUSTOM_STATS = DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, ChromeBall.MOD_ID);

        ITEM = ITEMS.register("chrome", () -> new ChromeItem(new Item.Properties().stacksTo(16)));
        ENTITY_TYPE = ENTITIES.register("chrome", () -> EntityType.Builder
                .<ChromeEntity>of(ChromeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("chrome"));
        HITS_BY_STAT = CUSTOM_STATS.register("hits_by", () -> fromNamespaceAndPath(ChromeBall.MOD_ID, "hits_by"));
    }

    public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (CreativeModeTabs.SEARCH.equals(event.getTabKey())) {
            event.accept(ChromeBallRegistry.ITEM.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
