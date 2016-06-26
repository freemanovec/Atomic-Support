package com.freemanovci.healer;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTest extends Block implements ITileEntityProvider{

	public BlockTest(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int side) {
		System.out.println("New tile entity has been created in world " + world.toString() + " facing " + side);
		return new TETest();
	}

	public boolean hasTileEntity(){
		return true;
	}
}
