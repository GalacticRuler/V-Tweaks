package com.oitsjustjose.vtweaks.common.event.mobtweaks;

import com.oitsjustjose.vtweaks.common.config.MobTweakConfig;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PeacefulSurface {
    @SubscribeEvent
    public void registerTweak(LivingSpawnEvent event) {
        if (!MobTweakConfig.ENABLE_PEACEFUL_SURFACE.get()) {
            return;
        }
        if (event.getEntity() == null || !(event.getEntity() instanceof MonsterEntity)) {
            return;
        }
        for (String dimType : MobTweakConfig.PEACEFUL_SURFACE_BLACKLIST.get()) {
            if (event.getWorld().getDimension().getType().getRegistryName().toString().equalsIgnoreCase(dimType)) {
                return;
            }
        }
        // Check for midnight
        if (event.getWorld().getWorld().getDimension().getMoonPhase(event.getWorld().getWorld().getDayTime()) != 4) {
            // Check if position is high enough
            if (event.getEntity().getPosition().getY() >= MobTweakConfig.PEACEFUL_SURFACE_MIN_Y.get()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
