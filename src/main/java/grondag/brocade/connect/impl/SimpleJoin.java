package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.SimpleJoinState;
import net.minecraft.util.math.Direction;

public class SimpleJoin implements SimpleJoinState {

    private final byte joins;
    private static final Direction[] FACES = Direction.values();

    public static final int STATE_COUNT = 64; // 2^6

    public <V> SimpleJoin(NeighborBlocksImpl<V>.NeighborTestResults testResults) {
        this.joins = getIndex(testResults);
    }

    public static <V> byte getIndex(NeighborBlocksImpl<V>.NeighborTestResults testResults) {
        byte j = 0;
        for (int i = 0; i < 6; i++) {
            if (testResults.result(FACES[i])) {
                j |= NeighborBlocksImpl.FACE_FLAGS[i];
            }
        }
        return j;
    }

    public SimpleJoin(boolean up, boolean down, boolean east, boolean west, boolean north, boolean south) {
        byte j = 0;
        if (up)
            j |= NeighborBlocksImpl.FACE_FLAGS[Direction.UP.ordinal()];
        if (down)
            j |= NeighborBlocksImpl.FACE_FLAGS[Direction.DOWN.ordinal()];
        if (east)
            j |= NeighborBlocksImpl.FACE_FLAGS[Direction.EAST.ordinal()];
        if (west)
            j |= NeighborBlocksImpl.FACE_FLAGS[Direction.WEST.ordinal()];
        if (north)
            j |= NeighborBlocksImpl.FACE_FLAGS[Direction.NORTH.ordinal()];
        if (south)
            j |= NeighborBlocksImpl.FACE_FLAGS[Direction.SOUTH.ordinal()];
        this.joins = j;
    }

    public SimpleJoin(int index) {
        this.joins = (byte) index;
    }

    @Override
    public boolean isJoined(Direction face) {
        return (joins & NeighborBlocksImpl.FACE_FLAGS[face.ordinal()]) == NeighborBlocksImpl.FACE_FLAGS[face.ordinal()];
    }

    @Override
    public int getIndex() {
        return (int) joins;
    }
}