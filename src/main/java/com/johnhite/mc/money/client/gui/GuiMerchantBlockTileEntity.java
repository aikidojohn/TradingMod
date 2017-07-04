package com.johnhite.mc.money.client.gui;

import com.johnhite.mc.money.tiles.MerchantBlockTileEntity;
import com.johnhite.mc.money.tiles.MerchantContainer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiMerchantBlockTileEntity extends GuiContainer {

	private MerchantBlockTileEntity entity;
	private IInventory playerInv;
	
	public GuiMerchantBlockTileEntity(MerchantContainer container) {
        super(container);
        this.entity = container.getTileEntity();
        this.playerInv = container.getPlayerInventory();
        this.xSize = 176;
        this.ySize = 166;
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("examplemod:textures/gui/merchant_entity_private.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.entity.getDisplayName().getUnformattedText();
	    this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
	    this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      //#404040
	}
}
