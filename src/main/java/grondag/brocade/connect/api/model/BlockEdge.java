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

package grondag.brocade.connect.api.model;

import static grondag.brocade.connect.api.model.ClockwiseRotation.ROTATE_180;
import static grondag.brocade.connect.api.model.ClockwiseRotation.ROTATE_270;
import static grondag.brocade.connect.api.model.ClockwiseRotation.ROTATE_90;
import static grondag.brocade.connect.api.model.ClockwiseRotation.ROTATE_NONE;
import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apiguardian.api.API;

import grondag.brocade.connect.impl.helper.BlockEdgeHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Vec3i;

/**
 * Defines the twelve edges of a block and the relative position of
 * neighboring blocks diagonally adjacent to those edges.
 */
@API(status = STABLE)
public enum BlockEdge {
    UP_EAST(Direction.UP, Direction.EAST, ROTATE_NONE), // Z AXIS
    UP_WEST(Direction.UP, Direction.WEST, ROTATE_270), // Z AXIS
    UP_NORTH(Direction.UP, Direction.NORTH, ROTATE_180), // X AXIS
    UP_SOUTH(Direction.UP, Direction.SOUTH, ROTATE_90), // X AXIS
    NORTH_EAST(Direction.NORTH, Direction.EAST, ROTATE_NONE), // Y AXIS
    NORTH_WEST(Direction.NORTH, Direction.WEST, ROTATE_270), // Y AXIS
    SOUTH_EAST(Direction.SOUTH, Direction.EAST, ROTATE_90), // Y AXIS
    SOUTH_WEST(Direction.SOUTH, Direction.WEST, ROTATE_180), // Y AXIS
    DOWN_EAST(Direction.DOWN, Direction.EAST, ROTATE_90), // Z AXIS
    DOWN_WEST(Direction.DOWN, Direction.WEST, ROTATE_180), // Z AXIS
    DOWN_NORTH(Direction.DOWN, Direction.NORTH, ROTATE_270), // X AXIS
    DOWN_SOUTH(Direction.DOWN, Direction.SOUTH, ROTATE_NONE); // X AXIS

    public final Direction face1;
    public final Direction face2;

    /**
     * Axis parallel to both faces.  
     * Equivalently, the axis orthogonal to the axes of both faces.
     */
    public final Direction.Axis parallelAxis;

    /**
     * Used to position models like stairs/wedges. Representation rotation around
     * the parallel axis such that face1 and face2 are most occluded. Based on
     * "default" model having Y axis and occluding north and east faces.
     */
    public final ClockwiseRotation rotation;
    
    public final Vec3i vector;
    
    /**
     * Ordinal sequence that includes all faces, corner and far corners. 
     * Used to index them in a mixed array.
     */
    @API(status = INTERNAL)
    public final int superOrdinal;
    
    @API(status = INTERNAL)
    public final int superOrdinalBit;

    /**
     * Will be null if not a horizontal edge.
     */
    @Nullable
    public final HorizontalEdge horizontalEdge;
    
    private BlockEdge(Direction face1, Direction face2, ClockwiseRotation rotation) {
        this.face1 = face1;
        this.face2 = face2;
        this.rotation = rotation;
        superOrdinal = 6 + this.ordinal();
        superOrdinalBit = 1 << superOrdinal;
        boolean hasX = (face1.getAxis() == Direction.Axis.X || face2.getAxis() == Direction.Axis.X);
        boolean hasY = (face1.getAxis() == Direction.Axis.Y || face2.getAxis() == Direction.Axis.Y);
        parallelAxis = hasX && hasY ? Direction.Axis.Z : hasX ? Direction.Axis.Y : Direction.Axis.X;

        Vec3i v1 = face1.getVector();
        Vec3i v2 = face2.getVector();
        vector = new Vec3i(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());

        if (face1.getAxis() == Axis.Y || face2.getAxis() == Axis.Y)
            horizontalEdge = null;
        else
            horizontalEdge = HorizontalEdge.find(HorizontalFace.find(face1), HorizontalFace.find(face2));
    }

    public static final int COUNT = BlockEdgeHelper.COUNT;
    
    /**
     * Will be null if the inputs do not specify an edge.
     */
    @Nullable
    public static BlockEdge find(Direction face1, Direction face2) {
        return BlockEdgeHelper.find(face1, face2);
    }

    public static BlockEdge find(Direction.Axis axis, ClockwiseRotation rotation) {
        return BlockEdgeHelper.find(axis, rotation);
    }
    
    public static final BlockEdge fromOrdinal(int ordinal) {
        return BlockEdgeHelper.fromOrdinal(ordinal);
    }
    
    public static void forEach(Consumer<BlockEdge> consumer) {
        BlockEdgeHelper.forEach(consumer);
    }
}
