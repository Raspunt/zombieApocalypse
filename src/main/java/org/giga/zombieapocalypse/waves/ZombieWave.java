package org.giga.zombieapocalypse.waves;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.GameMode;

import java.util.ArrayList;
import java.util.List;

public class ZombieWave {
    private final World world;
    private final List<BlockPos> spawnPositions;
    private final ServerPlayerEntity targetPlayer;
    private final List<Entity> zombies;

    public ZombieWave(World world, List<BlockPos> spawnPositions, ServerPlayerEntity targetPlayer) {
        this.world = world;
        this.spawnPositions = spawnPositions;
        this.targetPlayer = targetPlayer;
        this.zombies = new ArrayList<>();

        spawnZombies();
    }

    private void spawnZombies() {
        for (BlockPos pos : spawnPositions) {
            // Проверка на доступность спавн-позиции
            if (isSpawnPositionValid(pos)) {
                ZombieEntity zombie = new ZombieEntity(world);
                zombie.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                world.spawnEntity(zombie);
                zombies.add(zombie);
            }
        }
    }

    private boolean isSpawnPositionValid(BlockPos pos) {
        // Проверка, что место для спавна свободно и находится на твердом блоке
        BlockState blockStateBelow = world.getBlockState(pos.down());
        boolean isGroundSolid = blockStateBelow.isSolidBlock(world, pos.down());

        boolean isSpaceAboveClear = world.isAir(pos) && world.isAir(pos.up());

        return isGroundSolid && isSpaceAboveClear;
    }

    public void update() {
        if (targetPlayer.interactionManager.getGameMode() != GameMode.CREATIVE) {
            for (Entity zombie : zombies) {
                if (zombie.isAlive()) {
                    ((ZombieEntity) zombie).setTarget(targetPlayer);
                }
            }
        }
    }

    public boolean isFinished() {
        return zombies.stream().noneMatch(Entity::isAlive);
    }
}
