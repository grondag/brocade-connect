package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.block.NeighborBlocks;
import net.minecraft.util.math.Direction;

public class CornerJoinBlockStateSelector {
    // STATIC MEMBERS START
    private static final Direction[] FACES = Direction.values();
    public static final int BLOCK_JOIN_STATE_COUNT = 20115;
    private static final CornerJoinBlockStateImpl BLOCK_JOIN_STATES[] = new CornerJoinBlockStateImpl[BLOCK_JOIN_STATE_COUNT];
    private static final CornerJoinBlockStateSelector BLOCK_JOIN_SELECTOR[] = new CornerJoinBlockStateSelector[64];

    static {
        int firstIndex = 0;

        for (int i = 0; i < 64; i++) {
            SimpleJoin baseJoin = SimpleJoin.get(i);
            BLOCK_JOIN_SELECTOR[i] = new CornerJoinBlockStateSelector(baseJoin, firstIndex);

            for (int j = 0; j < BLOCK_JOIN_SELECTOR[i].getStateCount(); j++) {
                BLOCK_JOIN_STATES[firstIndex + j] = BLOCK_JOIN_SELECTOR[i].getJoinFromIndex(firstIndex + j);
            }

            firstIndex += BLOCK_JOIN_SELECTOR[i].getStateCount();
        }
    }

    public static int getIndex(NeighborBlocks tests) {
        SimpleJoin baseJoin = SimpleJoin.get(tests);
        return BLOCK_JOIN_SELECTOR[baseJoin.getIndex()].getIndexFromNeighbors(tests);
    }

    public static CornerJoinBlockStateImpl get(NeighborBlocks tests) {
        return get(getIndex(tests));
    }
    
    public static CornerJoinBlockStateImpl get(int index) {
        return BLOCK_JOIN_STATES[index];
    }

    // STATIC MEMBERS END

    private final int firstIndex;
    private final SimpleJoin simpleJoin;

    private CornerJoinFaceSelector faceSelector[] = new CornerJoinFaceSelector[6];

    private CornerJoinBlockStateSelector(SimpleJoin baseJoinState, int firstIndex) {
        this.firstIndex = firstIndex;
        this.simpleJoin = baseJoinState;
        for (int i = 0; i < 6; i++) {
            faceSelector[i] = new CornerJoinFaceSelector(FACES[i], baseJoinState);
        }
    }

    private int getStateCount() {
        int count = 1;
        for (int i = 0; i < 6; i++) {
            count *= faceSelector[i].faceCount;
        }
        return count;
    }

    private <V> int getIndexFromNeighbors(NeighborBlocks tests) {
        int index = 0;
        int shift = 1;
        for (int i = 0; i < 6; i++) {
            if (faceSelector[i].faceCount > 1) {
                index += shift * faceSelector[i].getIndexFromNeighbors(tests);
                shift *= faceSelector[i].faceCount;
            }
        }
        return index + firstIndex;
    }

    private CornerJoinBlockStateImpl getJoinFromIndex(int index) {
        int shift = 1;
        int localIndex = index - firstIndex;

        CornerJoinBlockStateImpl retVal = new CornerJoinBlockStateImpl(index, simpleJoin);

        for (int i = 0; i < 6; i++) {
            final Direction face = FACES[i];
            if (faceSelector[i].faceCount == 1) {
                retVal.setFaceJoinState(face, faceSelector[i].getFaceJoinFromIndex(0));
            } else {
                int faceIndex = (localIndex / shift) % faceSelector[i].faceCount;
                retVal.setFaceJoinState(face, faceSelector[i].getFaceJoinFromIndex(faceIndex));
                shift *= faceSelector[i].faceCount;
            }
        }

        return retVal;
    }
}
