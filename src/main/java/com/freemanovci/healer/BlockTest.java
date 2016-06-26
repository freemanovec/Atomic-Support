package com.freemanovci.healer;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTest extends BlockContainer{

	protected BlockTest(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int side) {
		return null;
	}

}
