package net.cazzar.bukkit.perplayerchatfilter.configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Configuration {
    public Map<String, PlayerConfiguration> players = Maps.newHashMap();
    public List<String> defaultCensorList = Lists.newArrayList();

    public static Configuration config;

    static {

        try {
            PerPlayerChatFilter instance = PerPlayerChatFilter.getInstance();

            File configFile = new File(instance.getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                config = new Configuration();
                config.save();
            }

            Representer representer = new Representer();
            representer.addClassTag(Configuration.class, Tag.YAML);
            Yaml yaml = new Yaml(new Constructor(Configuration.class), representer);

            config = (Configuration) yaml.load(new FileInputStream(configFile));
        } catch (FileNotFoundException ignored) {
        }
    }

    private Configuration() {
    }

    public void save() {
        try {
            FileWriter fw = null;
            PerPlayerChatFilter instance = PerPlayerChatFilter.getInstance();
            File configFile = new File(instance.getDataFolder(), "config.yml");
            try {
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
