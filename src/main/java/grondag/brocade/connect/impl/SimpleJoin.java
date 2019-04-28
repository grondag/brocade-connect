package grondag.brocade.connect.impl;

import grondag.brocade.connect.api.block.BlockNeighbors;
import grondag.brocade.connect.api.state.SimpleJoinState;
import net.minecraft.util.math.Direction;

//PERF: make this immutable
public class SimpleJoin implements SimpleJoinState {
    private static final Direction[] FACES = Direction.values();
    public static final int STATE_COUNT = 64; // 2^6
    private static final SimpleJoin JOINS[] = new SimpleJoin[STATE_COUNT];

    static {
        for(int i = 0; i < 64; i++) {
            JOINS[i] = new SimpleJoin(i);
        }
    }
    private final int joins;

    private SimpleJoin(int joins) {
        this.joins = joins;
    }

    public static SimpleJoin get(int index) {
        return JOINS[index];
    }
    
    public static SimpleJoin get(BlockNeighbors testResults) {
        return get(getIndex(testResults));
    }
    
    public static int getIndex(BlockNeighbors testResults) {
        byte j = 0;
        for (int i = 0; i < 6; i++) {
            if (testResults.result(FACES[i])) {
                j |= (1 << i);
            }
        }
        return j;
    }

    @Override
    public boolean isJoined(Direction face) {
        final int flag = 1 << face.ordinal();
        return (joins & flag) == flag;
    }

    @Override
    public int getIndex() {
        return (int) joins;
    }
}