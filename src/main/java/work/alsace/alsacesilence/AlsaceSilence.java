package work.alsace.alsacesilence;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class AlsaceSilence extends JavaPlugin {
    private final List<UUID> silenceMap = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Objects.requireNonNull(this.getCommand("silence")).setExecutor(new SilenceCommand(this));
        this.getLogger().info("AlsaceSilence 已启动");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("AlsaceSilence 已关闭");
    }

    /**
     * 已静音玩家列表Map
     *
     * @return Map 已静音玩家列表
     */
    public List<UUID> getSilenceMap() {
        return silenceMap;
    }

    /**
     * 添加已静音玩家
     * @param uuid 玩家UUID
     */
    public void addSilencePlayer(UUID uuid) {
        silenceMap.add(uuid);
    }

    /**
     * 移除已静音玩家
     * @param uuid 玩家UUID
     */
    public void removeSilencePlayer(UUID uuid) {
        silenceMap.remove(uuid);
    }

    /**
     * 判断玩家是否已静音
     * @param uuid 玩家UUID
     * @return boolean 是否已静音
     */
    public boolean isSilencePlayer(UUID uuid) {
        return silenceMap.contains(uuid);
    }
}
