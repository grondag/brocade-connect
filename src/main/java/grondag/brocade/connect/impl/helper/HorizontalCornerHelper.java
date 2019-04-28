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

import grondag.brocade.connect.api.model.HorizontalCorner;
import grondag.brocade.connect.api.model.HorizontalFace;

@API(status = INTERNAL)
public abstract class HorizontalCornerHelper {
    private HorizontalCornerHelper() {}
    
    private static final HorizontalCorner[] VALUES = HorizontalCorner.values();
    public static final int COUNT = VALUES.length;

    private static final HorizontalCorner[][] HORIZONTAL_CORNER_LOOKUP = new HorizontalCorner[4][4];

    static {
        for (HorizontalCorner corner : HorizontalCorner.values()) {
            HORIZONTAL_CORNER_LOOKUP[corner.face1.ordinal()][corner.face2.ordinal()] = corner;
            HORIZONTAL_CORNER_LOOKUP[corner.face2.ordinal()][corner.face1.ordinal()] = corner;
        }
    }
    
    public static HorizontalCorner find(HorizontalFace face1, HorizontalFace face2) {
        return HORIZONTAL_CORNER_LOOKUP[face1.ordinal()][face2.ordinal()];
    }
    
    public static HorizontalCorner fromOrdinal(int ordinal) {
        return VALUES[ordinal];
    }
    
    public static void forEach(Consumer<HorizontalCorner> consumer) {
        for(HorizontalCorner val: VALUES) {
            consumer.accept(val);
        }
    }
}
