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

package net.cazzar.bukkit.perplayerchatfilter.configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public class PlayerConfiguration implements ConfigurationSerializable {
    public List<String> censoredWords = Lists.newArrayList(PerPlayerChatFilter.getInstance().censorWords);
    public boolean enabled = PerPlayerChatFilter.getInstance().getEnabledByDefault();

    public PlayerConfiguration() {
    }

    public PlayerConfiguration(Map<String, Object> dataMap) {
        try {
            for (Field f : getClass().getDeclaredFields()) {
                //data.put(f.getName(), f.get(this));
                if (dataMap.containsKey(f.getName()))
                    f.set(this, dataMap.get(f.getName()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Map representation of this class.
     * <p/>
     * This class must provide a method to restore this class, as defined in the {@link
     * org.bukkit.configuration.serialization.ConfigurationSerializable} interface javadocs.
     *
     * @return Map containing the current state of this class
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = Maps.newHashMap();
        try {
            for (Field f : getClass().getDeclaredFields()) {
                data.put(f.getName(), f.get(this));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public String toString() {
        return String.format("censoredWords: %s, enabled: %s", censoredWords, enabled);
    }

    public List<String> getCensoredWords() {
        if (censoredWords == null)
            return Lists.newArrayList();

        return censoredWords;
    }

    public boolean addCensoredWord(String word) {
        if (censoredWords == null) censoredWords = Lists.newArrayList();

        if (censoredWords.contains(word.toLowerCase())) return false;

        censoredWords.add(word.toLowerCase());
        return true;
    }

    public boolean removeCensoredWord(String word) {
        if (censoredWords == null) censoredWords = Lists.newArrayList();

        if (!censoredWords.contains(word.toLowerCase())) return false;

        censoredWords.remove(word.toLowerCase());
        return true;
    }

    public static PlayerConfiguration valueOf(Map<String, Object> map) {
        return new PlayerConfiguration(map);
    }

    public static PlayerConfiguration deserialize(Map<String, Object> map) {
        return new PlayerConfiguration(map);
    }
}
