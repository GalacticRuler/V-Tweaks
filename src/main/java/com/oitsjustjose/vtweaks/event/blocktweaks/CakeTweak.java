package com.oitsjustjose.vtweaks.event.blocktweaks;

import com.oitsjustjose.vtweaks.util.CakeRegistry;
import com.oitsjustjose.vtweaks.util.ModConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CakeTweak
{
    @SubscribeEvent
    public void registerTweak(BlockEvent.HarvestDropsEvent event)
    {
        // Checks if feature is enabled
        if (!ModConfig.blockTweaks.enableCakeDrop)
        {
            return;
        }

        Block block = event.getState().getBlock();
        if (event.getHarvester() != null && block instanceof BlockCake && block.getMetaFromState(event.getState()) == 0)
        {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(CakeRegistry.getItemFromCakeBlock(block)));
        }
    }
}