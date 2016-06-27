package com.freemanovci.healer;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HealerItem extends Item{
	public static void init(){
		System.out.println("Atomic Healer initialized!");
	}
	
	private double energy = 0;
	private double maxEnergy = 15000;
	
	@Override
	public void onUpdate(ItemStack par1, World par2, Entity par3, int par4, boolean par5){
		if(!par2.isRemote){
			System.out.println("Client ticked Atomic Healer!");
		}
	}
}
