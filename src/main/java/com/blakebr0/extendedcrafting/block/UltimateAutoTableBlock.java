package com.blakebr0.extendedcrafting.block;

import com.blakebr0.cucumber.block.BaseTileEntityBlock;
import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.util.VoxelShapeBuilder;
import com.blakebr0.extendedcrafting.config.ModConfigs;
import com.blakebr0.extendedcrafting.lib.ModTooltips;
import com.blakebr0.extendedcrafting.tileentity.AutoTableTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class UltimateAutoTableBlock extends BaseTileEntityBlock implements IEnableable {
    public static final VoxelShape ULTIMATE_AUTO_TABLE_SHAPE = new VoxelShapeBuilder()
            .cuboid(14, 2, 14, 2, 0, 2)
            .cuboid(5, 10, 5, 3, 2, 3)
            .cuboid(13, 10, 13, 11, 2, 11)
            .cuboid(13, 10, 5, 11, 2, 3)
            .cuboid(5, 10, 13, 3, 2, 11)
            .cuboid(16, 16, 16, 0, 10, 0)
            .cuboid(12, 10, 12, 4, 2, 4)
            .build();

    public UltimateAutoTableBlock() {
        super(Material.IRON, SoundType.METAL, 5.0F, 10.0F);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AutoTableTileEntity.Ultimate();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote()) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof AutoTableTileEntity.Ultimate) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (AutoTableTileEntity.Ultimate) tile, pos);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof AutoTableTileEntity.Ultimate) {
                AutoTableTileEntity.Ultimate table = (AutoTableTileEntity.Ultimate) tile;
                InventoryHelper.dropItems(world, pos, table.getInventory().getStacks());
            }
        }

        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return ULTIMATE_AUTO_TABLE_SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(ModTooltips.TIER.args(4).build());
    }

    @Override
    public boolean isEnabled() {
        return ModConfigs.ENABLE_TABLES.get() && ModConfigs.ENABLE_AUTO_TABLES.get();
    }
}
