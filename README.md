# Brocade Connections

This is a library for Minecraft mods using the Fabric toolchain.  It is meant to be distributed as a nested jar and introduces no additional dependencies.

The library functions are relative to a central block and the 26 neighbor blocks immediately adjacent to it. For the "block neighborhood" the library offers:

1) Declarations to describe the location and orientation of neighboring blocks relative to the central block.

2) Cached and lazily-evaluated access to block state and optionally, results of a provided state function and test function. 

3) Fast computation of "join" state for connected textures and shapes, either simple (six adjacent blocks) or complex (dependent on up to all twenty-six neighbors.)

4) Low- or no-allocation operation via threadLocal and pooled instances. 

## Devlopment Environment

```
repositories {
  maven {
    name = "grondag"
    url = "https://grondag-repo.appspot.com"
    credentials {
      username "guest"
      password ""
    }
  }
}

dependencies {
  // recommended but not required
  compile "org.apiguardian:apiguardian-api:1.0.0"
  compile "com.google.code.findbugs:jsr305:3.0.2"
  
  modCompile "grondag:brocade-connect:1.0.+"
  include "grondag:brocade-connect:1.0.+"
}
```

## Sample of Usage

Obtaining the state needed for a connected-texture model is straightforward.  If the connection depends only on block state, the test function is simply this:

```java
    static final BlockTest MATCHER = (b0, m0, b1, m1) -> b0 != null && b0.equals(b1);
```

Retrieving the connection state is then done with a single call:

```java
    CornerJoinState joinState = CornerJoinState.fromWorld(BlockNeighbors.threadLocal(blockView, pos, MATCHER));
```

Using the state to texture or transform models is not in the scope of this library. Future Brocade libraries will provide those features but a crude working example can be found in https://github.com/grondag/smart_chest.
