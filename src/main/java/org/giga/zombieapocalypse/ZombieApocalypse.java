package org.giga.zombieapocalypse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.giga.zombieapocalypse.commands.CommandManager;
import org.giga.zombieapocalypse.waves.WaveManager;

public class ZombieApocalypse implements ModInitializer {
    public static final String MOD_ID = "zombiewaves";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static WaveManager waveManager;

    @Override
    public void onInitialize() {
        Config.loadConfig();
        waveManager = new WaveManager();
        CommandManager commandManager = new CommandManager();
        commandManager.buildCommands(waveManager);
        commandManager.registerCommands();

        ServerTickEvents.END_SERVER_TICK.register(server -> waveManager.onServerTick(server));
    }


}
