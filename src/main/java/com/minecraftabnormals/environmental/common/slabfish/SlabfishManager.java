package com.minecraftabnormals.environmental.common.slabfish;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.LogicalSide;

import java.util.Random;
import java.util.function.Predicate;

import com.minecraftabnormals.environmental.common.entity.util.SlabfishRarity;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.minecraftabnormals.environmental.core.Environmental;

/**
 * <p>Manages all slabfish for both sides.</p>
 *
 * @author Ocelot
 */
public interface SlabfishManager {
    /**
     * The default slabfish that exists if there are no other slabfish types to choose from.
     */
    SlabfishType DEFAULT_SLABFISH = new SlabfishType(SlabfishRarity.COMMON, new TranslationTextComponent("entity." + Environmental.MODID + ".slabfish.type.swamp"), null, false, true, true, -1, new SlabfishCondition[0]).setRegistryName(new ResourceLocation(Environmental.MODID, "swamp"));

    /**
     * The default sweater that exists if there are no other sweater types to choose from.
     */
    SweaterType EMPTY_SWEATER = new SweaterType(null, Ingredient.EMPTY).setRegistryName(new ResourceLocation(Environmental.MODID, "empty"));

    /**
     * The default backpack that exists if there are no other backpack types to choose from.
     */
    BackpackType BROWN_BACKPACK = new BackpackType(null, Ingredient.EMPTY).setRegistryName(new ResourceLocation(Environmental.MODID, "brown"));

    /**
     * The id for the ghost slabfish. Used for custom functionality.
     */
    ResourceLocation GHOST = new ResourceLocation(Environmental.MODID, "ghost");

    /**
     * Fetches the slabfish manager for the specified side.
     *
     * @param side The logical side to get the slabfish manager for
     * @return The slabfish manager for that side
     */
    static SlabfishManager get(LogicalSide side) {
        return side.isClient() ? ClientSlabfishManager.INSTANCE : SlabfishLoader.instance;
    }

    /**
     * Same as {@link #get(LogicalSide)} but provides ease of access by using a world instead of {@link LogicalSide}.
     *
     * @param world The world to get the logical side from
     * @return The slabfish manager for that side
     */
    static SlabfishManager get(IWorldReader world) {
        return world.isRemote() ? ClientSlabfishManager.INSTANCE : SlabfishLoader.instance;
    }

    /**
     * Checks the slabfish types for a slabfish of the specified name.
     *
     * @param registryName The name of the slabfish to search for
     * @return The slabfish type by that name or {@link #DEFAULT_SLABFISH} for no slabfish under that name
     */
    SlabfishType getSlabfishType(ResourceLocation registryName);

    /**
     * Checks the sweater types for a sweater of the specified name.
     *
     * @param registryName The name of the sweater to search for
     * @return The sweater type by that name or {@link #EMPTY_SWEATER} for no sweater under that name
     */
    SweaterType getSweaterType(ResourceLocation registryName);

    /**
     * Checks the backpack types for a backpack of the specified name.
     *
     * @param registryName The name of the backpack to search for
     * @return The sweater type by that name or {@link #BROWN_BACKPACK} for no sweater under that name
     */
    BackpackType getBackpackType(ResourceLocation registryName);

    /**
     * Checks through all slabfish types for a slabfish conditions that succeed in the current context.
     *
     * @param context The context of the slabfish
     * @return The slabfish that that was selected to be the best fit for the context
     */
    default SlabfishType getSlabfishType(SlabfishConditionContext context) {
        return this.getSlabfishType(__ -> true, context);
    }

    /**
     * Checks through all slabfish types for a slabfish conditions that succeed in the current context.
     *
     * @param predicate The predicate to determine what kinds of slabfish to allow
     * @param context   The context of the slabfish
     * @return The slabfish that that was selected to be the best fit for the context
     */
    SlabfishType getSlabfishType(Predicate<SlabfishType> predicate, SlabfishConditionContext context);

    /**
     * Checks the sweater types for a sweater using the specified stack.
     *
     * @param stack The stack to test against the sweater types
     * @return The sweater type using that stack or {@link #EMPTY_SWEATER} if that item has no sweater type
     */
    SweaterType getSweaterType(ItemStack stack);

    /**
     * Checks the backpack types for a backpack using the specified stack.
     *
     * @param stack The stack to test against the backpack types
     * @return The backpack type using that stack or {@link #BROWN_BACKPACK} if that item has no backpack type
     */
    BackpackType getBackpackType(ItemStack stack);

    /**
     * Fetches a random slabfish type by the specified {@link Predicate}.
     *
     * @param predicate The predicate to use when searching for a slabfish type
     * @param random    The random to use for the index
     * @return A random slabfish type by that rarity or {@link #DEFAULT_SLABFISH} if there were no results
     */
    SlabfishType getRandomSlabfishType(Predicate<SlabfishType> predicate, Random random);

    /**
     * Checks the sweater types for a sweater under the specified registry name.
     *
     * @param registryName The stack to test against the sweater types
     * @return Whether or not there is a sweater for the specified stack
     */
    boolean hasSweaterType(ResourceLocation registryName);

    /**
     * Checks the backpack types for a backpack under the specified registry name.
     *
     * @param registryName The registry name of the backpack type
     * @return Whether or not there is a backpack for the specified registry name
     */
    boolean hasBackpackType(ResourceLocation registryName);

    /**
     * Checks the sweater types for a sweater using the specified stack.
     *
     * @param stack The registry name of the sweater types
     * @return Whether or not there is a sweater for the specified registry name
     */
    boolean hasSweaterType(ItemStack stack);

    /**
     * Checks the backpack types for a backpack using the specified stack.
     *
     * @param stack The stack to test against the backpack types
     * @return Whether or not there is a backpack for the specified stack
     */
    boolean hasBackpackType(ItemStack stack);

    /**
     * @return All registered slabfish types
     */
    SlabfishType[] getAllSlabfishTypes();

    /**
     * @return All registered sweater types
     */
    SweaterType[] getAllSweaterTypes();

    /**
     * @return All registered backpack types
     */
    BackpackType[] getAllBackpackTypes();
}
