package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.CornerJoinFaceState;
import net.minecraft.util.math.Direction;

public class CornerJoinFaceSelector {
    public final Direction face;

    public final int faceCount;
    public final CornerJoinFaceState[] faceJoins;
    public final int[] joinIndex = new int[48];

    public CornerJoinFaceSelector(Direction face, SimpleJoin baseJoinState) {
        this.face = face;
        faceJoins = CornerJoinFaceState.find(face, baseJoinState).getSubStates();
        this.faceCount = faceJoins.length;

        for (int i = 0; i < faceCount; i++) {
            joinIndex[faceJoins[i].ordinal()] = i;
        }
    }

    public <V> int getIndexFromNeighbors(NeighborBlocksImpl<V>.NeighborTestResults tests) {
        return joinIndex[CornerJoinFaceState.find(face, tests).ordinal()];
    }

    public CornerJoinFaceState getFaceJoinFromIndex(int index) {
        return faceJoins[index];
    }
}
