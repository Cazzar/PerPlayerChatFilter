/*
 * {one line to give the program's name and a brief idea of what it does
 * Copyright (C) 2013 cazzar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.bukkit.perplayerchatfilter.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter.getInstance;

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
        if (!(sender instanceof Player)) {
            if (args.length == 0)
                return false;
            else {
                if (args[0].equals("add")) {
                    String[] words = Arrays.copyOfRange(args, 1, args.length);
                    for (String word : words)
                        if (!getInstance().addCensoredWord(word))
                            sender.sendMessage(String.format("\"%s\" is already in the default censor list!", word));
                        else getInstance().saveConfig();

                } else if (args[0].equals("rem") || args[0].equals("remove")) {
                    String[] words = Arrays.copyOfRange(args, 1, args.length);
                    for (String word : words)
                        if (!getInstance().removeCensoredWord(word))
                            sender.sendMessage(String.format("\"%s\" is not in the default censor list!", word));
                        else getInstance().saveConfig();
                }

                return true;
            }
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (getInstance().censoredForPlayer(player)) {
                getInstance().removePlayer(player);
                player.sendMessage("Disabled censoring for you!");
            } else {
                getInstance().addPlayer(player);
                player.sendMessage("Enabled censoring for you!");
            }
            return true;
        }

        if (args[0].toLowerCase().equals("add")) {
            String[] words = Arrays.copyOfRange(args, 1, args.length);

            for (String word : words)
                if (!getInstance().getPlayerData(player).addCensoredWord(word))
                    sender.sendMessage(String.format("\"%s\" is already in the censor list!", word));
                else getInstance().saveConfig();

        } else if (args[0].toLowerCase().equals("rem") || args[0].toLowerCase().equals("remove")) {
            String[] words = Arrays.copyOfRange(args, 1, args.length);
            for (String word : words)
                if (!getInstance().getPlayerData(player).removeCensoredWord(word))
                    sender.sendMessage(String.format("\"%s\" is not in the censor list!", word));
                else getInstance().saveConfig();
        }
        return true;
    }
}
