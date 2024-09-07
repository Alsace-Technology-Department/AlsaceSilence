package work.alsace.alsacesilence;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SilenceCommand implements CommandExecutor, TabCompleter {
    private final AlsaceSilence plugin;

    public SilenceCommand(AlsaceSilence plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("只有玩家可以进行该操作。");
            return false;
        }

        if (args.length == 0) {
            toggleSilenceMode(player);
        } else if (args.length == 1) {
            handleSilenceCommand(commandSender, args[0]);
        } else {
            commandSender.sendMessage("指令使用方法：/mute [on/off]");
            return false;
        }
        return true;
    }

    /**
     * 切换静音模式
     *
     * @param player 玩家
     */
    private void toggleSilenceMode(Player player) {
        if (plugin.isSilencePlayer(player.getUniqueId())) {
            plugin.removeSilencePlayer(player.getUniqueId());
            player.sendMessage("已解除静音模式。");
        } else {
            plugin.addSilencePlayer(player.getUniqueId());
            player.sendMessage("已开启静音模式。");
        }
    }

    /**
     * 处理静音命令
     *
     * @param commandSender 命令发送人
     * @param arg           命令参数, on/off
     */
    private void handleSilenceCommand(CommandSender commandSender, String arg) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("只有玩家可以进行该操作。");
            return;
        }
        switch (arg.toLowerCase()) {
            case "on" -> {
                if (!plugin.isSilencePlayer(player.getUniqueId())) {
                    plugin.addSilencePlayer(player.getUniqueId());
                    player.sendMessage("已开启静音模式。");
                } else {
                    commandSender.sendMessage("您已处于静音模式。");
                }
            }
            case "off" -> {
                if (plugin.isSilencePlayer(player.getUniqueId())) {
                    plugin.removeSilencePlayer(player.getUniqueId());
                    player.sendMessage("已解除静音模式。");
                } else {
                    commandSender.sendMessage("您没有开启静音模式。");
                }
            }
            default -> commandSender.sendMessage("指令使用方法：/mute [on/off]");
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("on", "off");
        }
        return List.of();
    }
}
