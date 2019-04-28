package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.block.NeighborBlocks;
import grondag.brocade.connect.impl.SimpleJoin;
import net.minecraft.util.math.Direction;

public interface SimpleJoinState {

    boolean isJoined(Direction face);

    int getIndex();

    public static SimpleJoinState get(int index) {
        return SimpleJoin.get(index);
    }
    
    public static SimpleJoinState get(NeighborBlocks testResults) {
        return SimpleJoin.get(testResults);
    }
    
    public static int getIndex(NeighborBlocks testResults) {
        return SimpleJoin.getIndex(testResults);
    }
}