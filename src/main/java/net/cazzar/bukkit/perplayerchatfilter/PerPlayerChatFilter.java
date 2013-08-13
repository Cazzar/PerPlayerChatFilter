package net.cazzar.bukkit.perplayerchatfilter;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.collect.Lists;
import net.cazzar.bukkit.perplayerchatfilter.configuration.Configuration;
import net.cazzar.bukkit.perplayerchatfilter.configuration.PlayerConfiguration;
import net.cazzar.bukkit.perplayerchatfilter.packets.PacketInterceptor;
import net.cazzar.bukkit.perplayerchatfilter.util.CommandCensorToggle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PerPlayerChatFilter extends JavaPlugin {
    private ProtocolManager protocolManager;
    public List<String> censorWords;
    private List<String> censorPlayers;
    private static PerPlayerChatFilter instance;

    public static PerPlayerChatFilter getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        //ArrayList<String> defs = Lists.newArrayList("fuck");
        //getConfig().addDefault("censor.words", defs);
        //censorWords = (List<String>) getConfig().getList("censor.words", defs);
        //censorPlayers = (List<String>) getConfig().getList("censor.uncensored", Lists.newArrayList());
        //getConfig().options().copyDefaults(true);
        //saveConfig();
        protocolManager.addPacketListener(new PacketInterceptor(this));


        getCommand("filterme").setExecutor(new CommandCensorToggle());
    }

    public void addPlayer(Player player) {
        if (!Configuration.config.players.containsKey(player.getDisplayName()))
            Configuration.config.players.put(player.getDisplayName(), new PlayerConfiguration());
        else Configuration.config.players.get(player.getDisplayName()).enabled = true;
        Configuration.config.save();
    }

    public void removePlayer(Player player) {
        if (!Configuration.config.players.containsKey(player.getDisplayName()))
            Configuration.config.players.put(player.getDisplayName(), new PlayerConfiguration());
        else Configuration.config.players.get(player.getDisplayName()).enabled = false;
        Configuration.config.save();
    }

    public boolean uncensoredForPlayer(Player player) {
        if (!Configuration.config.players.containsKey(player.getDisplayName()))
            Configuration.config.players.put(player.getDisplayName(), new PlayerConfiguration());
        Configuration.config.save();

        return !Configuration.config.players.get(player.getDisplayName()).enabled;
    }
}
