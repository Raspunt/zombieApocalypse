package org.giga.zombieapocalypse.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

public class SpawnZombieCommand implements ICommand {
    @Override
    public void register() {

    }

    @Override
    public int action(CommandContext<ServerCommandSource> context) {
        return 0;
    }
}
