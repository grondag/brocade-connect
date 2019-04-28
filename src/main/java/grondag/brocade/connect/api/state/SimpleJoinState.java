package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.block.BlockNeighbors;
import grondag.brocade.connect.impl.SimpleJoin;
import net.minecraft.util.math.Direction;

public interface SimpleJoinState {

    boolean isJoined(Direction face);

    int getIndex();

    public static SimpleJoinState get(int index) {
        return SimpleJoin.get(index);
    }
    
    public static SimpleJoinState get(BlockNeighbors testResults) {
        return SimpleJoin.get(testResults);
    }
    
    public static int getIndex(BlockNeighbors testResults) {
        return SimpleJoin.getIndex(testResults);
    }
}