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

package net.cazzar.bukkit.perplayerchatfilter.packets;

import com.comphenix.protocol.events.*;
import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import net.cazzar.bukkit.perplayerchatfilter.util.CensorUtil;
import org.bukkit.plugin.Plugin;

public class PacketInterceptor extends PacketAdapter {
    public PacketInterceptor(Plugin plugin) {
        super(plugin, ConnectionSide.SERVER_SIDE, ListenerPriority.HIGH, 0x03);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        switch (event.getPacketID()) {
            case 0x03:
                PacketContainer packet = event.getPacket();
                String message = packet.getSpecificModifier(String.class).read(0);

                message = (PerPlayerChatFilter.getInstance().censoredForPlayer(event.getPlayer()))
                        ? CensorUtil.censorString(event.getPlayer(), message) : message;

                packet.getSpecificModifier(String.class).write(0, message);
                break;
        }
    }
}
