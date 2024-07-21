package org.giga.zombieapocalypse.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.giga.zombieapocalypse.ZombieApocalypse;
import org.giga.zombieapocalypse.waves.WaveManager;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public void buildCommands(WaveManager waveManager) {
        ZombieApocalypse.LOGGER.info("[ZombieWaves] Registration Commands");
        this.commands.add(new WaveInfoCommand(waveManager));
        this.commands.add(new SetWaveCommand(waveManager));
    }

    public void registerCommands() {
        for (ICommand command : commands) {
            CommandRegistrationCallback.EVENT.register(command::register);
        }
    }
}


