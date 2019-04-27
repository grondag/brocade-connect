package grondag.brocade.connect.api.state;

import net.minecraft.util.math.Direction;

public interface CornerJoinState {

    int getIndex();

    //PERF: values()
    CornerJoinFaceState getFaceJoinState(Direction face);

}