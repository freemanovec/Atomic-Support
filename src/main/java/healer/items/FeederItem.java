package healer.items;

import java.util.List;

import healer.common.ItemsShared;
import healer.common.Supplementary;

import mekanism.api.EnumColor;
import mekanism.api.energy.IEnergizedItem;
import mekanism.common.util.LangUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

//Feeder item is an Item, so it extends Item
//Uses Mekanism energy, implements API's interface
public class FeederItem extends Item implements IEnergizedItem{
	//Max energy this item can store
	private double maxEnergy = 1500000;
	//Ammount of energy used to feed 1 CW (Half of it)
	private double perWingEnergy = 2500;
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
	//[DEPENDANT]flips the state of the item, preventing it from using energy and feeding player
	private void toggleActive(ItemStack itemstack){
		setActive(!active(itemstack),itemstack);
	}
	//[DEPENDANT]called when no nbt is detected
	//creates one
	/*private void justCreated(ItemStack itemstack){
		System.out.println("ITEM CREATED! YAAAY!");
		itemstack.stackTagCompound = new NBTTagCompound();
		itemstack.stackTagCompound.setDouble("energy", 0);
		itemstack.stackTagCompound.setBoolean("active", true);
	}*/
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
			list.add("Mode: " + color + message);
			list.add("Hold " + EnumColor.AQUA + "LSHIFT" + EnumColor.GREY + " for details");
		}else{ //check Left SHIFT for additional data
			String longy = LangUtils.localize("tooltip.FeederItem.text");
			//String longy = "Uses energy to form essential nutreons in the stomach (or the equivalent), thus feeding the player.";
			for(String line: Supplementary.breakIntoLines(longy, 30)){
				list.add(line);
			}
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
	public boolean discharge(double amount,ItemStack itemstack){
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
		ItemsShared.feed(itemstack, par2, par3, par4, par5, active(itemstack), perWingEnergy, this);
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
	//no idea.. Propably Alice in Wonderland 2
	@Override
	public boolean isMetadataSpecific(ItemStack itemstack) {
		return true;
	}
}
