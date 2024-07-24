package org.giga.zombieapocalypse.waves;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.giga.zombieapocalypse.Config;
import org.giga.zombieapocalypse.ZombieApocalypse;
import org.giga.zombieapocalypse.process.TickTaskManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveManager {
    private TickTaskManager.TickTaskTimer zombieWaveTask;
    private final List<ZombieWave> activeWaves = new ArrayList<>();
    private final Random random = new Random();

    public void initialize(MinecraftServer server) {
        this.zombieWaveTask = ZombieApocalypse.tickTaskManager.timer(() -> {
            ZombieApocalypse.LOGGER.info("[ZombieWaves] TRYING TO START");
            ServerPlayerEntity randomPlayer = getRandomPlayer(server);
            if (randomPlayer != null) {
                ZombieApocalypse.LOGGER.info("[ZombieWaves] startwave on player {}", randomPlayer.getName());
                startZombieWave(randomPlayer);
            }
        }, Config.waveInterval);
    }

    // Поток Волн
    public void onServerTick(MinecraftServer server) {
        List<ZombieWave> finishedWaves = new ArrayList<>();
        for (ZombieWave wave : activeWaves) {
            wave.update();
            if (wave.isFinished()) {
                ZombieApocalypse.LOGGER.info("[ZombieWaves] Wave is Finished");
                finishedWaves.add(wave);
            }
        }
        activeWaves.removeAll(finishedWaves);
        this.zombieWaveTask.restart(); // Перезапускаем задачу
    }

    private @Nullable ServerPlayerEntity getRandomPlayer(@NotNull MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        if (players.isEmpty()) {
            return null;
        }
        return players.get(random.nextInt(players.size()));
    }

    private void startZombieWave(@NotNull ServerPlayerEntity player) {
        World world = player.getWorld();
        BlockPos playerPos = player.getBlockPos();
        List<BlockPos> spawnPositions = ZombieSpawner.getSpawnPositionsAround(playerPos, Config.spawnRadius, Config.safeRadius, 10);
        ZombieWave wave = new ZombieWave(world, spawnPositions, player);
        activeWaves.add(wave);
        ZombieApocalypse.LOGGER.info("[ZombieWaves] WAVE STARTED");
    }
}
