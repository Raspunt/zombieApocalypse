package org.giga.zombieapocalypse.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import org.giga.zombieapocalypse.waves.WaveManager;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static final List<ICommand> commands = new ArrayList<>();

    public static void registerCommands(WaveManager waveManager) {
        commands.add(new WaveInfoCommand(waveManager));
        commands.add(new SpawnZombieCommand(waveManager));
    }

    public static void registerAll(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (ICommand command : commands) {
            command.register(dispatcher);
        }
    }

}
