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

package net.cazzar.bukkit.perplayerchatfilter;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.cazzar.bukkit.perplayerchatfilter.configuration.PlayerConfiguration;
import net.cazzar.bukkit.perplayerchatfilter.packets.PacketInterceptor;
import net.cazzar.bukkit.perplayerchatfilter.util.CommandCensorToggle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
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

        ConfigurationSerialization.registerClass(PlayerConfiguration.class, "PlayerConfig");
        //noinspection unchecked
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

        players.get(player.getName()).enabled = true;
        saveConfig();
    }

    public void removePlayer(Player player) {
        if (!players.containsKey(player.getName()))
            players.put(player.getName(), new PlayerConfiguration());

        players.get(player.getDisplayName()).enabled = false;
        saveConfig();
    }

    public boolean censoredForPlayer(Player player) {
        if (!players.containsKey(player.getName())) {
            players.put(player.getName(), new PlayerConfiguration());
            saveConfig();
        }

        return players.get(player.getName()).enabled;
    }

    public PlayerConfiguration getPlayerData(Player player) {
        if (!players.containsKey(player.getName())) {
            players.put(player.getName(), new PlayerConfiguration());
            saveConfig();
        }

        return players.get(player.getName());
    }

    @Override
    public void saveConfig() {
        getConfig().set("censor.players", players);
        getConfig().set("censor.words", censorWords);
        super.saveConfig();
    }

    public boolean addCensoredWord(String word) {
        if (censorWords == null) censorWords = Lists.newArrayList();

        if (censorWords.contains(word.toLowerCase())) return false;

        censorWords.add(word.toLowerCase());
        return true;
    }

    public boolean removeCensoredWord(String word) {
        if (censorWords == null) censorWords = Lists.newArrayList();

        if (!censorWords.contains(word.toLowerCase())) return false;

        censorWords.remove(word.toLowerCase());
        return true;
    }
}
