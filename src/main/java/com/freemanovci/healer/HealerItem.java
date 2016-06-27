package com.freemanovci.healer;

import mekanism.api.energy.IEnergizedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HealerItem extends Item implements IEnergizedItem{
	public static void init(){
		System.out.println("Atomic Healer initialized!");
	}
	
	private double energy = 0;
	private double maxEnergy = 15000;
	
	private double perHealthEnergy = 100;
	
	//???
	private double maxTransfer = 5000;
	
	@Override
	public boolean showDurabilityBar(ItemStack itemStack){
		//System.out.println("Requested showDurabilityBar, returning: " + true);
		return true;
	}
	@Override
    public double getDurabilityForDisplay(ItemStack itemStack)
    {
		//System.out.println("Requested getDurabilityForDisplay, returning: " + (1f-(float)energy/(float)maxEnergy));
        return 1f-(float)energy/(float)maxEnergy;
    }
	private boolean discharge(double amount){
		if(amount<=energy){
			energy -= amount;
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void onUpdate(ItemStack par1, World par2, Entity par3, int par4, boolean par5){
		if(!par2.isRemote){
			EntityPlayerMP player = (EntityPlayerMP) par3;
			//float health = Minecraft.getMinecraft().thePlayer.getHealth();
			//float maxHealth = Minecraft.getMinecraft().thePlayer.getMaxHealth();
			float health = player.getHealth();
			float maxHealth = player.getMaxHealth();
			
			if(health < maxHealth){
				System.out.println("Low health");
				if(discharge(perHealthEnergy)){
					System.out.println("Discharged, healing");
					//Minecraft.getMinecraft().thePlayer.heal(1f);
					//Minecraft.getMinecraft().thePlayer.setHealth(health+1f);
					player.heal(1f);
				}
			}
		}
	}

	@Override
	public double getEnergy(ItemStack itemStack) {
		System.out.println("Requested getEnergy, returning: " + energy);
		return energy;
	}

	@Override
	public void setEnergy(ItemStack itemStack, double amount) {
		//System.out.println("Requested setEnergy to amount: " + amount);
		if(amount<=maxEnergy){
			energy = amount;
		}else{
			energy = maxEnergy;
		}
	}

	@Override
	public double getMaxEnergy(ItemStack itemStack) {
		//System.out.println("Requested getMaxEnergy, returning: " + maxEnergy);
		return maxEnergy;
	}

	@Override
	public double getMaxTransfer(ItemStack itemStack) {
		//System.out.println("Requested getMaxTransfer, returning: " + maxTransfer);
		return maxTransfer;
	}

	@Override
	public boolean canReceive(ItemStack itemStack) {
		//System.out.println("Requested canReceive, returning: " + true);
		return true;
	}

	@Override
	public boolean canSend(ItemStack itemStack) {
		//System.out.println("Requested canSend, returning: " + true);
		return true;
	}

	@Override
	public boolean isMetadataSpecific(ItemStack arg0) {
		//System.out.println("Requested isMetadataSpecific, returning: " + true);
		return true;
	}
}
