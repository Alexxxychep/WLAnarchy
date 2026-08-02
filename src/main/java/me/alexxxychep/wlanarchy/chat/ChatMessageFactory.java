package me.alexxxychep.wlanarchy.chat;

import net.kyori.adventure.text.Component;

import javax.inject.Singleton;

public class ChatMessageFactory {

    public WLChatMessage fromComponent(Component component) {

        return new WLChatMessage(true, component);
    }
}
