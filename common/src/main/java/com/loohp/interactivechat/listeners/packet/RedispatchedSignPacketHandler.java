package com.loohp.interactivechat.listeners.packet;

import com.loohp.interactivechat.InteractiveChat;
import com.loohp.interactivechat.utils.ModernChatSigningUtils;
import com.loohp.interactivechat.utils.PlayerUtils;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.tjdev.util.tjpluginutil.spigot.FoliaUtil;

import java.util.concurrent.ExecutionException;

/**
 * Reducing code duplication... one class at a time.
 * (where possible, of course!)
 */
public class RedispatchedSignPacketHandler {

    public static void redispatchCommand(Player player, String command) {
        FoliaUtil.scheduler.runTask(player, () -> {
            PlayerUtils.dispatchCommandAsPlayer(player, command);
            if (!InteractiveChat.skipDetectSpamRateWhenDispatchingUnsignedPackets) {
                ModernChatSigningUtils.detectRateSpam(player, command);
            }
        });
    }

    /**
     * Must check if ModernChatSigningUtils.isChatMessageIllegal is false!
     * @param player Player to dispatch the message as.
     * @param message Message to "re-dispatch"
     */
    public static void redispatchChatMessage(Player player, String message) {
        if (player.isConversing()) {
            FoliaUtil.scheduler.runTask(player, () -> player.acceptConversationInput(message));
            if (!InteractiveChat.skipDetectSpamRateWhenDispatchingUnsignedPackets) {
                FoliaUtil.scheduler.runTaskAsynchronously(() -> ModernChatSigningUtils.detectRateSpam(player, message));
            }
        } else {
            FoliaUtil.scheduler.runTaskAsynchronously(() -> {
                try {
                    Object decorated = ModernChatSigningUtils.getChatDecorator(player, LegacyComponentSerializer.legacySection().deserialize(message)).get();
                    PlayerUtils.chatAsPlayer(player, message, decorated);
                    if (!InteractiveChat.skipDetectSpamRateWhenDispatchingUnsignedPackets) {
                        ModernChatSigningUtils.detectRateSpam(player, message);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
