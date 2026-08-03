package me.alexxxychep.wlanarchy.chat;

import net.kyori.adventure.text.Component;

public class WLChatMessage {
    private final boolean global;
    private Component component;

    public WLChatMessage(boolean global, Component component) {
        this.global = global;
        this.component = component;
    }

    public WLChatMessage appendComponent(Component component) {
        component.append(component);
        return this;
    }

    public boolean isGlobal() {
        return global;
    }

    public Component toComponent() {
        return component;
    }
}
