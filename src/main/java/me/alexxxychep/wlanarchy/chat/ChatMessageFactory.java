package me.alexxxychep.wlanarchy.chat;

import me.alexxxychep.wlanarchy.player.NameService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

public class ChatMessageFactory {
    private final NameService nameService;

    public ChatMessageFactory(NameService nameService) {
        this.nameService = nameService;
    }

    public WLChatMessage fromComponent(Component component, UUID sender) {

        TextComponent textComponent = (TextComponent) component;
        String rawContent = textComponent.content();
        boolean global = rawContent.startsWith("!");
        if (global) {
            rawContent = rawContent.substring(1);
        }

        return new WLChatMessage(global, textComponent.content(rawContent), sender, nameService.getDisplayName(sender));
    }
}
