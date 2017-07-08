package com.johnhite.mc.money.tiles;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
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
		this.addSlotToContainer(new GhostSlot(entity, 0, 8, 17));
		this.addSlotToContainer(new GhostSlot(entity, 1, 8 + 18, 17));
		
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
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		// TODO Auto-generated method stub
		return slotIn.getSlotIndex() >= 2;
	}
	@Override 
	public boolean canDragIntoSlot(Slot slotIn) {
		return slotIn.getSlotIndex() >= 2;
	}
	
	@Override
	@Nullable
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
		final boolean isOwner = player.getName().equals(entity.getOwner());
		if (slotId >= 2) {
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		}
		
		if (!isOwner) {
			return null;
		}
		
		ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = player.inventory;
		if ((clickTypeIn == ClickType.PICKUP)  && (dragType == 0 || dragType == 1)) {	
			if (slotId < 0) {
        		return null;
        	}
			
        	Slot slot = (Slot)super.inventorySlots.get(slotId);
        	if (slot != null) {
        		final ItemStack slotStack = slot.getStack();
        		final ItemStack playerStack = inventoryplayer.getItemStack();
        		if (slotStack != null) {
        			itemstack = slotStack.copy();
        		}
        		//Player is putting an item into the slot
        		if (playerStack != null) {
        			//clone player stack so we don't take from the player.
        			final ItemStack ghost = playerStack.copy();
        			int size = dragType == 0 ? ghost.stackSize : 1;

                    if (size > slot.getItemStackLimit(ghost)){
                        size = slot.getItemStackLimit(ghost);
                    }
                    slot.putStack(ghost.splitStack(size));
        		}
        		else {
        			slot.putStack(null);
        		}
        		slot.onSlotChanged();
        	}
        }
		
		return itemstack;
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

	class GhostSlot extends Slot {

		public GhostSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean canTakeStack(EntityPlayer playerIn) {
			return false;
		}
		
		@Override
		public void putStack(ItemStack is) {
			if (is != null) {
				is = is.copy();
			}
			super.putStack(is);
		}
		
		@Override
		public ItemStack decrStackSize(final int s) {
			return null;
		}
		
		@Override
		public boolean isItemValid(final ItemStack stack) {
			return false;
		}
		
		@Override
		public void onPickupFromSlot( final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack )
		{
		}
	}
}
