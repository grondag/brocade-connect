package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.world.BlockNeighbors;
import grondag.brocade.connect.impl.SimpleJoinStateImpl;
import net.minecraft.util.math.Direction;

public interface SimpleJoinState {

    boolean isJoined(Direction face);

    int ordinal();

    public static SimpleJoinState fromOrdinal(int ordinal) {
        return SimpleJoinStateImpl.fromOrdinal(ordinal);
    }
    
    public static SimpleJoinState fromWorld(BlockNeighbors neighbors) {
        return SimpleJoinStateImpl.fromWorld(neighbors);
    }
    
    public static int ordinalFromWorld(BlockNeighbors neighbors) {
        return SimpleJoinStateImpl.ordinalFromWorld(neighbors);
    }
}