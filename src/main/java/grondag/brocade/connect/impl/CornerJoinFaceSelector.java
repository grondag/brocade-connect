package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.block.BlockNeighbors;
import net.minecraft.util.math.Direction;

public class CornerJoinFaceSelector {
    public final Direction face;

    public final int faceCount;
    public final CornerJoinFaceStateImpl[] faceJoins;
    public final int[] joinIndex = new int[48];

    public CornerJoinFaceSelector(Direction face, SimpleJoin baseJoinState) {
        this.face = face;
        faceJoins = CornerJoinFaceStateImpl.find(face, baseJoinState).getSubStates();
        this.faceCount = faceJoins.length;

        for (int i = 0; i < faceCount; i++) {
            joinIndex[faceJoins[i].ordinal()] = i;
        }
    }

    public <V> int getIndexFromNeighbors(BlockNeighbors tests) {
        return joinIndex[CornerJoinFaceStateImpl.find(face, tests).ordinal()];
    }

    public CornerJoinFaceStateImpl getFaceJoinFromIndex(int index) {
        return faceJoins[index];
    }
}
