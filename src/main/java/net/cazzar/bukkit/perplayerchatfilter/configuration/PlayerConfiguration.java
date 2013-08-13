package net.cazzar.bukkit.perplayerchatfilter.configuration;

import com.google.common.collect.Maps;
import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class PlayerConfiguration implements ConfigurationSerializable {
    public List<String> censoredWords = PerPlayerChatFilter.getInstance().censorWords;
    public boolean enabled = true;

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
}
