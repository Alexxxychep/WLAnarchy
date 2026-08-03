package me.alexxxychep.wlanarchy.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ChatMessageFactory {

    public WLChatMessage fromComponent(Component component) {
        TextComponent textComponent = (TextComponent) component;
        String rawContent = textComponent.content();
        boolean global = rawContent.startsWith("!");
        if(global) {
            rawContent = rawContent.substring(1);
        }

        return new WLChatMessage(true, textComponent.content(rawContent).append(Component.text("global = " + global)));
    }
}
