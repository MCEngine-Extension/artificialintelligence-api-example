package io.github.mcengine.extension.api.example;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.extension.logger.MCEngineExtensionLogger;
import io.github.mcengine.api.artificialintelligence.extension.addon.IMCEngineArtificialIntelligenceAddOn;

import io.github.mcengine.extension.api.example.APICommand;
import io.github.mcengine.extension.api.example.APIListener;
import io.github.mcengine.extension.api.example.APITabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Main class for the ExampleAPI.
 * <p>
 * Registers the {@code /aiapiexample} command and event listeners.
 */
public class ExampleAPI implements IMCEngineArtificialIntelligenceAddOn {

    /**
     * Initializes the ExampleAPI.
     * Called automatically by the MCEngine core plugin.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onLoad(Plugin plugin) {
        MCEngineExtensionLogger logger = new MCEngineExtensionLogger(plugin, "API", "ArtificialIntelligenceExampleAPI");

        try {
            // Register event listener
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(new APIListener(plugin, logger), plugin);

            // Reflectively access Bukkit's CommandMap
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Define the /aiapiexample command
            Command aiApiExampleCommand = new Command("aiapiexample") {

                /**
                 * Handles command execution for /aiapiexample.
                 */
                private final APICommand handler = new APICommand();

                /**
                 * Handles tab-completion for /aiapiexample.
                 */
                private final APITabCompleter completer = new APITabCompleter();

                @Override
                public boolean execute(CommandSender sender, String label, String[] args) {
                    return handler.onCommand(sender, this, label, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                    return completer.onTabComplete(sender, this, alias, args);
                }
            };

            aiApiExampleCommand.setDescription("AI API example command.");
            aiApiExampleCommand.setUsage("/aiapiexample");

            // Dynamically register the /aiapiexample command
            commandMap.register(plugin.getName().toLowerCase(), aiApiExampleCommand);

            logger.info("Enabled successfully.");
        } catch (Exception e) {
            logger.warning("Failed to initialize ExampleAPI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisload(Plugin plugin) {
        // No specific unload logic
    }

    @Override
    public void setId(String id) {
        MCEngineCoreApi.setId("mcengine-artificialintelligence-api-example");
    }
}
