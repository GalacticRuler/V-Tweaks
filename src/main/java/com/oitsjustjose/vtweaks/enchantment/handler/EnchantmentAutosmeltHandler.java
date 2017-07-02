package com.oitsjustjose.vtweaks.enchantment.handler;

import java.util.ListIterator;

import com.oitsjustjose.vtweaks.VTweaks;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAutosmeltHandler
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void register(HarvestDropsEvent event)
	{
		// Check if enchantment is disabled
		if (VTweaks.config.autosmeltID <= 0)
			return;
		// Check if the player is null
		if (event.getHarvester() == null || event.getHarvester().getHeldItemMainhand() == null)
			return;

		// Some local variables
		EntityPlayer player = event.getHarvester();
		World world = event.getWorld();
		ItemStack heldItem = player.getHeldItemMainhand();
		int autosmeltLevel = EnchantmentHelper.getEnchantmentLevel(VTweaks.enchantments.autosmelt, heldItem);

		if (autosmeltLevel > 0)
		{
			ListIterator<ItemStack> iter = event.getDrops().listIterator();
			// Goes through every drop in the drpo list
			while (iter.hasNext())
			{
				ItemStack temp = iter.next().copy();
				ItemStack newDrop = FurnaceRecipes.instance().getSmeltingResult(temp.copy());
				// Check if current item in the iterator has a smelting result
				if (newDrop != null)
				{
					// Compensates for stack sizes depending on normal drop qty & fortune
					newDrop = newDrop.copy();
					newDrop.stackSize = temp.stackSize;
					if (event.getFortuneLevel() > 0 && shouldFortuneSmelt(temp))
						newDrop.stackSize *= world.rand.nextInt(event.getFortuneLevel() + 1) + 1;
					// Replaces the drop
					iter.set(newDrop);
					// Spawns XP from smelting the item in a furnace
					spawnXP(event.getWorld(), event.getPos(), newDrop.copy());
				}
			}
		}

	}

	private void spawnXP(World world, BlockPos pos, ItemStack itemstack)
	{
		// Extract x, y and z for spawning
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int stackSize = itemstack.stackSize;
		float smeltingXP = FurnaceRecipes.instance().getSmeltingExperience(itemstack);
		int xpSplit;
		// Prevents XP spawning if none should spawn
		if (smeltingXP == 0.0F)
			stackSize = 0;
		// Divies up the XP if it's too much (preventing lag)
		else if (smeltingXP < 1.0F)
		{
			xpSplit = MathHelper.floor_float((float) stackSize * smeltingXP);

			if (xpSplit < MathHelper.ceiling_float_int((float) stackSize * smeltingXP) && (float) Math.random() < (float) stackSize * smeltingXP - (float) xpSplit)
				++xpSplit;

			stackSize = xpSplit;
		}
		// Spawns the proper XP amounts
		while (stackSize > 0)
		{
			xpSplit = EntityXPOrb.getXPSplit(stackSize);
			stackSize -= xpSplit;
			world.spawnEntityInWorld(new EntityXPOrb(world, x, y + 0.5, z, xpSplit));
		}
	}

	private boolean shouldFortuneSmelt(ItemStack stack)
	{
		// Checks if feature is disabled
		if (!VTweaks.config.enableAutosmeltFortuneInteraction)
			return false;
		// Checks if the item dropped is a block
		if (stack.getItem() instanceof ItemBlock)
		{
			Block block = Block.getBlockFromItem(stack.getItem());
			// Easy check: does the block name contain ore? Typically modders do this
			if (block.getRegistryName().toString().toLowerCase().contains("ore"))
				return true;
			// BUT if they don't, there's a config to override:
			for (String s : VTweaks.config.autosmeltOverrides)
				if (block.getRegistryName().toString().toLowerCase().contains(s))
					return true;
		}
		return false;
	}
}