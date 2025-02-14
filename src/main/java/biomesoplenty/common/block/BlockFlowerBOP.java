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
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockFlowerBOP extends FlowerBlock
{
	protected static final VoxelShape NORMAL = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
	protected static final VoxelShape LARGE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	
    public BlockFlowerBOP(Block.Properties properties)
    {
        super(Effects.SLOWNESS, 0, properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext)
    {
    	Block block = state.getBlock();
        
        if (block == BOPBlocks.lavender || block == BOPBlocks.pink_hibiscus)
        {
        	return LARGE;
        }
        
        return NORMAL;
    }
    
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        Block ground = worldIn.getBlockState(pos.down()).getBlock();

        if (this == BOPBlocks.wildflower)
        {
            return ground == Blocks.SAND || ground == Blocks.RED_SAND || ground == BOPBlocks.white_sand || super.isValidPosition(state, worldIn, pos);
        }

        return super.isValidPosition(state, worldIn, pos);
    }
    
    @Override
    public void onEntityCollision(BlockState stateIn, World worldIn, BlockPos pos, Entity entityIn)
    {
    	Block block = stateIn.getBlock();
    	
    	if (entityIn instanceof LivingEntity)
    	{
	    	if (block == BOPBlocks.burning_blossom)
	    	{
	    		(entityIn).setFire(1);
	    	}
    	}
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
       super.animateTick(stateIn, worldIn, pos, rand);
       Block block = stateIn.getBlock();
       
       if (block == BOPBlocks.burning_blossom)
       {
	       if (rand.nextInt(4) == 0)
	       {
	    	   worldIn.addParticle(ParticleTypes.FLAME, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY() + rand.nextFloat()), (double)((float)pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D);
	       }
	       if (rand.nextInt(2) == 0)
	       {
	    	   worldIn.addParticle(ParticleTypes.SMOKE, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY() + rand.nextFloat()), (double)((float)pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D);
	       }
	   }
    }
    
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
    	return Blocks.POPPY.getFlammability(state, world, pos, face);
    }
    
    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return Blocks.POPPY.getFireSpreadSpeed(state,world, pos, face);
    }
}
