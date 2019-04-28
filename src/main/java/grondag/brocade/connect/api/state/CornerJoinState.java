package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.block.NeighborBlocks;
import grondag.brocade.connect.impl.CornerJoinBlockStateSelector;
import net.minecraft.util.math.Direction;

public interface CornerJoinState {
    int index();

    CornerJoinFaceState getFaceJoinState(Direction face);
    
    public static int getIndex(NeighborBlocks tests) {
        return CornerJoinBlockStateSelector.getIndex(tests);
    }

    public static CornerJoinState get(NeighborBlocks tests) {
        return CornerJoinBlockStateSelector.get(tests);
    }
    
    public static CornerJoinState get(int index) {
        return CornerJoinBlockStateSelector.get(index);
    }
}