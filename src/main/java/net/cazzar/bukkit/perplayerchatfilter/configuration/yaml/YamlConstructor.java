package net.cazzar.bukkit.perplayerchatfilter.configuration.yaml;

import net.cazzar.bukkit.perplayerchatfilter.configuration.PlayerConfiguration;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;

import java.util.HashMap;

/**
 * @Author: Cayde
 */
public class YamlConstructor extends Constructor {
    private HashMap<String, Class<?>> classMap = new HashMap<String, Class<?>>();

    public YamlConstructor(Class<?> theRoot) {
        super(theRoot);
        //classMap.put(MyPreferences.class.getName(), MyPreferences.class);
        //classMap.put(DeviceInfo.class.getName(), DeviceInfo.class);
        classMap.put(PlayerConfiguration.class.getName(), PlayerConfiguration.class);
    }


    /*
     * This is a modified version of the Constructor. Rather than using a class loader to
     * get external classes, they are already predefined above. This approach works similar to
     * the typeTags structure in the original constructor, except that class information is
     * pre-populated during initialization rather than runtime.
     *
     * @see org.yaml.snakeyaml.constructor.Constructor#getClassForNode(org.yaml.snakeyaml.nodes.Node)
     */
    protected Class<?> getClassForNode(Node node) {
        String name = node.getTag().getClassName();
        Class<?> cl = classMap.get(name);
        if (cl == null)
            throw new YAMLException("Class not found: " + name);
        else
            return cl;
    }
}
