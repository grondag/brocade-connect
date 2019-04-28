package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.block.BlockTest;
import grondag.brocade.connect.api.block.ModelStateFunction;
import grondag.brocade.connect.api.block.NeighborBlocks;
import grondag.brocade.connect.api.model.BlockCorner;
import grondag.brocade.connect.api.model.FarCorner;
import grondag.brocade.connect.api.model.HorizontalCorner;
import grondag.brocade.connect.api.model.HorizontalFace;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;

/**
 * Convenient way to gather and test block states for blocks adjacent to a given
 * position. Position is immutable, blockstates are looked up lazily and values
 * are cached for reuse.
 */
public class NeighborBlocksImpl implements NeighborBlocks {
    private static final int STATE_COUNT = 6 + 12 + 8;
    private static final BlockState EMPTY_BLOCK_STATE[] = new BlockState[STATE_COUNT];
    private static final Object EMPTY_MODEL_STATE[] = new Object[STATE_COUNT];
    
    private static ThreadLocal<NeighborBlocksImpl> THREADLOCAL = ThreadLocal.withInitial(NeighborBlocksImpl::new);
    
    public static NeighborBlocks threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest blockTest) {
        return THREADLOCAL.get().prepare(world, x, y, z, stateFunc, blockTest);
    }
    
    private final BlockState blockStates[] = new BlockState[STATE_COUNT];
    private final Object modelStates[] = new Object[STATE_COUNT];
    private final BlockPos.Mutable mutablePos = new BlockPos.Mutable();
    
    private int completionFlags = 0;
    private int resultFlags = 0;
    
    private BlockView world;
    private int x;
    private int y;
    private int z;
    private ModelStateFunction stateFunc;

    private BlockTest blockTest;
    private BlockState testBlockState;
    private Object testModelState;
    
    protected NeighborBlocksImpl () {
    }
    
    NeighborBlocksImpl prepare(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest blockTest) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.stateFunc = stateFunc;
        this.blockTest = blockTest;
        completionFlags = blockTest == null ? -1 : 0;
        resultFlags = 0;
        System.arraycopy(EMPTY_BLOCK_STATE, 0, blockStates, 0, STATE_COUNT);
        System.arraycopy(EMPTY_MODEL_STATE, 0, modelStates, 0, STATE_COUNT);
        return this;
    }
    
    @Override
    public NeighborBlocksImpl test(BlockTest blockTest) {
        this.blockTest = blockTest;
        completionFlags = 0;
        resultFlags = 0;
        return this;
    }
    
    //////////////////////////////
    // BLOCK STATE
    //////////////////////////////
    @Override
    public BlockState getBlockState(Direction face) {
        BlockState result = blockStates[face.ordinal()];
        if (result == null) {
            final Vec3i vec = face.getVector();
            result = world.getBlockState(mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            blockStates[face.ordinal()] = result;
        }
        return result;
    }

    @Override
    public BlockState getBlockState(HorizontalFace face) {
        return getBlockState(face.face);
    }

    @Override
    public BlockState getBlockStateUp(HorizontalFace face) {
        return getBlockState(face.face, Direction.UP);
    }

    @Override
    public BlockState getBlockStateDown(HorizontalFace face) {
        return getBlockState(face.face, Direction.DOWN);
    }

    @Override
    public BlockState getBlockState(Direction face1, Direction face2) {
        BlockCorner corner = BlockCorner.find(face1, face2);
        return getBlockState(corner);
    }

    @Override
    public BlockState getBlockState(HorizontalCorner corner) {
        return getBlockState(corner.face1.face, corner.face2.face);
    }

    @Override
    public BlockState getBlockStateUp(HorizontalCorner corner) {
        return getBlockState(corner.face1.face, corner.face2.face, Direction.UP);
    }

    @Override
    public BlockState getBlockStateDown(HorizontalCorner corner) {
        return getBlockState(corner.face1.face, corner.face2.face, Direction.DOWN);
    }
    
    @Override
    public BlockState getBlockState() {
        BlockState result = this.testBlockState;
        if (result == null) {
            result = world.getBlockState(mutablePos.set(x, y, z));
            this.testBlockState = result;
        }
        return result;
    }

    @Override
    public BlockState getBlockState(BlockCorner corner) {
        BlockState result = blockStates[corner.superOrdinal];
        if (result == null) {
            final Vec3i vec = corner.directionVector;
            result = world.getBlockState(mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            blockStates[corner.superOrdinal] = result;
        }
        return result;
    }

    @Override
    public BlockState getBlockState(Direction face1, Direction face2, Direction face3) {
        FarCorner corner = FarCorner.find(face1, face2, face3);
        return getBlockState(corner);
    }

    @Override
    public BlockState getBlockState(FarCorner corner) {
        BlockState result = blockStates[corner.superOrdinal];
        if (result == null) {
            final Vec3i vec = corner.directionVector;
            result = world.getBlockState(mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            blockStates[corner.superOrdinal] = result;
        }
        return result;
    }

    //////////////////////////////
    // MODEL STATE
    //////////////////////////////

    @Override
    public Object getModelState() {
        if(this.stateFunc == null) 
            return null;
        
        Object result = this.testModelState;
        if (result == null) {
            BlockState state = this.getBlockState();
            mutablePos.set(x, y, z);
            result = this.stateFunc.get(this.world, state, mutablePos);
            this.testModelState = result;
        }
        return result;
    }
    
    @Override
    public Object getModelState(Direction face) {
        if(this.stateFunc == null) 
            return null;
        
        Object result = modelStates[face.ordinal()];
        if (result == null) {
            BlockState state = this.getBlockState(face);
            final Vec3i vec = face.getVector();
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.stateFunc.get(this.world, state, mutablePos);
            modelStates[face.ordinal()] = result;
        }
        return result;
    }

    @Override
    public Object getModelState(HorizontalFace face) {
        return getModelState(face.face);
    }

    @Override
    public Object getModelStateUp(HorizontalFace face) {
        return getModelState(face.face, Direction.UP);
    }

    @Override
    public Object getModelStateDown(HorizontalFace face) {
        return getModelState(face.face, Direction.DOWN);
    }

    @Override
    public Object getModelState(Direction face1, Direction face2) {
        BlockCorner corner = BlockCorner.find(face1, face2);
        return getModelState(corner);
    }

    @Override
    public Object getModelState(HorizontalCorner corner) {
        return getModelState(corner.face1.face, corner.face2.face);
    }

    @Override
    public Object getModelStateUp(HorizontalCorner corner) {
        return getModelState(corner.face1.face, corner.face2.face, Direction.UP);
    }

    @Override
    public Object getModelStateDown(HorizontalCorner corner) {
        return getModelState(corner.face1.face, corner.face2.face, Direction.DOWN);
    }

    @Override
    public Object getModelState(BlockCorner corner) {
        if(this.stateFunc == null) 
            return null;
        
        Object result = modelStates[corner.superOrdinal];
        if (result == null) {
            BlockState state = this.getBlockState(corner);
            final Vec3i vec = corner.directionVector;
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.stateFunc.get(this.world, state, mutablePos);
            modelStates[corner.superOrdinal] = result;
        }
        return result;
    }

    @Override
    public Object getModelState(Direction face1, Direction face2, Direction face3) {
        FarCorner corner = FarCorner.find(face1, face2, face3);
        return getModelState(corner);
    }

    @Override
    public Object getModelState(FarCorner corner) {
        if(this.stateFunc == null) 
            return null;
        
        Object result = modelStates[corner.superOrdinal];
        if (result == null) {
            BlockState state = this.getBlockState(corner);
            final Vec3i vec = corner.directionVector;
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.stateFunc.get(this.world, state, mutablePos);
            modelStates[corner.superOrdinal] = result;
        }
        return result;
    }

    //////////////////////////
    // TESTS
    //////////////////////////
    
    private boolean doTest(Direction face) {
        if (stateFunc == null) {
            return this.blockTest.apply(this.getBlockState(), null, getBlockState(face), null);
        } else {
            return this.blockTest.apply(this.getBlockState(), this.getModelState(), getBlockState(face), getModelState(face));
        }
    }

    private boolean doTest(BlockCorner corner) {
        if (stateFunc == null) {
            return this.blockTest.apply(this.getBlockState(), null, getBlockState(corner), null);
        } else {
            return this.blockTest.apply(this.getBlockState(), this.getModelState(), getBlockState(corner), getModelState(corner));
        }
    }

    private boolean doTest(FarCorner corner) {
        if (stateFunc == null) {
            return this.blockTest.apply(this.getBlockState(), null, getBlockState(corner), null);
        } else {
            return this.blockTest.apply(this.getBlockState(), this.getModelState(), getBlockState(corner), getModelState(corner));
        }
    }
    
    @Override
    public boolean result(Direction face) {
        int bitFlag = 1 << face.ordinal();
        if ((completionFlags & bitFlag) != bitFlag) {
            if (doTest(face)) {
                resultFlags |= bitFlag;
            }
            completionFlags |= bitFlag;
        }
        return (resultFlags & bitFlag) == bitFlag;
    }

    @Override
    public void override(Direction face, boolean override) {
        final int bitFlag = 1 << face.ordinal();
        completionFlags |= bitFlag;
        if (override) {
            resultFlags |= bitFlag;
        } else {
            resultFlags &= ~bitFlag;
        }        
    }

    @Override
    public boolean result(BlockCorner corner) {
        if ((completionFlags & corner.ordinalBit) != corner.ordinalBit) {
            if (doTest(corner)) {
                resultFlags |= corner.ordinalBit;
            }
            completionFlags |= corner.ordinalBit;
        }
        return (resultFlags & corner.ordinalBit) == corner.ordinalBit;
    }

    @Override
    public boolean result(FarCorner corner) {
        if ((completionFlags & corner.bitFlag) != corner.bitFlag) {
            if (doTest(corner)) {
                resultFlags |= corner.bitFlag;
            }
            completionFlags |= corner.bitFlag;
        }
        return (resultFlags & corner.bitFlag) == corner.bitFlag;
    }
}
