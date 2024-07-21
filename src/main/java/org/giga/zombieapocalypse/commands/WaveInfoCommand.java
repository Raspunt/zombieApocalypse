package org.giga.zombieapocalypse.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.giga.zombieapocalypse.waves.WaveManager;


public class WaveInfoCommand implements ICommand {

    protected String literal = "apocinfowave";

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
        long ticksUntilNextWave = waveManager.getTicksUntilNextWave();
        context.getSource().sendFeedback(() -> Text.literal("Ticks until next wave: " + ticksUntilNextWave), false);
        return 1;
    }
}