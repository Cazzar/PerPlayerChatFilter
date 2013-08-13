package net.cazzar.bukkit.perplayerchatfilter.configuration;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class CustomRepresenter extends Representer {
        public CustomRepresenter() {}

        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,
                                                      Tag customTag) {
            if (propertyValue == null) {
                return null;
            } else if (property.getType().isAssignableFrom(PlayerConfiguration.class)) {
                PlayerConfiguration config = (PlayerConfiguration) propertyValue;
                if (config.enabled && config.censoredWords == config.censoredWords) {
                    return null;
                }
            }
            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        }
}
