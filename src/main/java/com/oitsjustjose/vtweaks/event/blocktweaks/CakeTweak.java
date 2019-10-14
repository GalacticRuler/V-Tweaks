package com.oitsjustjose.vtweaks.event.blocktweaks;

import com.oitsjustjose.vtweaks.config.BlockTweakConfig;

import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CakeTweak
{
    @SubscribeEvent
    public void registerTweak(BlockEvent.HarvestDropsEvent event)
    {
        // Checks if feature is enabled
        if (!BlockTweakConfig.ENABLE_CAKE_DROP.get())
        {
            return;
        }

        Block block = event.getState().getBlock();
        if (event.getHarvester() != null && block instanceof CakeBlock)
        {
            if (event.getState().has(CakeBlock.BITES))
            {
                int bites = event.getState().get(CakeBlock.BITES);
                if (bites == 0)
                {
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Items.CAKE));
                }
            }
        }
    }
}