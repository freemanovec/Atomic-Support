package healer.common;

import healer.items.CraftComponentOnlyItem;
import healer.items.FeederItem;
import healer.items.HealerItem;
import healer.items.MultiItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.OreDictionary;
import mekanism.common.*;
import healer.common.*;

@Mod(modid = ResourcesDNM.modid, version = ResourcesDNM.version, canBeDeactivated = true, dependencies = "required-after:Mekanism")
public class Main
{
    public static final String MODID = "healer";
    public static final String VERSION = "1.0.1";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	HealerItem atomic_healer = (HealerItem) new HealerItem().setUnlocalizedName("AtomicHealer").setCreativeTab(CreativeTabs.tabCombat).setTextureName(MODID + ":" + "AtomicHealer").setMaxStackSize(1);
    	GameRegistry.registerItem(atomic_healer, "AtomicHealer");
    	FeederItem atomic_feeder = (FeederItem) new FeederItem().setUnlocalizedName("AtomicFeeder").setCreativeTab(CreativeTabs.tabFood).setTextureName(MODID + ":" + "AtomicFeeder").setMaxStackSize(1);
    	GameRegistry.registerItem(atomic_feeder, "AtomicFeeder");
    	MultiItem atomic_supporter = (MultiItem) new MultiItem().setUnlocalizedName("AtomicSupporter").setCreativeTab(CreativeTabs.tabMisc).setTextureName(MODID + ":" + "AtomicSupporter").setMaxStackSize(1);
    	GameRegistry.registerItem(atomic_supporter, "AtomicSupporter");
    	
    	Item conductorHealth = new CraftComponentOnlyItem().setUnlocalizedName("ConductorHealth").setCreativeTab(CreativeTabs.tabRedstone).setTextureName(MODID + ":" + "ConductorHealth");
    	Item conductorFood = new CraftComponentOnlyItem().setUnlocalizedName("ConductorFood").setCreativeTab(CreativeTabs.tabRedstone).setTextureName(MODID + ":" + "ConductorFood");
    	GameRegistry.registerItem(conductorHealth, "conductorHealth");
    	GameRegistry.registerItem(conductorFood, "conductorFood");
    	
    	String craftComponent_atomicAlloy = "alloyUltimate";
    	String craftComponent_energyTablet = "battery";
    	String craftComponent_teleportationCore = "TeleportationCore";
    	String craftComponent_ultimateCircuit = "circuitUltimate";
    	
    	ItemStack craftComponent_atomicAlloy_item = (ItemStack) OreDictionary.getOres(craftComponent_atomicAlloy).toArray()[0];
    	ItemStack craftComponent_energyTablet_item = (ItemStack) OreDictionary.getOres(craftComponent_energyTablet).toArray()[0];
    	ItemStack craftComponent_teleportationCore_item = new ItemStack(GameRegistry.findItem("Mekanism", craftComponent_teleportationCore));
    	ItemStack craftComponent_ultimateCircuit_item = (ItemStack) OreDictionary.getOres(craftComponent_ultimateCircuit).toArray()[0];
    	ItemStack craftComponent_conductorFood_item = new ItemStack(conductorFood);
    	ItemStack craftComponent_conductorHealth_item = new ItemStack(conductorHealth);
    	ItemStack craftComponent_atomicFeeder_item = new ItemStack(atomic_feeder);
    	ItemStack craftComponent_atomicHealer_item = new ItemStack(atomic_healer);
    	
    	GameRegistry.addShapelessRecipe(new ItemStack(conductorHealth), 
    		Items.golden_apple, craftComponent_atomicAlloy_item
    	);
    	GameRegistry.addShapelessRecipe(new ItemStack(conductorFood),
    		Items.cooked_chicken, Items.cooked_beef, Items.bread, Items.cake, craftComponent_atomicAlloy_item
    	);
    	GameRegistry.addRecipe(new ItemStack((Item)atomic_feeder),
    		" A ",
    		"BCB",
    		" D ",
    		'A', craftComponent_ultimateCircuit_item,
    		'B', craftComponent_energyTablet_item,
    		'C', craftComponent_conductorFood_item,
    		'D', craftComponent_teleportationCore_item
    	);
    	GameRegistry.addRecipe(new ItemStack((Item)atomic_healer),
    		" A ",
    		"BCB",
    		" D ",
    		'A', craftComponent_ultimateCircuit_item,
    		'B', craftComponent_energyTablet_item,
    		'C', craftComponent_conductorHealth_item,
    		'D', craftComponent_teleportationCore_item
    	);
    	GameRegistry.addRecipe(new ItemStack((Item)atomic_supporter),
    		"AAA",
    		"BAC",
    		"AAA",
    		'A', craftComponent_energyTablet_item,
    		'B', craftComponent_atomicHealer_item,
    		'C', craftComponent_atomicFeeder_item
    	);
    	
    }
}
