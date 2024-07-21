package org.giga.zombieapocalypse.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.giga.zombieapocalypse.waves.WaveManager;

public class SetWaveCommand implements ICommand {
    private final WaveManager waveManager;

    public SetWaveCommand(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("apocsetwave")
                .then(CommandManager.argument("ticks", IntegerArgumentType.integer())
                        .executes(this::setWave)));
    }

    private int setWave(CommandContext<ServerCommandSource> context) {
        int ticks = IntegerArgumentType.getInteger(context, "ticks");
        waveManager.setTicksUntilNextWave(ticks);
        context.getSource().sendFeedback(() -> Text.literal("Set ticks until next wave to: " + ticks), false);
        return 1;
    }
}
