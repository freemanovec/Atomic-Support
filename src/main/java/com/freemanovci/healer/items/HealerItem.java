package com.freemanovci.healer.items;

import java.util.List;

import com.freemanovci.healer.common.Supplementary;

import mekanism.api.EnumColor;
import mekanism.api.energy.IEnergizedItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class HealerItem extends Item implements IEnergizedItem{
	public static void init(){
		System.out.println("Atomic Healer initialized!");
	}
	
	//private double internEnergy = 0;
	private double maxEnergy = 1500000;
	
	private double perHealthEnergy = 10000;
	
	//??? Noone knows what this does.. hm... maybe it's the max rate of recharging... Well, this is enough.
	private double maxTransfer = 50000;
	
	//private boolean internActive = true;
	//private ItemStack lastlyCalledItemStack;
	
	public double energy(ItemStack itemstack) {
		if(itemstack != null && itemstack.stackTagCompound != null){
			//System.out.println("Reading energy from NBT");
			return itemstack.stackTagCompound.getDouble("energy");
		}else{
			//System.out.println("Reading energy from NBT, but no NBT exists");
			return 0;
		}
	}
	public void setEnergy(double energy, ItemStack itemstack) {
		//this.internEnergy = energy;
		if(itemstack != null && itemstack.stackTagCompound != null){
			//System.out.println("Writing energy to NBT");
			itemstack.stackTagCompound.setDouble("energy", energy);
		}else{
			//System.out.println("Writing energy to NBT, but no NBT exists");
		}
	}
	public boolean active(ItemStack itemstack) {
		if(itemstack != null && itemstack.stackTagCompound != null){
			//System.out.println("Reading active from NBT");
			return itemstack.stackTagCompound.getBoolean("active");
		}else{
			//System.out.println("Reading active from NBT, but no NBT exists");
			return false;
		}
	}
	public void setActive(boolean active, ItemStack itemstack) {
		//this.internActive = active;
		if(itemstack != null && itemstack.stackTagCompound != null){
			//System.out.println("Writing active to NBT");
			itemstack.stackTagCompound.setBoolean("active", active);
		}else{
			//System.out.println("Writing active to NBT, but no NBT exists");
		}
	}
	/*public void writeCreated(ItemStack itemstack){
		itemstack.stackTagCompound.setBoolean("created", true);
	}
	public boolean created(){
		
	}*/
	private void toggleActive(ItemStack itemstack){
		setActive(!active(itemstack),itemstack);
	}
	private void justCreated(ItemStack itemstack){
		System.out.println("ITEM CREATED! YAAAY!");
		itemstack.stackTagCompound = new NBTTagCompound();
		itemstack.stackTagCompound.setDouble("energy", 0);
		itemstack.stackTagCompound.setBoolean("active", true);
	}
	/*@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer){
		//if(itemstack.stackTagCompound != null){
		System.out.println("ITEM CREATED! YAAAY!");
		lastlyCalledItemStack = itemstack;
		itemstack.stackTagCompound = new NBTTagCompound();
		itemstack.stackTagCompound.setDouble("energy", 0);
		itemstack.stackTagCompound.setBoolean("active", true);
		//}
	}*/
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag){
		super.addInformation(itemstack, entityplayer, list, flag);
		
		if(!GuiScreen.isShiftKeyDown()){
			EnumColor color;
			String message;
			if(active(itemstack)){
			color = EnumColor.BRIGHT_GREEN;
			message = "On";
			}
			else{
			color = EnumColor.RED;
			message = "Off";
			}
			list.add(EnumColor.AQUA + "Stored energy: " + EnumColor.GREY + Supplementary.humanReadableEnergy((long)energy(itemstack)));
			list.add("Mode: " + color + active(itemstack));
			list.add("Hold " + EnumColor.AQUA + "LSHIFT" + EnumColor.GREY + " for details");
		}else{
			list.add("Uses energy to locate all");
			list.add("kinds of wounds (not");
			list.add("emotional), thus healing");
			list.add("the player.");
		}
		
	}
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(!world.isRemote && player.isSneaking()){
			toggleActive(itemstack);
			String message = "Active: ";
			EnumColor color;
			if(active(itemstack))
				color = EnumColor.BRIGHT_GREEN;
			else
				color = EnumColor.RED;
			String messageActive;
			if(active(itemstack))
				messageActive = "Activated";
			else
				messageActive = "Deactivated";
			player.addChatMessage(new ChatComponentText(EnumColor.GREY + message + color + messageActive));
		}
		return itemstack;
	}
	@Override
	public boolean showDurabilityBar(ItemStack itemstack){
		//System.out.println("Requested showDurabilityBar, returning: " + true);
		return true;
	}
	@Override
    public double getDurabilityForDisplay(ItemStack itemstack)
    {
		//System.out.println("Requested getDurabilityForDisplay, returning: " + (1f-(float)energy/(float)maxEnergy));
        return 1f-(float)energy(itemstack)/(float)maxEnergy;
    }
	private boolean discharge(double amount,ItemStack itemstack){
		if(amount<=energy(itemstack)){
			//energy() -= amount;
			setEnergy(energy(itemstack)-amount,itemstack);
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void onUpdate(ItemStack itemstack, World par2, Entity par3, int par4, boolean par5){
		if(itemstack.stackTagCompound != null){
			if(!par2.isRemote && active(itemstack)){
				EntityPlayerMP player = (EntityPlayerMP) par3;
				//float health = Minecraft.getMinecraft().thePlayer.getHealth();
				//float maxHealth = Minecraft.getMinecraft().thePlayer.getMaxHealth();
				float health = player.getHealth();
				float maxHealth = player.getMaxHealth();
				
				if(health < maxHealth){
					//System.out.println("Low health");
					if(discharge(perHealthEnergy,itemstack)){
						//System.out.println("Discharged, healing");
						//Minecraft.getMinecraft().thePlayer.heal(1f);
						//Minecraft.getMinecraft().thePlayer.setHealth(health+1f);
						player.heal(1f);
					}
				}
			}
		}else{
			System.out.println("No NBT exist, calling justCreated!");
			justCreated(itemstack);
		}
	}

	@Override
	public double getEnergy(ItemStack itemstack) {
		//System.out.println("Requested getEnergy, returning: " + energy());
		return energy(itemstack);
	}

	@Override
	public void setEnergy(ItemStack itemstack, double amount) {
		//System.out.println("Requested setEnergy to amount: " + amount);
		if(amount<=maxEnergy){
			setEnergy(amount,itemstack);
		}else{
			setEnergy(maxEnergy,itemstack);
		}
	}

	@Override
	public double getMaxEnergy(ItemStack itemstack) {
		//System.out.println("Requested getMaxEnergy, returning: " + maxEnergy);
		return maxEnergy;
	}

	@Override
	public double getMaxTransfer(ItemStack itemstack) {
		//System.out.println("Requested getMaxTransfer, returning: " + maxTransfer);
		return maxTransfer;
	}

	@Override
	public boolean canReceive(ItemStack itemstack) {
		//System.out.println("Requested canReceive, returning: " + true);
		return true;
	}

	@Override
	public boolean canSend(ItemStack itemstack) {
		//System.out.println("Requested canSend, returning: " + true);
		return false;
	}

	@Override
	public boolean isMetadataSpecific(ItemStack itemstack) {
		//System.out.println("Requested isMetadataSpecific, returning: " + true);
		return true;
	}
}
