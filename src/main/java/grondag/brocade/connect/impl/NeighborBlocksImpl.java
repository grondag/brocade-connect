package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.block.BlockTest;
import grondag.brocade.connect.api.block.CornerJoinTestProvider;
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
public class NeighborBlocksImpl<V> implements NeighborBlocks<V> {
    private final static Direction[] FACES = Direction.values();
    // Direction.values().length + BlockCorner.values().length +
    // FarCorner.values().length = 6 +
    private final static int STATE_COUNT = 6 + 12 + 8;

    private BlockState blockStates[] = new BlockState[STATE_COUNT];
    private Object modelStates[] = new Object[STATE_COUNT];

    private final BlockView world;
    private final int x;
    private final int y;
    private final int z;
    private final ModelStateFunction<V> factory;
    private final BlockPos.Mutable mutablePos = new BlockPos.Mutable();

    /**
     * Gathers blockstates for adjacent positions as needed.
     */
    public NeighborBlocksImpl(BlockView worldIn, BlockPos pos) {
        this(worldIn, pos, ModelStateFunction::NONE);
    }

    public NeighborBlocksImpl(BlockView worldIn, BlockPos pos, ModelStateFunction<V> factory) {
        this.world = worldIn;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.factory = factory;
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
    public V getModelState(Direction face) {
        @SuppressWarnings("unchecked")
        V result = (V) modelStates[face.ordinal()];
        if (result == null) {
            BlockState state = this.getBlockState(face);
            final Vec3i vec = face.getVector();
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.factory.get(this.world, mutablePos, state);
            modelStates[face.ordinal()] = result;
        }
        return result;
    }

    @Override
    public V getModelState(HorizontalFace face) {
        return getModelState(face.face);
    }

    @Override
    public V getModelStateUp(HorizontalFace face) {
        return getModelState(face.face, Direction.UP);
    }

    @Override
    public V getModelStateDown(HorizontalFace face) {
        return getModelState(face.face, Direction.DOWN);
    }

    @Override
    public V getModelState(Direction face1, Direction face2) {
        BlockCorner corner = BlockCorner.find(face1, face2);
        return getModelState(corner);
    }

    @Override
    public V getModelState(HorizontalCorner corner) {
        return getModelState(corner.face1.face, corner.face2.face);
    }

    @Override
    public V getModelStateUp(HorizontalCorner corner) {
        return getModelState(corner.face1.face, corner.face2.face, Direction.UP);
    }

    @Override
    public V getModelStateDown(HorizontalCorner corner) {
        return getModelState(corner.face1.face, corner.face2.face, Direction.DOWN);
    }

    @Override
    public V getModelState(BlockCorner corner) {
        @SuppressWarnings("unchecked")
        V result = (V) modelStates[corner.superOrdinal];
        if (result == null) {
            BlockState state = this.getBlockState(corner);
            final Vec3i vec = corner.directionVector;
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.factory.get(this.world, mutablePos, state);
            modelStates[corner.superOrdinal] = result;
        }
        return result;
    }

    @Override
    public V getModelState(Direction face1, Direction face2, Direction face3) {
        FarCorner corner = FarCorner.find(face1, face2, face3);
        return getModelState(corner);
    }

    @Override
    public V getModelState(FarCorner corner) {
        @SuppressWarnings("unchecked")
        V result = (V) modelStates[corner.superOrdinal];
        if (result == null) {
            BlockState state = this.getBlockState(corner);
            final Vec3i vec = corner.directionVector;
            mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ());
            result = this.factory.get(this.world, mutablePos, state);
            modelStates[corner.superOrdinal] = result;
        }
        return result;
    }

    //////////////////////////////
    // TESTS AND OTHER STUFF
    //////////////////////////////

    /**
     * Apply given test to neighboring block states.
     */
    @Override
    public NeighborTestResults getNeighborTestResults(BlockTest<V> test) {
        return new NeighborTestResults(test);
    }

    /**
     * For testing
     */
    @Override
    public NeighborTestResults getFakeNeighborTestResults(int faceFlags) {
        return new NeighborTestResults(faceFlags);
    }

    /**
     * Convenient data structure for returning test results.
     */
    public class NeighborTestResults implements CornerJoinTestProvider {
        private int completionFlags = 0;
        private int resultFlags = 0;
        private final BlockTest<V> test;

        private NeighborTestResults(BlockTest<V> test) {
            this.test = test;
        }

        // for testing
        private NeighborTestResults(int faceFlags) {
            this.test = null;
            this.resultFlags = faceFlags;
            this.completionFlags = (1 << 27) - 1; // 26 bits mask
        }

        private boolean doTest(Direction face) {
            BlockState state = getBlockState(face);
            final Vec3i vec = face.getVector();

            if (test.wantsModelState()) {
                V modelState = getModelState(face);
                return test.testBlock(face, world, state,
                        mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()), modelState);
            } else {
                return test.testBlock(face, world, state,
                        mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            }
        }

        private boolean doTest(BlockCorner corner) {
            BlockState state = getBlockState(corner);
            final Vec3i vec = corner.directionVector;

            if (test.wantsModelState()) {
                V modelState = getModelState(corner);
                return test.testBlock(corner, world, state,
                        mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()), modelState);
            } else {
                return test.testBlock(corner, world, state,
                        mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            }
        }

        private boolean doTest(FarCorner corner) {
            BlockState state = getBlockState(corner);
            final Vec3i vec = corner.directionVector;

            if (test.wantsModelState()) {
                V modelState = getModelState(corner);
                return test.testBlock(corner, world, state,
                        mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()), modelState);
            } else {
                return test.testBlock(corner, world, state,
                        mutablePos.set(x + vec.getX(), y + vec.getY(), z + vec.getZ()));
            }
        }

        @Override
        public boolean result(Direction face) {
            int bitFlag = FACE_FLAGS[face.ordinal()];
            if ((completionFlags & bitFlag) != bitFlag) {
                if (doTest(face)) {
                    resultFlags |= bitFlag;
                }
                completionFlags |= bitFlag;
            }
            return (resultFlags & bitFlag) == bitFlag;
        }

        /** use this to override world results */
        public void override(Direction face, boolean override) {
            int bitFlag = FACE_FLAGS[face.ordinal()];
            completionFlags |= bitFlag;
            if (override) {
                resultFlags |= bitFlag;
            } else {
                resultFlags &= ~bitFlag;
            }
        }

        public boolean result(HorizontalFace face) {
            return result(face.face);
        }

        public boolean resultUp(HorizontalFace face) {
            return result(face.face, Direction.UP);
        }

        public boolean resultDown(HorizontalFace face) {
            return result(face.face, Direction.DOWN);
        }

        /** convenience method */
        public int resultBit(Direction face) {
            return result(face) ? 1 : 0;
        }

        public boolean result(HorizontalCorner corner) {
            return result(corner.face1.face, corner.face2.face);
        }

        public boolean resultUp(HorizontalCorner corner) {
            return result(corner.face1.face, corner.face2.face, Direction.UP);
        }

        public boolean resultDown(HorizontalCorner corner) {
            return result(corner.face1.face, corner.face2.face, Direction.DOWN);
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

        public int resultBit(Direction face1, Direction face2) {
            return result(face1, face2) ? 1 : 0;
        }

        public int resultBit(BlockCorner corner) {
            return result(corner) ? 1 : 0;
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

        public int resultBit(Direction face1, Direction face2, Direction face3) {
            return result(face1, face2, face3) ? 1 : 0;
        }

        public int resultBit(FarCorner corner) {
            return result(corner) ? 1 : 0;
        }

        @Override
        public String toString() {
            String retval = "";

            for (int i = 0; i < 6; i++) {
                final Direction face = FACES[i];
                retval += face.toString() + "=" + this.result(face) + " ";
            }

            for (BlockCorner corner : BlockCorner.values()) {
                retval += corner.toString() + "=" + this.result(corner) + " ";
            }

            for (FarCorner corner : FarCorner.values()) {
                retval += corner.toString() + "=" + this.result(corner) + " ";
            }
            return retval;
        }
    }
}
