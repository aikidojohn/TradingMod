package com.johnhite.mc.money.client.gui;

import com.johnhite.mc.money.tiles.MerchantBlockTileEntity;
import com.johnhite.mc.money.tiles.MerchantContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class MoneyGuiHandler implements IGuiHandler {
	
	public static final int MERCHANT_PUBLIC_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == MERCHANT_PUBLIC_GUI) {
			return new MerchantContainer(player.inventory, (MerchantBlockTileEntity)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == MERCHANT_PUBLIC_GUI) {
			return new GuiMerchantBlockTileEntity(new MerchantContainer(player.inventory, (MerchantBlockTileEntity)world.getTileEntity(new BlockPos(x, y, z))));
		}
		return null;
	}

}
