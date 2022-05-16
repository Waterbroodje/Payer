package me.waterbroodje.payer.managers.methods;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import me.waterbroodje.payer.models.ShopItem;
import org.bukkit.entity.Player;
import me.waterbroodje.payer.PayerPlugin;
import me.waterbroodje.payer.managers.PayerConfigManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StripeManager {
    private final PayerPlugin plugin;

    public StripeManager(PayerPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the api key that the admin wants to use
     * @param apiKey The api key you can get on the stripe dashboard
     */
    public void register(String apiKey) {
        Stripe.apiKey = apiKey;
    }

    /**
     * Will create a payment link based on the product the player wants to buy
     * @param player The player that wants to buy the product
     * @param shopItem The product the player wants to buy
     * @return Returns the URL that will be given to the player to buy the product
     * @throws StripeException The exception that will be given if the session can not be created
     */
    public String createPayment(Player player, ShopItem shopItem) throws StripeException {
        PayerConfigManager payerConfigManager = plugin.getPayerConfigManager();

        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> product = new HashMap<>();
        product.put("price", shopItem.getPrice());
        product.put("quantity", 1);
        lineItems.add(product);

        Map<String, Object> params = new HashMap<>();
        params.put("success_url", payerConfigManager.getSuccessUrl());
        params.put("cancel_url", payerConfigManager.getCancelUrl());
        params.put("line_items", lineItems);
        params.put("payment_method_types", shopItem.getPaymentMethods());
        params.put("mode", "payment");
        params.put("customer", player.getUniqueId());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("product", shopItem.getName());
        params.put("metadata", hashMap);

        Session session = Session.create(params);
        plugin.getCurrentSessions().put(player.getUniqueId(), session.getId());
        return session.getUrl();
    }
}
