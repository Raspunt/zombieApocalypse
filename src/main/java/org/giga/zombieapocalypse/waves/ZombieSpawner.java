package org.giga.zombieapocalypse.waves;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieSpawner {
    public static List<BlockPos> getSpawnPositionsAround(BlockPos center, int radius, int safeRadius, int count) {
        List<BlockPos> positions = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int dx, dz;
            do {
                dx = random.nextInt(radius * 2 + 1) - radius;
                dz = random.nextInt(radius * 2 + 1) - radius;
            } while (Math.sqrt(dx * dx + dz * dz) < safeRadius);
            positions.add(center.add(dx, 0, dz));
        }
        return positions;
    }
}