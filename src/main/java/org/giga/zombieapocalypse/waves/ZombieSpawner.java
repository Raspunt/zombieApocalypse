package org.giga.zombieapocalypse.waves;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieSpawner {

    public static List<BlockPos> getSpawnPositionsAround(BlockPos center, int spawnRadius, int safeRadius, int maxSpawns) {
        List<BlockPos> spawnPositions = new ArrayList<>();
        Random random = new Random();
        World world = null; // Нужно будет передать World в этот метод, если у вас есть доступ к нему

        while (spawnPositions.size() < maxSpawns) {
            int xOffset = random.nextInt(spawnRadius * 2) - spawnRadius;
            int zOffset = random.nextInt(spawnRadius * 2) - spawnRadius;
            BlockPos potentialPos = center.add(xOffset, 0, zOffset);

            if (center.isWithinDistance(potentialPos, safeRadius)) {
                continue;
            }

            // Находим ближайшую доступную позицию по вертикали
            potentialPos = findGround(world, potentialPos);

            if (isSpawnPositionValid(world, potentialPos)) {
                spawnPositions.add(potentialPos);
            }
        }

        return spawnPositions;
    }

    private static BlockPos findGround(World world, BlockPos pos) {
        // Находим ближайшую свободную позицию по вертикали вниз
        while (pos.getY() > 0 && !world.getBlockState(pos).isSolidBlock(world, pos)) {
            pos = pos.down();
        }

        // Поднимаемся вверх до первой доступной позиции
        while (pos.getY() < world.getTopY() && world.getBlockState(pos.up()).isAir()) {
            pos = pos.up();
        }

        return pos;
    }

    private static boolean isSpawnPositionValid(World world, BlockPos pos) {
        BlockState blockStateBelow = world.getBlockState(pos.down());
        boolean isGroundSolid = blockStateBelow.isSolidBlock(world, pos.down());

        boolean isSpaceAboveClear = world.isAir(pos) && world.isAir(pos.up());

        return isGroundSolid && isSpaceAboveClear;
    }
}
