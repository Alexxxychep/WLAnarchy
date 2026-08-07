package me.alexxxychep.wlanarchy.color;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.Objects;

public class ColorPalette {
    private final TextColor primaryColor;
    private final TextColor secondaryColor;
    private final TextColor textColor;
    private final TextColor errorColor;
    private final TextColor successColor;
    private final TextColor warningColor;
    private final TextColor infoColor;

    public ColorPalette() {
        primaryColor = TextColor.color(NamedTextColor.BLUE);
        secondaryColor = TextColor.color(NamedTextColor.WHITE);

        textColor = TextColor.color(NamedTextColor.WHITE);
        errorColor = TextColor.color(NamedTextColor.RED);
        successColor = TextColor.color(NamedTextColor.GREEN);
        warningColor = TextColor.color(NamedTextColor.YELLOW);
        infoColor = TextColor.color(NamedTextColor.GRAY);
    }

    public ColorPalette(TextColor primaryColor, TextColor secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;

        textColor = TextColor.color(NamedTextColor.WHITE);
        errorColor = TextColor.color(NamedTextColor.RED);
        successColor = TextColor.color(NamedTextColor.GREEN);
        warningColor = TextColor.color(NamedTextColor.YELLOW);
        infoColor = TextColor.color(NamedTextColor.GRAY);
    }

    public TextColor getPrimary() {
        return primaryColor;
    }

    public TextColor getSecondary() {
        return secondaryColor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ColorPalette that = (ColorPalette) obj;
        return Objects.equals(primaryColor, that.primaryColor) &&
                Objects.equals(secondaryColor, that.secondaryColor);
    }
}
