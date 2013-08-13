package net.cazzar.bukkit.perplayerchatfilter.configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Configuration {
    public Map<String, PlayerConfiguration> players = Maps.newHashMap();
    public List<String> defaultCensorList = Lists.newArrayList();

    public Configuration() {
    }

    public void save() {
        try {
            FileWriter fw = null;
            PerPlayerChatFilter instance = PerPlayerChatFilter.getInstance();
            File configFile = new File(instance.getDataFolder(), "config.yml");
            if (!instance.getDataFolder().exists()) instance.getDataFolder().mkdirs();
            try {
                if (!configFile.exists()) configFile.createNewFile();
                fw = new FileWriter(configFile);
                Yaml yaml = new Yaml(new Constructor(Configuration.class), new Representer());

                fw.write(yaml.dumpAsMap(this));

            } finally {
                if (fw != null) fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
