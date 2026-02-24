package org.teacon.chromeball.common;

import com.mojang.logging.annotations.FieldsAreNonnullByDefault;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.teacon.chromeball.ChromeBall;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChromeBallRegistry {
    public static final DeferredRegister.Items ITEMS;
    public static final DeferredRegister.Entities ENTITY_TYPES;
    public static final DeferredRegister<Identifier> CUSTOM_STATS;

    public static final DeferredItem<ChromeItem> ITEM;
    public static final DeferredHolder<EntityType<?>, EntityType<ChromeEntity>> ENTITY_TYPE;
    public static final DeferredHolder<Identifier, Identifier> HITS_BY_STAT;

    static {
        ITEMS = DeferredRegister.createItems(ChromeBall.MOD_ID);
        ENTITY_TYPES = DeferredRegister.createEntities(ChromeBall.MOD_ID);
        CUSTOM_STATS = DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, ChromeBall.MOD_ID);

        ITEM = ITEMS.registerItem("chrome", ChromeItem::new, () -> new Properties().stacksTo(16));
        ENTITY_TYPE = ENTITY_TYPES.registerEntityType("chrome", ChromeEntity::new, MobCategory.MISC);
        HITS_BY_STAT = CUSTOM_STATS.register("hits_by", () -> fromNamespaceAndPath(ChromeBall.MOD_ID, "hits_by"));
    }

    public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (CreativeModeTabs.SEARCH.equals(event.getTabKey())) {
            event.accept(ChromeBallRegistry.ITEM.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
