package org.teacon.chromeball.common;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.teacon.chromeball.ChromeBall;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CBRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChromeBall.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChromeBall.MOD_ID);

    public static final RegistryObject<CBItem> ITEM = ITEMS.register("chrome", () -> new CBItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<EntityType<CBEntity>> ENTITY_TYPE = ENTITIES.register("chrome",
            () -> EntityType.Builder.<CBEntity>of(CBEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build("chrome"));

    public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab().equals(CreativeModeTabs.SEARCH)) {
            event.accept(CBRegistry.ITEM, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
