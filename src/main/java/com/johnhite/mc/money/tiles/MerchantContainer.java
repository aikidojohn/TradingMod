package com.johnhite.mc.money.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MerchantContainer extends Container {

	private MerchantBlockTileEntity entity;
	private IInventory playerInv;

	public MerchantContainer(IInventory playerInv, MerchantBlockTileEntity entity) {
		this.entity = entity;
		this.playerInv = playerInv;
		
		//Ghost Blocks - Sales configuration Slot IDs 0-1;
		this.addSlotToContainer(new Slot(entity, 0, 8, 17));
		this.addSlotToContainer(new Slot(entity, 1, 8 + 18, 17));
		
		// Tile Entity, Slot 2-10, Slot IDs 2-10 - Merchant Payments
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(entity, x + y * 3 + 2, 53 + x * 18, 17 + y * 18));
	        }
	    }
	    
	    // Tile Entity, Slot 11-20, Slot IDs 11-20 - Goods for Sale
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 3; ++x) {
	            this.addSlotToContainer(new Slot(entity, x + y * 3 + 11, 116 + x * 18, 17 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 9-35, Slot IDs 21-46
	    for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory, Slot 0-8, Slot IDs 47-55
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
	    }
	}
	
	public MerchantBlockTileEntity getTileEntity() {
		return this.entity;
	}
	
	public IInventory getPlayerInventory() {
		return this.playerInv;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return entity.isUseableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        // [...] Custom behaviour
	        if (fromSlot < 21) {
	            // From TE Inventory to Player Inventory
	            if (!this.mergeItemStack(current, 21, 56, true))
	                return null;
	        } else {
	            // From Player Inventory to TE Inventory
	            if (!this.mergeItemStack(current, 0, 21, false))
	                return null;
	        }
	        //
	        
	        if (current.stackSize == 0)
	            slot.putStack((ItemStack) null);
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}

}
