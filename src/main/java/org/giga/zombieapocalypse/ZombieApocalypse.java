package org.giga.zombieapocalypse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.giga.zombieapocalypse.waves.WaveManager;

public class ZombieApocalypse implements ModInitializer {

    private static WaveManager waveManager;

    @Override
    public void onInitialize() {

        Config.loadConfig();
        waveManager = new WaveManager();
        ServerTickEvents.END_SERVER_TICK.register(server -> waveManager.onServerTick(server));

    }


}
