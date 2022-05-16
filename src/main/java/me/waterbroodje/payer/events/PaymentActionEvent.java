package me.waterbroodje.payer.events;

import me.waterbroodje.payer.models.ShopItem;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PaymentActionEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID uuid;
    private final ShopItem shopItem;

    public PaymentActionEvent(UUID uuid, ShopItem shopItem) {
        this.uuid = uuid;
        this.shopItem = shopItem;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
