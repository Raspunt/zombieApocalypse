package org.giga.zombieapocalypse.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.giga.zombieapocalypse.ZombieApocalypse;
import org.giga.zombieapocalypse.waves.WaveManager;


public class WaveInfoCommand implements ICommand {

    protected String literal = "waveinfo";

    protected WaveManager waveManager;

    public WaveInfoCommand(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>literal(this.literal)
                .executes(this::action)
        );
    }

    private int action(CommandContext<ServerCommandSource> context) {
        ZombieApocalypse.LOGGER.info("Ввёл команду");
        return 0;
    }
}