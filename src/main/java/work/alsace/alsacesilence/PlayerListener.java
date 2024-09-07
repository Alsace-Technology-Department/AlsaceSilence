package work.alsace.alsacesilence;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final AlsaceSilence plugin;

    public PlayerListener(AlsaceSilence plugin) {
        this.plugin = plugin;
    }

    /**
     * 监听玩家发送消息事件，如果玩家处于静音状态，则取消发送消息
     */
    @EventHandler
    public void onPlayerChatA(AsyncChatEvent event) {
        Player player = event.getPlayer();
        // 检查发送人是否处于静音状态，如果是，则取消发送消息
        if (plugin.isSilencePlayer(player.getUniqueId())) {
            player.sendMessage("您已开启静音模式，无法发送消息。");
            event.setCancelled(true);
        }
        // 接收人是静音状态则移除发送的消息
        event.viewers().removeIf(viewer ->
                viewer instanceof Player
                        && plugin.isSilencePlayer(((Player) viewer).getUniqueId())
        );
    }

    /**
     * 监听玩家下线事件，移除其静音状态
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.isSilencePlayer(player.getUniqueId())) {
            plugin.removeSilencePlayer(player.getUniqueId());
        }
    }
}
