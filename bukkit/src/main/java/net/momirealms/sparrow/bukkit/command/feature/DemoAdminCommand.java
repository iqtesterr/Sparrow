package net.momirealms.sparrow.bukkit.command.feature;

import net.momirealms.sparrow.bukkit.SparrowNMSProxy;
import net.momirealms.sparrow.bukkit.command.handler.SparrowMessagingHandler;
import net.momirealms.sparrow.bukkit.command.key.SparrowBukkitArgumentKeys;
import net.momirealms.sparrow.bukkit.util.CommandUtils;
import net.momirealms.sparrow.common.command.AbstractCommandFeature;
import net.momirealms.sparrow.common.command.key.SparrowFlagKeys;
import net.momirealms.sparrow.common.locale.MessageConstants;
import net.momirealms.sparrow.common.util.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.bukkit.data.MultiplePlayerSelector;
import org.incendo.cloud.bukkit.parser.selector.MultiplePlayerSelectorParser;

public class DemoAdminCommand extends AbstractCommandFeature<CommandSender> {

    @Override
    public String getFeatureID() {
        return "demo_admin";
    }

    @Override
    public Command.Builder<? extends CommandSender> assembleCommand(CommandManager<CommandSender> manager, Command.Builder<CommandSender> builder) {
        return builder
                .required(SparrowBukkitArgumentKeys.PLAYER_SELECTOR, MultiplePlayerSelectorParser.multiplePlayerSelectorParser())
                .flag(SparrowFlagKeys.SILENT_FLAG)
                .handler(commandContext -> {
                    MultiplePlayerSelector selector = commandContext.get(SparrowBukkitArgumentKeys.PLAYER_SELECTOR);
                    var players = selector.values();
                    for (Player player : players) {
                        SparrowNMSProxy.getInstance().sendDemo(player);
                    }
                    CommandUtils.storeSelectorMessage(commandContext, selector,
                            Pair.of(MessageConstants.COMMANDS_ADMIN_DEMO_SUCCESS_SINGLE, MessageConstants.COMMANDS_ADMIN_DEMO_SUCCESS_MULTIPLE)
                    );
                })
                .appendHandler(SparrowMessagingHandler.instance());
    }
}
