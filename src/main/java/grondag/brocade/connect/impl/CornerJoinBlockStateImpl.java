package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.CornerJoinBlockState;
import net.minecraft.util.math.Direction;

public class CornerJoinBlockStateImpl implements CornerJoinBlockState {
    private final int index;

    /** join state considering only direct neighbors */
    public final SimpleJoin simpleJoin;

    private byte faceJoinIndex[] = new byte[6];

    CornerJoinBlockStateImpl(int index, SimpleJoin simpleJoin) {
        this.index = index;
        this.simpleJoin = simpleJoin;
    }

    @Override
    public int index() {
        return index;
    }

    void setFaceJoinState(Direction face, CornerJoinFaceStateImpl state) {
        faceJoinIndex[face.ordinal()] = (byte) state.ordinal();
    }
    //PERF: values()
    @Override
    public CornerJoinFaceStateImpl getFaceJoinState(Direction face) {
        return CornerJoinFaceStateImpl.values()[faceJoinIndex[face.ordinal()]];
    }
}