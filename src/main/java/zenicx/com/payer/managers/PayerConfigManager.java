package zenicx.com.payer.managers;

import org.bukkit.configuration.file.FileConfiguration;
import zenicx.com.payer.models.ShopItem;

import java.util.List;

public class PayerConfigManager {
    private final FileConfiguration fileConfiguration;

    public PayerConfigManager(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public boolean isShopEnabled() {
        return fileConfiguration.getBoolean("shop.enabled");
    }

    public List<String> getAllProducts() {
        List<String> list = fileConfiguration.getStringList("shop");
        list.remove("enabled");
        return list;
    }

    public String getPriceFromItem(ShopItem shopItem) {
        return fileConfiguration.getString("shop." + shopItem.getName() + ".price");
    }

    public String getSuccessUrl() {
        return fileConfiguration.getString("redirect.success-url");
    }

    public String getCancelUrl() {
        return fileConfiguration.getString("redirect.cancel-url");
    }

    public List<String> getPaymentMethodsFromItem(ShopItem shopItem) {
        return fileConfiguration.getStringList("shop." + shopItem.getName() + ".payment-methods");
    }

    public List<String> getPaymentMethodsFromName(String name) {
        return fileConfiguration.getStringList("shop." + name + ".payment-methods");
    }

    public ShopItem getShopItemFromName(String name) {
        return new ShopItem(
                fileConfiguration.getStringList("shop." + name + ".payment-methods"),
                fileConfiguration.getString("shop." + name + ".price"),
                fileConfiguration.getString("shop." + name + ".description"),
                name,
                fileConfiguration.getStringList("shop." + name + ".succes-commands"));
    }
}
