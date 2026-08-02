package me.alexxxychep.wlanarchy.chat;

import net.kyori.adventure.text.Component;

public class ChatMessageFactory {
    public WLChatMessage fromComponent(Component component) {

        return new WLChatMessage(true, component);
    }
}
