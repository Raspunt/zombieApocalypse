package org.giga.zombieapocalypse.waves;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
            ZombieEntity zombie = new ZombieEntity(world);
            zombie.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            world.spawnEntity(zombie);
            zombies.add(zombie);
        }
    }

    public void update() {
        for (Entity zombie : zombies) {
            if (zombie.isAlive()) {
                ((ZombieEntity) zombie).getNavigation().startMovingTo(targetPlayer, 1.0);
            }
        }
    }

    public boolean isFinished() {
        return zombies.stream().noneMatch(Entity::isAlive);
    }
}