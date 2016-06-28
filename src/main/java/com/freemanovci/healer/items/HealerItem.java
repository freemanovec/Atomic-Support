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

//Healer item is an Item, so it extends Item
//Uses Mekanism energy, implements API's interface
public class HealerItem extends Item implements IEnergizedItem{
	//Max energy this item can store
	private double maxEnergy = 1500000;
	//Ammount of energy used to heal 1 HP (Half a heart)
	private double perHealthEnergy = 10000;
	//Pretty much sure that it's a rate at which the item charges
	//[OLD]No-one knows what this does.. hm... maybe it's the max rate of recharging... Well, this is enough.
	private double maxTransfer = 50000;
	//Attempts to read NBT and returns ammount of energy stored in this item
	//Needs ItemStack so it can get the NBT to read from
	public double energy(ItemStack itemstack) {
		if(itemstack != null && itemstack.stackTagCompound != null){
			return itemstack.stackTagCompound.getDouble("energy");
		}else{
			return 0;
		}
	}
	//Attempts to write energy to NBT
	//needs itemstack
	public void setEnergy(double energy, ItemStack itemstack) {
		if(itemstack != null && itemstack.stackTagCompound != null){
			itemstack.stackTagCompound.setDouble("energy", energy);
		}else{
		}
	}
	//reads nbt
	//needs itemstack
	public boolean active(ItemStack itemstack) {
		if(itemstack != null && itemstack.stackTagCompound != null){
			return itemstack.stackTagCompound.getBoolean("active");
		}else{
			return false;
		}
	}
	//writes to nbt
	//needs nbt
	public void setActive(boolean active, ItemStack itemstack) {
		if(itemstack != null && itemstack.stackTagCompound != null){
			itemstack.stackTagCompound.setBoolean("active", active);
		}
	}
	//[DEPENDANT]flips the state of the item, preventing it from using energy and healing player
	private void toggleActive(ItemStack itemstack){
		setActive(!active(itemstack),itemstack);
	}
	//[DEPENDANT]called when no nbt is detected
	//creates one
	private void justCreated(ItemStack itemstack){
		System.out.println("ITEM CREATED! YAAAY!");
		itemstack.stackTagCompound = new NBTTagCompound();
		itemstack.stackTagCompound.setDouble("energy", 0);
		itemstack.stackTagCompound.setBoolean("active", true);
	}
	//writes tha info that is shown when mouse hovers over the item, even in the NEI
	//called every frame (not tick)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag){
		super.addInformation(itemstack, entityplayer, list, flag); //still dunno what super does
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
		}else{ //check Left SHIFT for additional data
			list.add("Uses energy to locate all");
			list.add("kinds of wounds (not");
			list.add("emotional), thus healing");
			list.add("the player.");
		}
	}
	//called on right click (obviously)
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(!world.isRemote && player.isSneaking()){ //checks if the world is server and if the player is crouching
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
	//tells MC to show durability bar
	@Override
	public boolean showDurabilityBar(ItemStack itemstack){
		return true;
	}
	//returns MC the value for that durability bar, shows energy level
	//0 is fully green
	//1 is depleted
	//thats why the reversal
	@Override
    public double getDurabilityForDisplay(ItemStack itemstack)
    {//         \|/ -- REVERSAL
        return 1f-(float)energy(itemstack)/(float)maxEnergy;
    }
	//handles discharging
	//checks energy level
	private boolean discharge(double amount,ItemStack itemstack){
		if(amount<=energy(itemstack)){
			setEnergy(energy(itemstack)-amount,itemstack);
			return true;
		}else{
			return false;
		}
	}
	//called every tick (not frame)
	@Override
	public void onUpdate(ItemStack itemstack, World par2, Entity par3, int par4, boolean par5){
		if(itemstack.stackTagCompound != null){
			if(!par2.isRemote && active(itemstack)){
				//gets health and max health
				EntityPlayerMP player = (EntityPlayerMP) par3;
				float health = player.getHealth();
				float maxHealth = player.getMaxHealth();
				//compares and heals (SERVERSIDE)
				if(health < maxHealth){
					if(discharge(perHealthEnergy,itemstack)){
						player.heal(1f);
					}
				}
			}
		}else{
			//first run, no NBT detected
			justCreated(itemstack);
		}
	}
	//[DEPENDANT]mekanism API's method, return energy from NBT
	@Override
	public double getEnergy(ItemStack itemstack) {
		return energy(itemstack);
	}
	//[DEPENDANT]mekanism API's method, writes energy to NBT
	@Override
	public void setEnergy(ItemStack itemstack, double amount) {
		if(amount<=maxEnergy){
			setEnergy(amount,itemstack);
		}else{
			setEnergy(maxEnergy,itemstack);
		}
	}
	//self explanatory
	@Override
	public double getMaxEnergy(ItemStack itemstack) {
		return maxEnergy;
	}
	//Alice in the Wonderland
	@Override
	public double getMaxTransfer(ItemStack itemstack) {
		return maxTransfer;
	}
	//can receive energy? Yes, obviously. Just return true to tell MC so.
	@Override
	public boolean canReceive(ItemStack itemstack) {
		return true;
	}
	//can send energy? No, it's not a battery. Return false
	@Override
	public boolean canSend(ItemStack itemstack) {
		return false;
	}
	//no idea.. Propably Alice in Wonderland 2 || Funny joke: I've been comitting under non-existant email for the whole time.. My commits did not count on my accound :C
	@Override
	public boolean isMetadataSpecific(ItemStack itemstack) {
		return true;
	}
}
