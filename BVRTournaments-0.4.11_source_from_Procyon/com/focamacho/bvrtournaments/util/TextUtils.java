// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.util;

import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandSource;
import com.google.common.collect.Lists;
import java.util.List;
import org.spongepowered.api.text.Text;
import java.text.MessageFormat;

public class TextUtils
{
    public static String formatString(final String string, final Object... args) {
        return MessageFormat.format(string, args).replace("&", "§");
    }
    
    public static Text formatText(final String text, final Object... args) {
        return (Text)Text.of(formatString(text, args));
    }
    
    public static List<Text> formatTextLore(final String[] text, final Object... args) {
        final List<Text> lore = (List<Text>)Lists.newArrayList();
        for (final String s : text) {
            for (final String s2 : formatString(s, args).split("\n")) {
                lore.add((Text)Text.of(s2));
            }
        }
        return lore;
    }
    
    public static void sendFormattedChatMessage(final CommandSource player, final String message, final Object... args) {
        for (final String s : message.split("\n")) {
            player.sendMessage(formatText(s, args));
        }
    }
    
    public static void sendFormattedMessage(final Player player, final String[] message, final Object... args) {
        final Title.Builder builder = Title.builder();
        if (message.length > 0 && !message[0].isEmpty()) {
            builder.title(formatText(message[0], args));
        }
        if (message.length > 1 && !message[1].isEmpty()) {
            builder.subtitle(formatText(message[1], args));
        }
        if (message.length > 2 && !message[2].isEmpty()) {
            builder.actionBar(formatText(message[2], args));
        }
        if (builder.getTitle().isPresent() || builder.getSubtitle().isPresent() || builder.getActionBar().isPresent()) {
            player.sendTitle(builder.build());
        }
        if (message.length > 3 && !message[3].isEmpty()) {
            sendFormattedChatMessage((CommandSource)player, message[3], args);
        }
    }
    
    public static void broadcastFormattedMessage(final String[] message, final Object... args) {
        Sponge.getServer().getOnlinePlayers().forEach(player -> sendFormattedMessage(player, message, args));
    }
    
    public static void broadcastFormattedChatMessage(final String message, final Object... args) {
        Sponge.getServer().getOnlinePlayers().forEach(player -> sendFormattedChatMessage(player, message, args));
    }
    
    public static BossBarColor getBossBarColor(String color) {
        final String trim;
        color = (trim = color.toUpperCase().trim());
        switch (trim) {
            case "BLUE": {
                return BossBarColors.BLUE;
            }
            case "GREEN": {
                return BossBarColors.GREEN;
            }
            case "PINK": {
                return BossBarColors.PINK;
            }
            case "PURPLE": {
                return BossBarColors.PURPLE;
            }
            case "RED": {
                return BossBarColors.RED;
            }
            case "WHITE": {
                return BossBarColors.WHITE;
            }
            default: {
                return BossBarColors.YELLOW;
            }
        }
    }
    
    public static BossBarOverlay getBossBarOverlay(String overlay) {
        final String trim;
        overlay = (trim = overlay.toUpperCase().trim());
        switch (trim) {
            case "PROGRESS": {
                return BossBarOverlays.PROGRESS;
            }
            case "NOTCHED_6": {
                return BossBarOverlays.NOTCHED_6;
            }
            case "NOTCHED_10": {
                return BossBarOverlays.NOTCHED_10;
            }
            case "NOTCHED_12": {
                return BossBarOverlays.NOTCHED_12;
            }
            default: {
                return BossBarOverlays.NOTCHED_20;
            }
        }
    }
}
