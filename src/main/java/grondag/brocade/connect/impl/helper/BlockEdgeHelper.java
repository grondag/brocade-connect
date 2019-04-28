/*******************************************************************************
 * Copyright 2019 grondag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.brocade.connect.impl.helper;

import static org.apiguardian.api.API.Status.INTERNAL;

import java.util.function.Consumer;

import org.apiguardian.api.API;

import grondag.brocade.connect.api.model.BlockEdge;
import grondag.brocade.connect.api.model.ClockwiseRotation;
import net.minecraft.util.math.Direction;

@API(status = INTERNAL)
public abstract class BlockEdgeHelper {
    private BlockEdgeHelper() {}
    
    private static final BlockEdge[] VALUES = BlockEdge.values();
    public static final int COUNT = VALUES.length;
    private static final BlockEdge[][] CORNER_LOOKUP = new BlockEdge[6][6];

    /** used to look up by axis and rotation */
    private static final BlockEdge[][] MODEL_LOOKUP = new BlockEdge[3][4];

    static {
        for (BlockEdge corner : VALUES) {
            CORNER_LOOKUP[corner.face1.ordinal()][corner.face2.ordinal()] = corner;
            CORNER_LOOKUP[corner.face2.ordinal()][corner.face1.ordinal()] = corner;
            MODEL_LOOKUP[corner.parallelAxis.ordinal()][corner.rotation.ordinal()] = corner;
        }
    }
    
    public static BlockEdge find(Direction face1, Direction face2) {
        return CORNER_LOOKUP[face1.ordinal()][face2.ordinal()];
    }

    public static BlockEdge find(Direction.Axis axis, ClockwiseRotation rotation) {
        return MODEL_LOOKUP[axis.ordinal()][rotation.ordinal()];
    }
    
    public static final BlockEdge fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    public static void forEach(Consumer<BlockEdge> consumer) {
        for(BlockEdge val: VALUES) {
            consumer.accept(val);
        }
    }
}
