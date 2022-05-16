package zenicx.com.payer.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import zenicx.com.payer.events.PaymentActionEvent;

public class PaymentActionlistener implements Listener {

    @EventHandler
    public void onPaymentAction(PaymentActionEvent e) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(e.getUuid());

        if (offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(ChatColor.GREEN + "Thank you very much for this donation.");
        }
    }
}
