package com.freemanovci.healer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import mekanism.common.*;

@Mod(modid = Main.MODID, version = Main.VERSION, canBeDeactivated = true)
public class Main
{
    public static final String MODID = "healer";
    public static final String VERSION = "0.1";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	 
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	HealerItem atomic_healer = (HealerItem) new HealerItem().setUnlocalizedName("atomic_healer").setCreativeTab(CreativeTabs.tabCombat);
    	atomic_healer.init();
    	GameRegistry.registerItem(atomic_healer, "atomic_healer");
    	
    	Block testBlock = new BlockTest(Material.ground);
		GameRegistry.registerBlock(testBlock, "mekanismAPITest");
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	 
    }
}
