package net.cazzar.bukkit.perplayerchatfilter.configuration;

import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import net.cazzar.bukkit.perplayerchatfilter.configuration.yaml.YamlConstructor;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Author: Cayde
 */
public class ConfigHelper {
    static Configuration config = null;

    public static Configuration getConfig() {
        if (config == null) {
            try {
                PerPlayerChatFilter instance = PerPlayerChatFilter.getInstance();

                File configFile = new File(instance.getDataFolder(), "config.yml");
                if (!configFile.exists()) {
                    config = new Configuration();
                    config.save();
                }

                //Representer representer = new Representer();
                //representer.addClassTag(Configuration.class, Tag.YAML);
                //Yaml yaml = new Yaml(new Constructor(Configuration.class), representer);

                YamlConstructor construct = new YamlConstructor(Configuration.class);
                TypeDescription description = new TypeDescription(Configuration.class);
                description.putListPropertyType("player", PlayerConfiguration.class);
                construct.addTypeDescription(description);

                Yaml yaml = new Yaml(construct);

                config = (Configuration) yaml.load(new FileInputStream(configFile));
            } catch (FileNotFoundException ignored) {
            }
        }
        return config;
    }
}
