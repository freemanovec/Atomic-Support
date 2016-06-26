package com.freemanovci.healer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@Mod(modid = Main.MODID, version = Main.VERSION)
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
    	Block testBlock = new BlockTest(Material.ground);
		GameRegistry.registerBlock(testBlock, "mekanismAPITest");
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	 
    }
}
