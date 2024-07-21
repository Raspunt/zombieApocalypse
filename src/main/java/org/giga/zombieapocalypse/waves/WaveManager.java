package org.giga.zombieapocalypse.waves;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.giga.zombieapocalypse.Config;
import org.giga.zombieapocalypse.ZombieApocalypse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveManager {
    private long ticksUntilNextWave;
    private final List<ZombieWave> activeWaves = new ArrayList<>();
    private final Random random = new Random();

    public void initialize(MinecraftServer server) {
        this.ticksUntilNextWave = Config.waveInterval;
    }

    public void onServerTick(MinecraftServer server) {

        // Проверка, нужно ли запускать новую волну
        if (this.ticksUntilNextWave <= 0) {
            ZombieApocalypse.LOGGER.info("[ZombieWaves] TRYING TO START");
            ticksUntilNextWave = Config.waveInterval;
            ServerPlayerEntity randomPlayer = getRandomPlayer(server);
            if (randomPlayer != null) {
                ZombieApocalypse.LOGGER.info("[ZombieWaves] startwave on player {}", randomPlayer.getName());
                startZombieWave(randomPlayer);
            }
            this.ticksUntilNextWave = Config.waveInterval;
        }
        this.ticksUntilNextWave--;


        // Обновление активных волн
        List<ZombieWave> finishedWaves = new ArrayList<>();
        for (ZombieWave wave : activeWaves) {
            wave.update();
            if (wave.isFinished()) {
                ZombieApocalypse.LOGGER.info("[ZombieWaves] Wave is Finished");
                finishedWaves.add(wave);
            }
        }
        activeWaves.removeAll(finishedWaves);
    }

    private ServerPlayerEntity getRandomPlayer(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        if (players.isEmpty()) {
            return null;
        }
        return players.get(random.nextInt(players.size()));
    }

    private void startZombieWave(ServerPlayerEntity player) {
        World world = player.getWorld();
        BlockPos playerPos = player.getBlockPos();
        List<BlockPos> spawnPositions = ZombieSpawner.getSpawnPositionsAround(playerPos, Config.spawnRadius, Config.safeRadius, 10);
        ZombieWave wave = new ZombieWave(world, spawnPositions, player);
        activeWaves.add(wave);
        ZombieApocalypse.LOGGER.info("[ZombieWaves] WAVE STARTED");
    }

    public long getTicksUntilNextWave() {
        return ticksUntilNextWave;
    }

    public void setTicksUntilNextWave(long ticks) {
        this.ticksUntilNextWave = ticks;
    }
}
