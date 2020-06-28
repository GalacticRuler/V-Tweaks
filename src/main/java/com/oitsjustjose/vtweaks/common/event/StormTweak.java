package com.oitsjustjose.vtweaks.common.event;

import com.oitsjustjose.vtweaks.common.config.CommonConfig;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StormTweak {
    @SubscribeEvent
    public void registerTweak(WorldEvent event) {
        // Check if feature is enabled
        if (!CommonConfig.DISABLE_THUNDER_STORMS.get() || event.getWorld() == null) {
            return;
        }
        // Converts storms to regular rain
        if (event.getWorld().getWorld() != null) {
            if (event.getWorld().getWorld().isRaining()) {
                if (event.getWorld().getWorldInfo().isThundering()) {
                    event.getWorld().getWorldInfo().setThundering(false);
                }
            }
        }
    }
}
