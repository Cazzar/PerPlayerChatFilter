package net.cazzar.bukkit.perplayerchatfilter.configuration;

import java.util.List;

public class PlayerConfiguration {
    public List<String> censoredWords = Configuration.config.defaultCensorList;
    public boolean enabled = true;

    public PlayerConfiguration() {
    }

    public List<String> getCensoredWords() {
        return censoredWords;
    }

    public void setCensoredWords(List<String> censoredWords) {
        this.censoredWords = censoredWords;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
