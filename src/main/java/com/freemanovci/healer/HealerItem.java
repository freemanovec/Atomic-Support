package com.freemanovci.healer;

import java.util.List;

import mekanism.api.EnumColor;
import mekanism.api.energy.IEnergizedItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class HealerItem extends Item implements IEnergizedItem{
	public static void init(){
		System.out.println("Atomic Healer initialized!");
	}
	
	private double energy = 0;
	private double maxEnergy = 1500000;
	
	private double perHealthEnergy = 10000;
	
	//??? Noone knows what this does.. hm... maybe it's the max rate of recharging... Well, this is enough.
	private double maxTransfer = 50000;
	
	private boolean active = true;
	
	private void toggleActive(){
		active = !active;
	}
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag){
		super.addInformation(itemstack, entityplayer, list, flag);
		
		if(!GuiScreen.isShiftKeyDown()){
			EnumColor color;
			String message;
			if(active){
			color = EnumColor.BRIGHT_GREEN;
			message = "On";
			}
			else{
			color = EnumColor.RED;
			message = "Off";
			}
			list.add(EnumColor.AQUA + "Stored energy: " + EnumColor.GREY + Supplementary.humanReadableEnergy((long)energy));
			list.add("Mode: " + color + active);
			list.add("Hold " + EnumColor.AQUA + "LSHIFT" + EnumColor.GREY + " for details");
		}else{
			list.add("Uses energy to locate");
			list.add("all kinds of wounds");
			list.add("(excluding emotional),");
			list.add("thus healing the player.");
		}
		
	}
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(!world.isRemote && player.isSneaking()){
			toggleActive();
			String message = "Active: ";
			EnumColor color;
			if(active)
				color = EnumColor.BRIGHT_GREEN;
			else
				color = EnumColor.RED;
			String messageActive;
			if(active)
				messageActive = "Activated";
			else
				messageActive = "Deactivated";
			player.addChatMessage(new ChatComponentText(EnumColor.GREY + message + color + messageActive));
		}
		return itemstack;
	}
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
		if(!par2.isRemote && active){
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
		return false;
	}

	@Override
	public boolean isMetadataSpecific(ItemStack arg0) {
		//System.out.println("Requested isMetadataSpecific, returning: " + true);
		return true;
	}
}
