package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.world.BlockNeighbors;
import grondag.brocade.connect.impl.CornerJoinStateSelector;
import net.minecraft.util.math.Direction;

public interface CornerJoinState {
    int ordinal();

    CornerJoinFaceState faceState(Direction face);
    
    SimpleJoinState simpleJoin();
    
    static int ordinalFromWorld(BlockNeighbors tests) {
        return CornerJoinStateSelector.ordinalFromWorld(tests);
    }

    static CornerJoinState fromWorld(BlockNeighbors tests) {
        return CornerJoinStateSelector.fromWorld(tests);
    }
    
    static CornerJoinState fromOrdinal(int ordinal) {
        return CornerJoinStateSelector.fromOrdinal(ordinal);
    }
}