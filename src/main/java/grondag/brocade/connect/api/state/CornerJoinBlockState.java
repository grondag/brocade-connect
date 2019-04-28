package grondag.brocade.connect.api.state;

import grondag.brocade.connect.api.block.BlockNeighbors;
import grondag.brocade.connect.impl.CornerJoinBlockStateSelector;
import grondag.brocade.connect.impl.SimpleJoin;
import net.minecraft.util.math.Direction;

public interface CornerJoinBlockState {
    int index();

    CornerJoinFaceState getFaceJoinState(Direction face);
    
    SimpleJoin simpleJoin();
    
    static int getIndex(BlockNeighbors tests) {
        return CornerJoinBlockStateSelector.indexOf(tests);
    }

    static CornerJoinBlockState get(BlockNeighbors tests) {
        return CornerJoinBlockStateSelector.get(tests);
    }
    
    static CornerJoinBlockState get(int index) {
        return CornerJoinBlockStateSelector.get(index);
    }
}