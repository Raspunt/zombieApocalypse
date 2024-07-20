package org.giga.zombieapocalypse.waves;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.giga.zombieapocalypse.Config;

import java.util.ArrayList;
import java.util.List;

public class WaveManager {
    private int tickCounter = 0;
    private List<ZombieWave> activeWaves = new ArrayList<>();

    public void onServerTick(MinecraftServer server) {
        tickCounter++;

        if (tickCounter >= Config.waveInterval) {
            tickCounter = 0;
            //TODO рандомный игрог бляеть нужно реализовать
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                startZombieWave(player);
            }
        }

        List<ZombieWave> finishedWaves = new ArrayList<>();
        for (ZombieWave wave : activeWaves) {
            wave.update();
            if (wave.isFinished()) {
                finishedWaves.add(wave);
            }
        }
        activeWaves.removeAll(finishedWaves);
    }

    private void startZombieWave(ServerPlayerEntity player) {
        World world = player.getWorld();
        BlockPos playerPos = player.getBlockPos();
        List<BlockPos> spawnPositions = ZombieSpawner.getSpawnPositionsAround(playerPos, Config.spawnRadius, Config.safeRadius, 10);
        ZombieWave wave = new ZombieWave(world, spawnPositions, player);
        activeWaves.add(wave);
    }
}