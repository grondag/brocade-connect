package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.CornerJoinState;
import net.minecraft.util.math.Direction;

public class CornerJoinBlockState implements CornerJoinState {
    private final int index;

    /** join state considering only direct neighbors */
    public final SimpleJoin simpleJoin;

    private byte faceJoinIndex[] = new byte[6];

    CornerJoinBlockState(int index, SimpleJoin simpleJoin) {
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