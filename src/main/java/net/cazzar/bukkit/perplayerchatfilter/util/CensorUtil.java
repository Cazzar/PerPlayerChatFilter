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

import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class CensorUtil {
    public static String censorString(Player player, String message) {
        for (String word : PerPlayerChatFilter.getInstance().getPlayerData(player).getCensoredWords()) {
            String replacement = StringUtils.repeat("*", word.length());
            message = message.replaceAll("(?i)" + word, replacement);
        }

        return message;
    }
}
