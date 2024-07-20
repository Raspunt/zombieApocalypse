package org.giga.zombieapocalypse.commands;


import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface ICommand {

    public void register(CommandDispatcher<ServerCommandSource> dispatcher);
}