package net.cazzar.bukkit.perplayerchatfilter.util;

import net.cazzar.bukkit.perplayerchatfilter.PerPlayerChatFilter;
import org.apache.commons.lang.StringUtils;

public class CensorUtil {
    public static String censorString(String message) {
        for (String word : PerPlayerChatFilter.getInstance().censorWords) {
            String replacement = StringUtils.repeat("*", word.length());
            message = message.replaceAll("(?i)" + word, replacement);
        }

        return message;
    }
}
