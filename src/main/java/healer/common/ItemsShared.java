package healer.common;

import healer.items.FeederItem;
import healer.items.HealerItem;
import healer.items.MultiItem;
import mekanism.api.EnumColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemsShared {
	public static void createNBT(ItemStack itemstack){
		itemstack.stackTagCompound = new NBTTagCompound();
		itemstack.stackTagCompound.setDouble("energy", 0);
		itemstack.stackTagCompound.setBoolean("active", true);
	}
	public static void feed(ItemStack itemstack, World par2, Entity par3, int par4, boolean par5, boolean active, double perEnergy, FeederItem feederItem){
		if(itemstack.stackTagCompound != null){
			if(!par2.isRemote && active){
				//gets food level
				EntityPlayerMP player = (EntityPlayerMP) par3;
				float foodLevel = player.getFoodStats().getFoodLevel();
				//checks and feeds (SERVERSIDE)
				if(player.getFoodStats().needFood()){
					if(feederItem.discharge(perEnergy,itemstack)){
						player.getFoodStats().setFoodLevel((int)foodLevel + 1);
					}
				}
			}
		}else{
			//first run, no NBT detected
			createNBT(itemstack);
		}
	}
	public static void feed(ItemStack itemstack, World par2, Entity par3, int par4, boolean par5, boolean active, double perEnergy, MultiItem feederItem){
		if(itemstack.stackTagCompound != null){
			if(!par2.isRemote && active){
				//gets food level
				EntityPlayerMP player = (EntityPlayerMP) par3;
				float foodLevel = player.getFoodStats().getFoodLevel();
				//checks and feeds (SERVERSIDE)
				if(player.getFoodStats().needFood()){
					if(feederItem.discharge(perEnergy,itemstack)){
						player.getFoodStats().setFoodLevel((int)foodLevel + 1);
					}
				}
			}
		}else{
			//first run, no NBT detected
			createNBT(itemstack);
		}
	}
	public static void heal(ItemStack itemstack, World par2, Entity par3, int par4, boolean par5, boolean active, double perEnergy, HealerItem healerItem){
		if(itemstack.stackTagCompound != null){
			if(!par2.isRemote && active){
				//gets health and max health
				EntityPlayerMP player = (EntityPlayerMP) par3;
				float health = player.getHealth();
				float maxHealth = player.getMaxHealth();
				//compares and heals (SERVERSIDE)
				if(health < maxHealth){
					if(healerItem.discharge(perEnergy,itemstack)){
						player.heal(1f);
					}
				}
			}
		}else{
			//first run, no NBT detected
			createNBT(itemstack);
		}
	}
	public static void heal(ItemStack itemstack, World par2, Entity par3, int par4, boolean par5, boolean active, double perEnergy, MultiItem healerItem){
		if(itemstack.stackTagCompound != null){
			if(!par2.isRemote && active){
				//gets health and max health
				EntityPlayerMP player = (EntityPlayerMP) par3;
				float health = player.getHealth();
				float maxHealth = player.getMaxHealth();
				//compares and heals (SERVERSIDE)
				if(health < maxHealth){
					if(healerItem.discharge(perEnergy,itemstack)){
						player.heal(1f);
					}
				}
			}
		}else{
			//first run, no NBT detected
			createNBT(itemstack);
		}
	}
}
