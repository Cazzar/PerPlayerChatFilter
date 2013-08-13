package net.cazzar.bukkit.perplayerchatfilter.util;

import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCensorToggle extends PluginCommand {
    public CommandCensorToggle() {
        super("censortoggle");
    }

    /**
     * Executes the command, returning its success
     *
     * @param sender Source object which is executing this command
     * @param args   All arguments passed to the command, split via ' '
     * @return true if the command was successful, otherwise false
     */
    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        if (PerPlayerChatFilter.getInstance().uncensoredForPlayer(player)) {
            PerPlayerChatFilter.getInstance().removePlayer(player);
            player.sendMessage("Enabled censoring for you!");
        } else {
            PerPlayerChatFilter.getInstance().addPlayer(player);
            player.sendMessage("Disabled censoring for you!");
        }
        return true;
    }
}
