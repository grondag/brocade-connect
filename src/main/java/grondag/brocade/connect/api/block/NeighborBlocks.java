package grondag.brocade.connect.api.block;

import grondag.brocade.connect.api.model.BlockCorner;
import grondag.brocade.connect.api.model.FarCorner;
import grondag.brocade.connect.api.model.HorizontalCorner;
import grondag.brocade.connect.api.model.HorizontalFace;
import grondag.brocade.connect.impl.NeighborBlocksImpl;
import grondag.brocade.connect.impl.NeighborBlocksImpl.NeighborTestResults;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface NeighborBlocks<V> {

    int[] FACE_FLAGS = { 1, 2, 4, 8, 16, 32 };

    //////////////////////////////
    // BLOCK STATE
    //////////////////////////////
    BlockState getBlockState(Direction face);

    BlockState getBlockState(HorizontalFace face);

    BlockState getBlockStateUp(HorizontalFace face);

    BlockState getBlockStateDown(HorizontalFace face);

    BlockState getBlockState(Direction face1, Direction face2);

    BlockState getBlockState(HorizontalCorner corner);

    BlockState getBlockStateUp(HorizontalCorner corner);

    BlockState getBlockStateDown(HorizontalCorner corner);

    BlockState getBlockState(BlockCorner corner);

    BlockState getBlockState(Direction face1, Direction face2, Direction face3);

    BlockState getBlockState(FarCorner corner);

    V getModelState(Direction face);

    V getModelState(HorizontalFace face);

    V getModelStateUp(HorizontalFace face);

    V getModelStateDown(HorizontalFace face);

    V getModelState(Direction face1, Direction face2);

    V getModelState(HorizontalCorner corner);

    V getModelStateUp(HorizontalCorner corner);

    V getModelStateDown(HorizontalCorner corner);

    V getModelState(BlockCorner corner);

    V getModelState(Direction face1, Direction face2, Direction face3);

    V getModelState(FarCorner corner);

    /**
     * Apply given test to neighboring block states.
     */
    NeighborTestResults getNeighborTestResults(BlockTest<V> test);

    /**
     * For testing
     */
    NeighborTestResults getFakeNeighborTestResults(int faceFlags);

}