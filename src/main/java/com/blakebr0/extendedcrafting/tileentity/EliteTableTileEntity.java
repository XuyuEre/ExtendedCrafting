package com.blakebr0.extendedcrafting.tileentity;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import com.blakebr0.cucumber.tileentity.BaseInventoryTileEntity;
import com.blakebr0.cucumber.util.Localizable;
import com.blakebr0.extendedcrafting.container.EliteTableContainer;
import com.blakebr0.extendedcrafting.init.ModTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class EliteTableTileEntity extends BaseInventoryTileEntity implements INamedContainerProvider {
	private final BaseItemStackHandler inventory = new BaseItemStackHandler(49, this::markDirtyAndDispatch);

	public EliteTableTileEntity() {
		super(ModTileEntities.ELITE_TABLE.get());
	}

	@Override
	public BaseItemStackHandler getInventory() {
		return this.inventory;
	}

	@Override
	public ITextComponent getDisplayName() {
		return Localizable.of("container.extendedcrafting.elite_table").build();
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
		return EliteTableContainer.create(windowId, playerInventory, this::isUsableByPlayer, this.inventory);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return !this.removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? LazyOptional.empty() : super.getCapability(cap, side);
	}
}
