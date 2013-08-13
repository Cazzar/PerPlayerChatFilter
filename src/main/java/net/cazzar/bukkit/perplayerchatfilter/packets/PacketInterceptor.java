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

                message = (PerPlayerChatFilter.getInstance().uncensoredForPlayer(event.getPlayer())) ? CensorUtil.censorString(message) : message;

                packet.getSpecificModifier(String.class).write(0, message);
                break;
        }
    }
}
