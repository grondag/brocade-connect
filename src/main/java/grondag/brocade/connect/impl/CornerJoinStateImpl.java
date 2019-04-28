package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.CornerJoinState;
import net.minecraft.util.math.Direction;

class CornerJoinStateImpl implements CornerJoinState {
    private final int index;

    /** join state considering only direct neighbors */
    public final SimpleJoinStateImpl simpleJoin;

    private final byte[] faceJoinIndex;

    CornerJoinStateImpl(int index, SimpleJoinStateImpl simpleJoin, byte[] faceJoinIndex) {
        this.index = index;
        this.simpleJoin = simpleJoin;
        this.faceJoinIndex = faceJoinIndex;
    }

    @Override
    public SimpleJoinStateImpl simpleJoin() {
        return simpleJoin;
    }
    
    @Override
    public int ordinal() {
        return index;
    }

    @Override
    public CornerJoinFaceStateImpl faceState(Direction face) {
        return CornerJoinFaceStateImpl.fromOrdinal(faceJoinIndex[face.ordinal()]);
    }
}