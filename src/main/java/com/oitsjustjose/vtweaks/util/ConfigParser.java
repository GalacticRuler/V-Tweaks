package com.oitsjustjose.vtweaks.util;

import java.util.ArrayList;

import com.oitsjustjose.vtweaks.VTweaks;

import net.minecraft.item.ItemStack;

public class ConfigParser
{

    public static void parseItems()
    {
        VTweaks.LOGGER.info(">> Running Config Item Parser");
        ModConfig.setChallengerLootTable(parse(ModConfig.mobTweaks.challengerMobs.loot, true));
        VTweaks.LOGGER.info(">> Config Item Parsing complete!");
    }

    /**
     * @param input       A string array gathered from a config file
     * @param includesQty true if strings in the array include quantities of the itemstack
     * @return an ArrayList containing parsed ItemStacks of these
     */
    private static ArrayList<ItemStack> parse(String[] input, boolean includesQty)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        // Goes through every string in the inputs list
        for (String toSplit : input)
        {
            String[] parts = toSplit.split("[\\W]");
            ItemStack tempStack = HelperFunctions.findItemStack(parts[0], parts[1]);
            if (!tempStack.isEmpty())
            {
                try
                {
                    if (parts.length == 2)
                    {
                        ret.add(tempStack.copy());
                    }
                    else if (parts.length == 3)
                    {
                        // This lets users either use:
                        // <modid>:<item>:<meta> OR
                        // <modid>:<item>*<qty>
                        if (toSplit.contains("*"))
                        {
                            tempStack = new ItemStack(tempStack.getItem(), Integer.parseInt(parts[2]));
                            ret.add(tempStack.copy());
                        }
                        else
                        {
                            tempStack = new ItemStack(tempStack.getItem(), 1, Integer.parseInt(parts[2]));
                            ret.add(tempStack.copy());
                        }
                    }
                    else if (parts.length == 4 && includesQty)
                    {
                        tempStack = new ItemStack(tempStack.getItem(), Integer.parseInt(parts[3]),
                                Integer.parseInt(parts[2]));
                        ret.add(tempStack.copy());
                    }
                    else
                    {
                        LOGGER.info("Error parsing " + toSplit + ", please ensure proper formatting!");
                    }
                }
                catch (NumberFormatException e)
                {
                    LOGGER.info("Error parsing " + toSplit + ", please ensure proper formatting!");
                }
            }
        }
        return ret;
    }

}
