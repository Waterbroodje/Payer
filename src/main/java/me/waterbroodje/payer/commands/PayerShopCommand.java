package me.waterbroodje.payer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.waterbroodje.payer.PayerPlugin;

public class PayerShopCommand extends CommandBase<PayerPlugin> {
    /**
     * Creates a new CommandBase for the given plugin.
     *
     * @param plugin The plugin that owns this command.
     */
    public PayerShopCommand(PayerPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        // todo
        return true;
    }
}
