package fr.gardahaut.test1;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.BlockPlacementSettings;

public record Block(String type, Vector3d position, BlockPlacementSettings settings){}
