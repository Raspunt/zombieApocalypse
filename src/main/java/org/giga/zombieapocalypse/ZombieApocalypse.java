package org.giga.zombieapocalypse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.giga.zombieapocalypse.commands.CommandManager;
import org.giga.zombieapocalypse.process.TickTaskManager;
import org.giga.zombieapocalypse.waves.WaveManager;

public class ZombieApocalypse implements ModInitializer {
    public static final String MOD_ID = "zombiewaves";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static TickTaskManager tickTaskManager;
    private static WaveManager waveManager;


    @Override
    public void onInitialize() {
        Config.loadConfig();
        waveManager = new WaveManager();

        CommandManager commandManager = new CommandManager();
        commandManager.buildCommands(waveManager);
        commandManager.registerCommands();


        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            tickTaskManager = new TickTaskManager(server);
            waveManager.initialize(server);

        });

        ServerTickEvents.END_SERVER_TICK.register(server -> waveManager.onServerTick(server));
    }


}
