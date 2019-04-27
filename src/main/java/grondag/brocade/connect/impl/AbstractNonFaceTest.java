package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.block.BlockTest;
import grondag.brocade.connect.api.model.BlockCorner;
import grondag.brocade.connect.api.model.FarCorner;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

/**
 * Base class for block tests that don't care about facing.
 */
public abstract class AbstractNonFaceTest<V> implements BlockTest<V>{
    abstract protected boolean testBlock(BlockView world, BlockState ibs, BlockPos pos, V modelState);

    abstract protected boolean testBlock(BlockView world, BlockState ibs, BlockPos pos);
    
    @Override
    public boolean testBlock(Direction face, BlockView world, BlockState ibs, BlockPos pos)
    {
        return this.testBlock(world, ibs, pos);
    }
    
    @Override
    public boolean testBlock(Direction face, BlockView world, BlockState ibs, BlockPos pos, V modelState)
    {
        return this.testBlock(world, ibs, pos, modelState);
    }

    @Override
    public boolean testBlock(BlockCorner corner, BlockView world, BlockState ibs, BlockPos pos)
    {
        return this.testBlock(world, ibs, pos);
    }

    @Override
    public boolean testBlock(BlockCorner face, BlockView world, BlockState ibs, BlockPos pos, V modelState)
    {
        return this.testBlock(world, ibs, pos, modelState);
    }
    
    @Override
    public boolean testBlock(FarCorner corner, BlockView world, BlockState ibs, BlockPos pos)
    {
        return this.testBlock(world, ibs, pos);
    }
    
    @Override
    public boolean testBlock(FarCorner face, BlockView world, BlockState ibs, BlockPos pos, V modelState)
    {
        return this.testBlock(world, ibs, pos, modelState);
    }
}
