package org.giga.zombieapocalypse.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.giga.zombieapocalypse.waves.WaveManager;

import static net.minecraft.server.command.CommandManager.literal;

public class WaveInfoCommand implements ICommand {
    private final WaveManager waveManager;

    public WaveInfoCommand(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    public void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher,registryAccess, environment) -> {
            dispatcher.register(literal("waveinfo")
                    .executes(context -> {
                        return action(context);
                    })
            );
        });
}