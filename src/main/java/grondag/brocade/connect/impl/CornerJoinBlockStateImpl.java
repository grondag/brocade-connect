package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.CornerJoinBlockState;
import net.minecraft.util.math.Direction;

class CornerJoinBlockStateImpl implements CornerJoinBlockState {
    private final int index;

    /** join state considering only direct neighbors */
    public final SimpleJoin simpleJoin;

    private final byte[] faceJoinIndex;

    CornerJoinBlockStateImpl(int index, SimpleJoin simpleJoin, byte[] faceJoinIndex) {
        this.index = index;
        this.simpleJoin = simpleJoin;
        this.faceJoinIndex = faceJoinIndex;
    }

    @Override
    public SimpleJoin simpleJoin() {
        return simpleJoin;
    }
    
    @Override
    public int index() {
        return index;
    }

    //PERF: values()
    @Override
    public CornerJoinFaceStateImpl getFaceJoinState(Direction face) {
        return CornerJoinFaceStateImpl.values()[faceJoinIndex[face.ordinal()]];
    }
}