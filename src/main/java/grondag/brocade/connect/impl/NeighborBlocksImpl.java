package grondag.brocade.connect.impl;

import java.util.concurrent.ArrayBlockingQueue;

import grondag.brocade.connect.api.model.BlockEdge;
import grondag.brocade.connect.api.world.BlockNeighbors;
import grondag.brocade.connect.api.world.BlockTest;
import grondag.brocade.connect.api.world.ModelStateFunction;
import grondag.brocade.connect.api.model.BlockCorner;
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
public class NeighborBlocksImpl implements BlockNeighbors {
    private static final int STATE_COUNT = 6 + 12 + 8;
    private static final BlockState EMPTY_BLOCK_STATE[] = new BlockState[STATE_COUNT];
    private static final Object EMPTY_MODEL_STATE[] = new Object[STATE_COUNT];
    
    private static ThreadLocal<NeighborBlocksImpl> THREADLOCAL = ThreadLocal.withInitial(NeighborBlocksImpl::new);
    
    public static BlockNeighbors threadLocal(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest blockTest) {
        return THREADLOCAL.get().prepare(world, x, y, z, stateFunc, blockTest);
    }
    
    private static final ArrayBlockingQueue<NeighborBlocksImpl> POOL = new ArrayBlockingQueue<>(64);
    
    public static NeighborBlocksImpl claim(BlockView world, int x, int y, int z, ModelStateFunction stateFunc, BlockTest blockTest) {
        NeighborBlocksImpl result = POOL.poll();
        if (result == null) {
            result = new NeighborBlocksImpl();
        }
        result.allowReclaim = true;
        return result.prepare(world, x, y, z, stateFunc, blockTest);
    }
    
    private static void release(NeighborBlocksImpl instance) {
        if(instance.allowReclaim) {
            instance.allowReclaim = false;
            POOL.offer(instance);
        }
    }
    
    private final BlockState blockStates[] = new BlockState[STATE_COUNT];
    private final Object modelStates[] = new Object[STATE_COUNT];
    private final BlockPos.Mutable mutablePos = new BlockPos.Mutable();
    
    private boolean allowReclaim = false;
    
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
    public void release() {
        release(this);
    }
    
    @Override
    public NeighborBlocksImpl withTest(BlockTest blockTest) {
        this.blockTest = blockTest;
        completionFlags = 0;
        resultFlags = 0;
        return this;
    }
    
    //////////////////////////////
    // BLOCK STATE
    //////////////////////////////
    @Override
    public BlockState blockState(Direction face) {
        BlockState result = blockStates[face.ordinal()];
        if (result == null) {
            final Vec3i vec = face.getVector();
            result = world.getBlockState(mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            blockStates[face.ordinal()] = result;
        }
        return result;
    }

    @Override
    public BlockState blockState() {
        BlockState result = this.testBlockState;
        if (result == null) {
            result = world.getBlockState(mutablePos.set(x, y, z));
            this.testBlockState = result;
        }
        return result;
    }

    @Override
    public BlockState blockState(BlockEdge corner) {
        BlockState result = blockStates[corner.superOrdinal];
        if (result == null) {
            final Vec3i vec = corner.vector;
            result = world.getBlockState(mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            blockStates[corner.superOrdinal] = result;
        }
        return result;
    }

    @Override
    public BlockState blockState(BlockCorner corner) {
        BlockState result = blockStates[corner.superOrdinal];
        if (result == null) {
            final Vec3i vec = corner.vector;
            result = world.getBlockState(mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            blockStates[corner.superOrdinal] = result;
        }
        return result;
    }

    //////////////////////////////
    // MODEL STATE
    //////////////////////////////

    @Override
    public Object modelState() {
        if(this.stateFunc == null) 
            return null;
        
        Object result = this.testModelState;
        if (result == null) {
            BlockState state = this.blockState();
            mutablePos.set(x, y, z);
            result = this.stateFunc.get(this.world, state, mutablePos);
            this.testModelState = result;
        }
        return result;
    }
    
    @Override
    public Object modelState(Direction face) {
        if(this.stateFunc == null) 
            return null;
        
        Object result = modelStates[face.ordinal()];
        if (result == null) {
            BlockState state = this.blockState(face);
            final Vec3i vec = face.getVector();
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.stateFunc.get(this.world, state, mutablePos);
            modelStates[face.ordinal()] = result;
        }
        return result;
    }

    @Override
    public Object modelState(BlockEdge corner) {
        if(this.stateFunc == null) 
            return null;
        
        Object result = modelStates[corner.superOrdinal];
        if (result == null) {
            BlockState state = blockState(corner);
            final Vec3i vec = corner.vector;
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.stateFunc.get(this.world, state, mutablePos);
            modelStates[corner.superOrdinal] = result;
        }
        return result;
    }

    @Override
    public Object modelState(BlockCorner corner) {
        if(this.stateFunc == null) 
            return null;
        
        Object result = modelStates[corner.superOrdinal];
        if (result == null) {
            BlockState state = blockState(corner);
            final Vec3i vec = corner.vector;
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
            return this.blockTest.apply(this.blockState(), null, blockState(face), null);
        } else {
            return this.blockTest.apply(this.blockState(), this.modelState(), blockState(face), modelState(face));
        }
    }

    private boolean doTest(BlockEdge corner) {
        if (stateFunc == null) {
            return this.blockTest.apply(this.blockState(), null, blockState(corner), null);
        } else {
            return this.blockTest.apply(this.blockState(), this.modelState(), blockState(corner), modelState(corner));
        }
    }

    private boolean doTest(BlockCorner corner) {
        if (stateFunc == null) {
            return this.blockTest.apply(this.blockState(), null, blockState(corner), null);
        } else {
            return this.blockTest.apply(this.blockState(), this.modelState(), blockState(corner), modelState(corner));
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
    public boolean result(BlockEdge corner) {
        if ((completionFlags & corner.superOrdinalBit) != corner.superOrdinalBit) {
            if (doTest(corner)) {
                resultFlags |= corner.superOrdinalBit;
            }
            completionFlags |= corner.superOrdinalBit;
        }
        return (resultFlags & corner.superOrdinalBit) == corner.superOrdinalBit;
    }

    @Override
    public boolean result(BlockCorner corner) {
        if ((completionFlags & corner.superOrdinalBit) != corner.superOrdinalBit) {
            if (doTest(corner)) {
                resultFlags |= corner.superOrdinalBit;
            }
            completionFlags |= corner.superOrdinalBit;
        }
        return (resultFlags & corner.superOrdinalBit) == corner.superOrdinalBit;
    }
}
