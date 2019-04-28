package grondag.brocade.connect.api.block;

import grondag.brocade.connect.api.model.BlockCorner;
import grondag.brocade.connect.api.model.FarCorner;
import grondag.brocade.connect.api.model.HorizontalCorner;
import grondag.brocade.connect.api.model.HorizontalFace;
import grondag.brocade.connect.impl.NeighborBlocksImpl;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public interface NeighborBlocks {
    
    public static NeighborBlocks threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, stateFunc, test);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, stateFunc, null);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, int x, int y, int z, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, null, test);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, int x, int y, int z) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, null, null);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, BlockPos pos, ModelStateFunction stateFunc, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, pos.getX(), pos.getY(), pos.getZ(), stateFunc, test);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, BlockPos pos, ModelStateFunction stateFunc) {
        return NeighborBlocksImpl.threadLocal(world, pos.getX(), pos.getY(), pos.getZ(), stateFunc, null);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, BlockPos pos, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, pos.getX(), pos.getY(), pos.getZ(), null, test);
    }
    
    public static NeighborBlocks threadLocal(BlockView world, BlockPos pos) {
        return NeighborBlocksImpl.threadLocal(world, pos.getX(), pos.getY(), pos.getZ(), null, null);
    }
    
    NeighborBlocks test(BlockTest blockTest);
    
    //////////////////////////////
    // BLOCK STATE
    //////////////////////////////
    
    BlockState getBlockState();
    
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

    Object getModelState();
    
    Object getModelState(Direction face);

    Object getModelState(HorizontalFace face);

    Object getModelStateUp(HorizontalFace face);

    Object getModelStateDown(HorizontalFace face);

    Object getModelState(Direction face1, Direction face2);

    Object getModelState(HorizontalCorner corner);

    Object getModelStateUp(HorizontalCorner corner);

    Object getModelStateDown(HorizontalCorner corner);

    Object getModelState(BlockCorner corner);

    Object getModelState(Direction face1, Direction face2, Direction face3);

    Object getModelState(FarCorner corner);

    boolean result(Direction face);

    /** use this to override world results */
    void override(Direction face, boolean override);

    /**
     * Convenience for {@link #result(BlockCorner)}
     */
    public default boolean result(Direction face1, Direction face2) {
        BlockCorner corner = BlockCorner.find(face1, face2);
        return result(corner);
    }
    
    public default boolean result(HorizontalFace face) {
        return result(face.face);
    }

    public default boolean resultUp(HorizontalFace face) {
        return result(face.face, Direction.UP);
    }

    public default boolean resultDown(HorizontalFace face) {
        return result(face.face, Direction.DOWN);
    }
    
    public default boolean result(HorizontalCorner corner) {
        return result(corner.face1.face, corner.face2.face);
    }

    /**
     * Convenience for {@link #result(FarCorner)}
     */
    public default boolean result(Direction face1, Direction face2, Direction face3) {
        FarCorner corner = FarCorner.find(face1, face2, face3);
        return result(corner);
    }
    
    public default boolean resultUp(HorizontalCorner corner) {
        return result(corner.face1.face, corner.face2.face, Direction.UP);
    }

    public default boolean resultDown(HorizontalCorner corner) {
        return result(corner.face1.face, corner.face2.face, Direction.DOWN);
    }

    boolean result(BlockCorner corner);

    boolean result(FarCorner corner);
}