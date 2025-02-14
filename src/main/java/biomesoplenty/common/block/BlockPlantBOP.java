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
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class BlockPlantBOP extends BushBlock implements IPlantable
{
	protected static final VoxelShape NORMAL = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	
    public BlockPlantBOP(Block.Properties properties)
    {
        super(properties);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext)
    {
        return NORMAL;
    }
    
    @Override
    public Block.OffsetType getOffsetType()
    {
        return Block.OffsetType.XZ;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        Block ground = worldIn.getBlockState(pos.down()).getBlock();

        if (this == BOPBlocks.tiny_cactus)
        {
            return ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPBlocks.white_sand || super.isValidPosition(state, worldIn, pos);
        }

        return super.isValidPosition(state, worldIn, pos);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos)
    {
    	Block block = world.getBlockState(pos).getBlock();
    	
    	if (block == BOPBlocks.tiny_cactus)
    	{
    		return PlantType.Desert;
    	}
    	
    	return PlantType.Plains;
    }
    
    @Override
    public void onEntityCollision(BlockState stateIn, World worldIn, BlockPos pos, Entity entityIn)
    {
    	Block block = stateIn.getBlock();
    	
    	if (block == BOPBlocks.tiny_cactus)
    	{
    		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
    	}
    }
}
