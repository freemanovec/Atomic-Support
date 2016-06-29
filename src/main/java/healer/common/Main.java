package healer.common;

import healer.items.FeederItem;
import healer.items.HealerItem;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import mekanism.common.*;

@Mod(modid = Main.MODID, version = Main.VERSION, canBeDeactivated = true, dependencies = "required-after:Mekanism")
public class Main
{
    public static final String MODID = "healer";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	HealerItem atomic_healer = (HealerItem) new HealerItem().setUnlocalizedName("AtomicHealer").setCreativeTab(CreativeTabs.tabCombat).setTextureName(MODID + ":" + "AtomicHealer");
    	GameRegistry.registerItem(atomic_healer, "AtomicHealer");
    	FeederItem atomic_feeder = (FeederItem) new FeederItem().setUnlocalizedName("AtomicFeeder").setCreativeTab(CreativeTabs.tabFood).setTextureName(MODID + ":" + "AtomicFeeder");
    	GameRegistry.registerItem(atomic_feeder, "AtomicFeeder");
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	 
    }
}
