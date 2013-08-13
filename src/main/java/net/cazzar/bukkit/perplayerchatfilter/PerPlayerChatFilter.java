package net.cazzar.bukkit.perplayerchatfilter;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.collect.Maps;
import net.cazzar.bukkit.perplayerchatfilter.configuration.PlayerConfiguration;
import net.cazzar.bukkit.perplayerchatfilter.packets.PacketInterceptor;
import net.cazzar.bukkit.perplayerchatfilter.util.CommandCensorToggle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class PerPlayerChatFilter extends JavaPlugin {
    public List<String> censorWords;
    private Map<String, PlayerConfiguration> players = Maps.newHashMap();
    private static PerPlayerChatFilter instance;

    public static PerPlayerChatFilter getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        censorWords = (List<String>) getConfig().getList("censor.words");

        ConfigurationSection section = getConfig().getConfigurationSection("censor.players");

        if (section != null) {
            Map<String, Object> playersTmp = getConfig().getConfigurationSection("censor.players").getValues(false);

            for (Map.Entry<String, Object> entry : playersTmp.entrySet())
                players.put(entry.getKey(), (PlayerConfiguration) entry.getValue());

        }
        getConfig().options().copyDefaults(true);
        saveConfig();
        protocolManager.addPacketListener(new PacketInterceptor(this));


        getCommand("filterme").setExecutor(new CommandCensorToggle());
    }

    public void addPlayer(Player player) {
        if (!players.containsKey(player.getName()))
            players.put(player.getName(), new PlayerConfiguration());

        System.out.println(players);
        players.get(player.getName()).enabled = true;
        saveConfig();
    }

    public void removePlayer(Player player) {
        if (!players.containsKey(player.getName()))
            players.put(player.getName(), new PlayerConfiguration());

        System.out.println(player.getName());
        System.out.println(players);
        players.get(player.getDisplayName()).enabled = false;
        saveConfig();
    }

    public boolean uncensoredForPlayer(Player player) {
        if (!players.containsKey(player.getName())) {
            players.put(player.getName(), new PlayerConfiguration());
            saveConfig();
        }

        return players.get(player.getName()).enabled;
    }
}
