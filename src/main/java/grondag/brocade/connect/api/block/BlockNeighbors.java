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

public interface BlockNeighbors {
    void release();
    
    BlockNeighbors withTest(BlockTest blockTest);
    
    BlockState blockState();
    
    BlockState blockState(Direction face);

    BlockState blockState(BlockCorner corner);

    BlockState blockState(FarCorner corner);
    
    default BlockState blockState(HorizontalFace face) {
        return blockState(face.face);
    }

    default BlockState blockState(Direction face1, Direction face2) {
        return blockState(BlockCorner.find(face1, face2));
    }

    default BlockState blockState(HorizontalCorner corner) {
        return blockState(corner.face1.face, corner.face2.face);
    }
    
    default BlockState blockState(Direction face1, Direction face2, Direction face3) {
        return blockState(FarCorner.find(face1, face2, face3));
    }

    Object modelState();
    
    Object modelState(Direction face);

    Object modelState(BlockCorner corner);

    Object modelState(FarCorner corner);

    default Object modelState(Direction face1, Direction face2, Direction face3) {
        return modelState(FarCorner.find(face1, face2, face3));
    }

    default Object modelState(HorizontalFace face) {
        return modelState(face.face);
    }

    default Object modelState(Direction face1, Direction face2) {
        return modelState(BlockCorner.find(face1, face2));
    }

    default Object modelState(HorizontalCorner corner) {
        return modelState(corner.face1.face, corner.face2.face);
    }
    
    /** use this to override world results */
    void override(Direction face, boolean override);
    
    boolean result(Direction face);

    boolean result(BlockCorner corner);

    boolean result(FarCorner corner);
    
    public default boolean result(Direction face1, Direction face2) {
        return result(BlockCorner.find(face1, face2));
    }
    
    public default boolean result(HorizontalFace face) {
        return result(face.face);
    }

    public default boolean result(HorizontalCorner corner) {
        return result(corner.face1.face, corner.face2.face);
    }

    public default boolean result(Direction face1, Direction face2, Direction face3) {
        return result(FarCorner.find(face1, face2, face3));
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, stateFunc, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, stateFunc, null);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, null, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z) {
        return NeighborBlocksImpl.threadLocal(world, x, y, z, null, null);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos, ModelStateFunction stateFunc, BlockTest test) {
        return NeighborBlocksImpl.threadLocal(world, pos.getX(), pos.getY(), pos.getZ(), stateFunc, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos, ModelStateFunction stateFunc) {
        return threadLocal(world, pos, stateFunc, null);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos, BlockTest test) {
        return threadLocal(world, pos, null, test);
    }
    
    public static BlockNeighbors threadLocal(BlockView world, BlockPos pos) {
        return threadLocal(world, pos, null, null);
    }
}