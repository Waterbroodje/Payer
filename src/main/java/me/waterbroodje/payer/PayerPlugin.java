package me.waterbroodje.payer;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import me.waterbroodje.payer.commands.CommandBase;
import me.waterbroodje.payer.commands.PayerShopCommand;
import me.waterbroodje.payer.listeners.PaymentActionlistener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.waterbroodje.payer.events.PaymentActionEvent;
import me.waterbroodje.payer.managers.PayerConfigManager;

import java.util.HashMap;
import java.util.UUID;

public final class PayerPlugin extends JavaPlugin {
    private PayerConfigManager payerConfigManager;
    private HashMap<UUID, String> currentSessions; // The uuid of the player and the id of the session.

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupCommand();
        setupStripeListener();

        payerConfigManager = new PayerConfigManager(getConfig());
        currentSessions = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new PaymentActionlistener(), this);
    }

    public void setupCommand() {
        CommandBase<PayerPlugin> payerCommand = new CommandBase<PayerPlugin>(this) {
            @Override
            public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
                sender.sendMessage("");
                return false;
            }
        };

        PayerShopCommand payerShopCommand = new PayerShopCommand(this);
        payerCommand.registerSubCommand("shop", payerShopCommand);

        getCommand("payer").setExecutor(payerCommand);
    }

    public void setupStripeListener() {
        PayerPlugin plugin = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                currentSessions.keySet().forEach(uuid -> {
                    try {
                        Session session = Session.retrieve(currentSessions.get(uuid));

                        String paymentStatus = session.getPaymentStatus();
                        if (paymentStatus.equalsIgnoreCase("paid")) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Bukkit.getPluginManager().callEvent(new PaymentActionEvent(
                                            UUID.fromString(session.getCustomer()),
                                            payerConfigManager.getShopItemFromName(session.getMetadata().get("product"))));
                                }
                            }.runTaskAsynchronously(plugin);
                        }
                    } catch (StripeException e) {
                        e.printStackTrace();
                    }
                });
            }
        }.runTaskTimer(this, 0L, 20 * 60 * 10);
    }

    public String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void sendMessages(Player player, String... s) {
        for (String messages : s) {
            player.sendMessage(colorize(messages));
        }
    }

    public PayerConfigManager getPayerConfigManager() {
        return payerConfigManager;
    }

    public HashMap<UUID, String> getCurrentSessions() {
        return currentSessions;
    }
}
