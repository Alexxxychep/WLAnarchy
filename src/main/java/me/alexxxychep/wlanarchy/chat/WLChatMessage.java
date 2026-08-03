package me.alexxxychep.wlanarchy.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.UUID;

public class WLChatMessage {
    private final boolean global;
    private Component content;
    private Component senderName;
    private UUID sender;

    public WLChatMessage(boolean global, Component content, UUID sender, Component senderName) {
        this.global = global;
        this.content = content;
        this.sender = sender;
        this.senderName = senderName;
    }

    public WLChatMessage appendComponent(Component component) {
        component.append(component);
        return this;
    }

    public boolean isConsole() {
        return sender == null;
    }

    public boolean isGlobal() {
        return global;
    }

    public Component getContent() {
        return content;
    }

    public Component toMessage() {
        return senderName
                .append(Component.text(" → ").color(NamedTextColor.BLUE))
                .append(content)
                .append(content);
    }
}
