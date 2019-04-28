package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.state.SimpleJoinState;
import grondag.brocade.connect.api.world.BlockNeighbors;
import net.minecraft.util.math.Direction;

public class SimpleJoinStateImpl implements SimpleJoinState {
    private final int joins;

    @Override
    public boolean isJoined(Direction face) {
        final int flag = 1 << face.ordinal();
        return (joins & flag) == flag;
    }

    @Override
    public int ordinal() {
        return (int) joins;
    }
    
    private SimpleJoinStateImpl(int joins) {
        this.joins = joins;
    }

    private static final Direction[] FACES = Direction.values();
    public static final int STATE_COUNT = 64; // 2^6
    private static final SimpleJoinStateImpl JOINS[] = new SimpleJoinStateImpl[STATE_COUNT];

    static {
        for(int i = 0; i < 64; i++) {
            JOINS[i] = new SimpleJoinStateImpl(i);
        }
    }
    
    public static SimpleJoinStateImpl fromOrdinal(int index) {
        return JOINS[index];
    }
    
    public static SimpleJoinStateImpl fromWorld(BlockNeighbors testResults) {
        return fromOrdinal(ordinalFromWorld(testResults));
    }
    
    public static int ordinalFromWorld(BlockNeighbors testResults) {
        byte j = 0;
        for (int i = 0; i < 6; i++) {
            if (testResults.result(FACES[i])) {
                j |= (1 << i);
            }
        }
        return j;
    }
}