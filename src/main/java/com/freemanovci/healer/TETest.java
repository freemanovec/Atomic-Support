package com.freemanovci.healer;

import mekanism.api.IConfigurable;
import mekanism.api.energy.ICableOutputter;
import mekanism.api.energy.IStrictEnergyStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

public class TETest extends TileEntity implements IConfigurable, ICableOutputter, IStrictEnergyStorage{
	
	boolean canOutputEnergy = false;
	int startAt = 100;
	int actualTick = 0;
	int preTicked = 0;
	int retickAt = 20;
	
	double maxEnergy = 2000000;
	double actualEnergy = 25000;

	public static void init(){
		System.out.println("Tile initialized!");
	}
	
	/*@Override
	public void updateEntity(){
		//System.out.println("TE Ticked!");
		if(actualTick < 100){
			actualTick++;
		}else{
			if(preTicked<retickAt){
				preTicked++;
			}else{
				this.worldObj.createExplosion((Entity)null, this.xCoord+0.5f, this.yCoord+0.5f, this.zCoord+0.5f, 10, false);
				preTicked = 0;
			}
		}
	}*/
	
	@Override
	public boolean onSneakRightClick(EntityPlayer player, int side) {
		//System.out.println("Sneak right clicked on side " + side);
		
		/*this.worldObj.createExplosion((Entity)null, this.xCoord+0.5f, this.yCoord+0.5f, this.zCoord+0.5f, 5, false);
		
		String message = "BOOM! In your face!!";*/
		
		String message = "Actual energy: " + actualEnergy;
		System.out.println(message);
		player.addChatMessage(new ChatComponentText(message));
		
		return true;
	}
	@Override
	public boolean onRightClick(EntityPlayer player, int side) {
		//System.out.println("Right clicked on side " + side);
		
		/*String color = "";
		if(!canOutputEnergy){
			color="$a";
		}else{
			color="$4";
		}*/
		String message = "Can output power: " + !canOutputEnergy;
		System.out.println(message);
		player.addChatMessage(new ChatComponentText(message));
		
		canOutputEnergy = !canOutputEnergy;
		return true;
	}

	@Override
	public boolean canOutputTo(ForgeDirection side) {
		// TODO Auto-generated method stub
		return canOutputEnergy;
	}

	@Override
	public double getEnergy() {
		// TODO Auto-generated method stub
		return actualEnergy;
	}

	@Override
	public void setEnergy(double energy) {
		// TODO Auto-generated method stub
		actualEnergy = energy;
	}

	@Override
	public double getMaxEnergy() {
		// TODO Auto-generated method stub
		return maxEnergy;
	}
	
}
