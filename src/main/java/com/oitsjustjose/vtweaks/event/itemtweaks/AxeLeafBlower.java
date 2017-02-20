package com.oitsjustjose.vtweaks.event.itemtweaks;

import com.oitsjustjose.vtweaks.VTweaks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AxeLeafBlower
{
	@SubscribeEvent
	public void registerTweak(BreakEvent event)
	{
		if (!VTweaks.config.enableAxeLeafBlower || event.getState() == null || event.getState().getBlock() == null || event.getPlayer() == null)
			return;

		EntityPlayer player = event.getPlayer();
		World world = event.getWorld();

		if (player.getHeldItemMainhand() == ItemStack.EMPTY)
			return;

		if (player.getHeldItemMainhand().getItem() instanceof ItemAxe && event.getState().getMaterial() == Material.LEAVES)
		{
			BlockPos tempPos;
			for (int x = -3; x < 4; x++)
			{
				for (int y = -3; y < 4; y++)
				{
					for (int z = -3; z < 4; z++)
					{
						tempPos = event.getPos().add(x, y, z);

						if (world.getBlockState(tempPos).getBlock().getClass() == event.getState().getBlock().getClass())
						{
							breakBlock(world, player, tempPos);
						}
					}
				}
			}
		}
	}

	void breakBlock(World world, EntityPlayer player, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		IBlockState state = world.getBlockState(pos);

		block.onBlockHarvested(world, pos, state, player);
		if (block.removedByPlayer(state, world, pos, player, true))
		{
			block.onBlockDestroyedByPlayer(world, pos, state);
			// Passed "Null" for held-item to avoid axe being damaged highly
			block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), ItemStack.EMPTY);
		}
		world.setBlockToAir(pos);
		world.playSound(null, pos, block.getSoundType(state, world, pos, player).getBreakSound(), SoundCategory.BLOCKS, 0.25F, 0.8F);
	}

}
