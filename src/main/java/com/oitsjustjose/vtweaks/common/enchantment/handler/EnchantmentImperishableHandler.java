package com.oitsjustjose.vtweaks.common.enchantment.handler;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.oitsjustjose.vtweaks.VTweaks;
import com.oitsjustjose.vtweaks.common.config.EnchantmentConfig;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class EnchantmentImperishableHandler {
    // This event is for tools
    @SubscribeEvent
    public void register(BreakSpeed event) {
        if (!EnchantmentConfig.ENABLE_IMPERISHABLE.get()) {
            return;
        }

        if (event.getEntity() == null || !(event.getEntity() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) event.getEntity();
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        if (stack.isEmpty()) {
            return;
        }
        if (stack.getItem() instanceof ToolItem) {
            ToolItem tool = (ToolItem) stack.getItem();

            if (EnchantmentHelper.getEnchantmentLevel(VTweaks.imperishable, stack) > 0) {
                if (tool.getDamage(stack) >= (tool.getMaxDamage(stack) - 1)) {
                    event.getPlayer().playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.25F,
                            (float) Math.min(1.0F, 0.5F + event.getPlayer().getRNG().nextDouble()));
                    event.setNewSpeed(0F);
                }
            }
        } else if (stack.getItem() instanceof SwordItem) {
            SwordItem sword = (SwordItem) stack.getItem();

            if (EnchantmentHelper.getEnchantmentLevel(VTweaks.imperishable, stack) > 0) {
                if (sword.getDamage(stack) >= (sword.getMaxDamage(stack) - 1)) {
                    event.getPlayer().playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.25F,
                            (float) Math.min(1.0F, 0.5F + event.getPlayer().getRNG().nextDouble()));
                    event.setNewSpeed(0F);
                }
            }
        }
    }

    @SubscribeEvent
    public void register(AttackEntityEvent event) {
        if (!EnchantmentConfig.ENABLE_IMPERISHABLE.get()) {
            return;
        }
        if (event.getPlayer() == null || event.getPlayer().isCreative()) {
            return;
        }

        ItemStack stack = event.getPlayer().getHeldItemMainhand();

        if (stack.isEmpty() || !(stack.getItem() instanceof SwordItem)
                || EnchantmentHelper.getEnchantmentLevel(VTweaks.imperishable, stack) <= 0) {
            return;
        }

        SwordItem sword = (SwordItem) stack.getItem();

        if (EnchantmentHelper.getEnchantmentLevel(VTweaks.imperishable, stack) > 0) {
            if (sword.getDamage(stack) >= (sword.getMaxDamage(stack) - 1)) {
                if (event.isCancelable()) {
                    event.getPlayer().playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F,
                            (float) Math.min(1.0F, 0.5F + event.getPlayer().getRNG().nextDouble()));
                    event.getPlayer().sendStatusMessage(new TranslationTextComponent("vtweaks.sword.damaged"), true);
                    event.setCanceled(true);
                }
            }
        }
    }

    // This event is for attacking / damage
    @SubscribeEvent
    public void register(PlayerEvent event) {
        if (!EnchantmentConfig.ENABLE_IMPERISHABLE.get()) {
            return;
        }

        try {
            ArrayList<ItemStack> salvaged = Lists.newArrayList();
            for (ItemStack stack : event.getPlayer().getArmorInventoryList()) {
                if (stack.getItem() instanceof ArmorItem) {
                    ArmorItem armor = (ArmorItem) stack.getItem();
                    if (EnchantmentHelper.getEnchantmentLevel(VTweaks.imperishable, stack) > 0) {
                        if (armor.getDamage(stack) >= (armor.getMaxDamage(stack) - 1)) {
                            salvaged.add(stack);
                        }
                    }
                }
            }

            salvaged.forEach((stack) -> {
                ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), stack.copy());
                event.getPlayer().setItemStackToSlot(MobEntity.getSlotForItemStack(stack), ItemStack.EMPTY);
                event.getPlayer().inventory.markDirty();
                VTweaks.proxy.playSound(event.getPlayer());
            });
        } catch (NullPointerException ignored) {
            return;
        }
    }
}