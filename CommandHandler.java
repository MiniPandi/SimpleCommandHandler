import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minipandi.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class CommandHandler implements CommandExecutor, TabCompleter {

    private final String name;
    private final String description;
    private final String permission;
    private final String permissionMessage;
    private final String usage;
    private final List<String> alias;

    protected CommandHandler(@NotNull String name, @NotNull String description, @NotNull String permission, @NotNull String permissionMessage, @NotNull String usage, @NotNull List<String> alias) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
        this.usage = usage;
        this.alias = alias;
    }

    public boolean register() {
        Bukkit.getCommandMap().register(Main.getInstance().getName(), new RefCommand(this.name));
        Bukkit.getCommandAliases().put(this.name, this.alias.toArray(new String[0]));
        return true;
    }

    public class RefCommand extends Command {

        protected RefCommand(@NotNull String name) {
            super(name);
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
            if (sender instanceof Player player) {
                if (!player.hasPermission(permission)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(permissionMessage));
                    return true;
                }
            }
            return CommandHandler.this.onCommand(sender, this, label, args);
        }
    }

    @Override
    public abstract boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings);

    @Override
    public abstract List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings);
}
