/*******************************************************************************
 * Copyright 2014-2019, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/
package biomesoplenty.common.block;

import biomesoplenty.api.block.BOPBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;

public class BlockFoliageBOP extends BushBlock implements IPlantable
{
	protected static final VoxelShape NORMAL = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	protected static final VoxelShape SHORT = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
	
    public BlockFoliageBOP(Block.Properties properties)
    {
        super(properties);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext)
    {
    	Block block = state.getBlock();
        
        if (block == BOPBlocks.desert_grass)
        {
        	return SHORT;
        }
        
        return NORMAL;
    }
    
    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
           player.addStat(Stats.BLOCK_MINED.get(this));
           player.addExhaustion(0.005F);
           spawnAsEntity(worldIn, pos, new ItemStack(this));
        }
        else
        {
           super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
     }
    
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune)
    {
       world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
       return java.util.Arrays.asList(new ItemStack(this));
    }
    
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        Block ground = worldIn.getBlockState(pos.down()).getBlock();

        if (this == BOPBlocks.spectral_fern)
        {
            return ground == Blocks.END_STONE;
        }
        if (this == BOPBlocks.dune_grass)
        {
            return ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPBlocks.white_sand;
        }
        if (this == BOPBlocks.desert_grass || this == BOPBlocks.dead_grass)
        {
            return ground == BOPBlocks.dried_sand || ground == Blocks.GRAVEL || ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPBlocks.white_sand || super.isValidPosition(state, worldIn, pos);
        }

        return super.isValidPosition(state, worldIn, pos);
    }
    
    @Override
    public Block.OffsetType getOffsetType()
    {
        return Block.OffsetType.XYZ;
    }
    
    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos)
    {
    	return PlantType.Plains;
    }
    
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
    	return Blocks.GRASS.getFlammability(state, world, pos, face);
    }
    
    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return Blocks.GRASS.getFireSpreadSpeed(state,world, pos, face);
    }
}
