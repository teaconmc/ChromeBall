package org.teacon.chromeball.common;

import com.mojang.logging.annotations.FieldsAreNonnullByDefault;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
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
    public static final TagKey<Item> ITEM_TAG;
    public static final TagKey<EntityType<?>> ENTITY_TYPE_TAG;

    public static final DeferredRegister.Items ITEMS;
    public static final DeferredRegister.Entities ENTITY_TYPES;
    public static final DeferredRegister<Identifier> CUSTOM_STATS;

    public static final DeferredItem<ChromeItem> ITEM;
    public static final DeferredHolder<EntityType<?>, EntityType<ChromeEntity>> ENTITY_TYPE;
    public static final DeferredHolder<Identifier, Identifier> HITS_BY_STAT;

    static {
        ITEM_TAG = TagKey.create(Registries.ITEM, fromNamespaceAndPath("c", "chromeballs"));
        ENTITY_TYPE_TAG = TagKey.create(Registries.ENTITY_TYPE, fromNamespaceAndPath("c", "chromeballs"));

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

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        var ray = event.getRayTraceResult();
        if (ray.getType() == HitResult.Type.ENTITY) {
            var source = event.getProjectile();
            if (source.is(ENTITY_TYPE_TAG) || source instanceof ItemSupplier is && is.getItem().is(ITEM_TAG)) {
                var target = ((EntityHitResult) ray).getEntity();
                if (target instanceof ServerPlayer player) {
                    player.awardStat(ChromeBallRegistry.HITS_BY_STAT.get());
                }
            }
        }
    }
}
