package io.github.mcengine.extension.api.artificialintelligence.example.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Handles /aiapiexample command logic.
 */
public class APICommand implements CommandExecutor {

    /**
     * Executes the /aiapiexample command.
     *
     * @param sender  The source of the command.
     * @param command The command that was executed.
     * @param label   The alias used.
     * @param args    The command arguments.
     * @return true if the command was handled successfully.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§aExampleAPI command executed!");
        return true;
    }
}
